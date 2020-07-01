package com.plainid.assignment.controller;


import com.plainid.assignment.converter.mapper.PokemonRawMapper;
import com.plainid.assignment.dao.Battle;
import com.plainid.assignment.dao.Pokemon;
import com.plainid.assignment.dao.PokemonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * this class responsible about battle api
 */
@RestController
@RequestMapping("/battle")
public class BattleController {
    private

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Pokemon> getPokemonsTrainer(String trainerName) {
        List<Pokemon> pokemons = jdbcTemplate.query("select * from (select * from pokemon_trainer where" +
                " Trainer_id = (select id from trainer where name = '" + trainerName + "')) as PT" +
                " ,Pokemon where PT.pokemon_id = Pokemon.id", new PokemonRawMapper());
        return pokemons;
    }

    @GetMapping("/{trainerName1}/{trainerName2}")
    public Battle play(@PathVariable String trainerName1, @PathVariable String trainerName2) {
        Battle battle = new Battle();
        List<Pokemon> trainer1Pokemons = getPokemonsTrainer(trainerName1);
        List<Pokemon> trainer2Pokemons = getPokemonsTrainer(trainerName2);
        int t1Size = trainer1Pokemons.size();
        int t2Size = trainer2Pokemons.size();

        if (t1Size != 3 || t2Size != 3) {
            // there is no fight
            battle.setStatus("error");
            battle.setMassage("canceled");
            return battle;
        }
        int score = 0;
        for (int i = 0; i < t1Size; ++i) {
            // battle. calculate wins.
            score += battlePokemonType(trainer1Pokemons.get(i).getType(), trainer2Pokemons.get(i).getType());

        }
        // need update level in database.
        if (score == 0) {
            // tie, update 2 players
            jdbcTemplate.update("update trainer set level = level + 1 where name='" + trainerName1 + "';");
            jdbcTemplate.update("update trainer set level = level + 1 where name='" + trainerName2 + "';");
            battle.setStatus("success");
            battle.setMassage("draw");
        } else if (score > 0) {
            jdbcTemplate.update("update trainer set level = level + 2 where name='" + trainerName1 + "';");
            battle.setStatus("success");
            battle.setMassage(trainerName1 + " wins");

        } else {
            jdbcTemplate.update("update trainer set level = level + 2 where name='" + trainerName2 + "';");
            battle.setStatus("success");
            battle.setMassage(trainerName2 + " wins");

        }
        return battle;
    }

    public int battlePokemonType(PokemonType pokemon1, PokemonType pokemon2) {
        int retVal = 0; // value for same type
        // fire stronger than grass = 1
        if (pokemon1 == PokemonType.Fire) {
            if (pokemon2 == PokemonType.Grass) {
                retVal =  1;
            } else if (pokemon2 == PokemonType.Water) {
                retVal =  -1;

            }
        }
        // water stronger than fire = 1
        else if (pokemon1 == PokemonType.Water) {
            if (pokemon2 == PokemonType.Grass) {
                retVal =  -1;
            } else if (pokemon2 == PokemonType.Fire) {
                retVal = 1;
            }

        }
        // grass stronger than water = 1
        else if (pokemon1 == PokemonType.Grass) {
            if (pokemon2 == PokemonType.Fire) {
                retVal =  -1;
            } else if (pokemon2 == PokemonType.Water) {
                retVal = 1;
            }
        }
      return retVal;
    }
}
