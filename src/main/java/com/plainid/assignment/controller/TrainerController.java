package com.plainid.assignment.controller;

import com.plainid.assignment.converter.mapper.PokemonRawMapper;
import com.plainid.assignment.converter.mapper.TrainerRawMapper;
import com.plainid.assignment.dao.Pokemon;
import com.plainid.assignment.dao.PokemonList;
import com.plainid.assignment.dao.Trainer;
import com.plainid.assignment.dao.TrainersList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trainer")
public class TrainerController {
    private

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/trainers")
    public TrainersList getTrainers() {
        List<Trainer> rows = jdbcTemplate.query("SELECT t.name,t.level,p.name as PokemonName,p.id,p.type as type from Trainer t " +
                "inner join POKEMON_TRAINER pt on t.ID = pt.TRAINER_ID inner join Pokemon P on " +
                "p.ID = pt.POKEMON_ID", new TrainerRawMapper());
//        PokemonList pokemonList = new PokemonList();
//        pokemonList.setPokemons(rows);
//        return pokemonList;
        TrainersList trainersList = new TrainersList();
        trainersList.setTrainers(rows);
        return trainersList;
    }

    public Trainer GetTrainer(){
        return null;
    }
}
