package com.plainid.assignment.controller;

//import com.plainid.assignment.converter.mapper.TrainerRawMapper;
import com.plainid.assignment.converter.mapper.TrainerRawMapper;
import com.plainid.assignment.dao.Pokemon;
import com.plainid.assignment.dao.Trainer;
import com.plainid.assignment.dao.TrainersList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
/**
 * this class responsible about trainer/trainers api
 */
@RestController
//@RequestMapping("/trainers")
public class TrainerController {
    private

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/trainers")
    public TrainersList getTrainers() {
        List<Trainer> trainers = jdbcTemplate.query("select * from trainer", new TrainerRawMapper());
        for (Trainer t : trainers) {
            String name = t.getName();
            List<String> pokemonList = getStringPokemonsTrainer(name);
            Queue<String> queue = new LinkedList<>(pokemonList);
            t.setBag(queue);
        }

        TrainersList trainersList = new TrainersList();
        trainersList.setTrainers(trainers);
        return trainersList;
    }

    @GetMapping("/trainer/{trainerName}")
    public Trainer getTrainer(@PathVariable String trainerName) {
        Trainer trainer = jdbcTemplate.queryForObject("select * from trainer where name = '" + trainerName + "'", new TrainerRawMapper());
        List<String> pokemonList = getStringPokemonsTrainer(trainerName);
        Queue<String> queue = new LinkedList<>(pokemonList);
        assert trainer != null;
        trainer.setBag(queue);
        return trainer;
    }

    private List<String> getStringPokemonsTrainer(String trainerName) {
        List<String> pokemons = jdbcTemplate.query("select name from (select * from pokemon_trainer WHERE" +
                " Trainer_id = (select id from trainer where name = '" + trainerName + "')) as PT" +
                " ,Pokemon where PT.POKEMON_ID = Pokemon.ID", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("NAME");

            }
        });
        return pokemons;
    }

    @GetMapping("/trainer/{trainerName}/catch/{pokemonName}")
    public List<String> catchPokemon(@PathVariable String trainerName, @PathVariable String pokemonName){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        List<String> trainersPokemons = getStringPokemonsTrainer(trainerName);
        Integer trainerID = jdbcTemplate.queryForObject("select id from trainer where name ='" + trainerName + "'", Integer.class);
        Integer pokemonID = jdbcTemplate.queryForObject("select id from pokemon where name ='" + pokemonName + "'", Integer.class);

        if(trainersPokemons.size() == 3){
            jdbcTemplate.update("update pokemon_trainer set pokemon_id=" + pokemonID + ", timer='" + timestamp + "' \n" +
                    "where timer = (select min(timer) from pokemon_trainer\n" +
                    "where trainer_id=" + trainerID+")");
        } else {
            jdbcTemplate.update("insert into pokemon_trainer (trainer_id, pokemon_id) values ('" + trainerID + "'," + pokemonID +");");

        }

        trainersPokemons = getStringPokemonsTrainer(trainerName);
        return trainersPokemons;

    }
}

