package com.plainid.assignment.converter.mapper;

import com.plainid.assignment.dao.Pokemon;
import com.plainid.assignment.dao.PokemonType;
import com.plainid.assignment.dao.Trainer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainerRawMapper implements RowMapper<Trainer> {
    @Override
    public Trainer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Trainer trainer = new Trainer(resultSet.getString("NAME"));
        trainer.setLevel(resultSet.getInt("level"));
        Pokemon pokemon = new Pokemon();
        pokemon.setId(resultSet.getInt("ID"));
        pokemon.setName(resultSet.getString("PokemonName"));
        pokemon.setType(Enum.valueOf(PokemonType.class,resultSet.getString("TYPE")));
        trainer.add(pokemon);
        trainer.setBag(trainer.getBag());

        return trainer;
//        return null;
    }
}
