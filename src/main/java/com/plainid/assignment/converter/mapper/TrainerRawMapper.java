package com.plainid.assignment.converter.mapper;

import com.plainid.assignment.dao.Trainer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainerRawMapper implements RowMapper<Trainer> {
    @Override
    public Trainer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Trainer trainer = new Trainer(rs.getString("NAME"));
        trainer.setLevel(rs.getInt("LEVEL"));
        return trainer;

    }
}
