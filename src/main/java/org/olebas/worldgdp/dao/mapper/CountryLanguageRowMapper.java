package org.olebas.worldgdp.dao.mapper;

import org.olebas.worldgdp.model.CountryLanguage;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryLanguageRowMapper implements RowMapper<CountryLanguage> {

    @Override
    public CountryLanguage mapRow(ResultSet rs, int rowNum) throws SQLException {
        CountryLanguage countryLng = new CountryLanguage();
        countryLng.setCountryCode(rs.getString("countrycode"));
        countryLng.setIsOfficial(rs.getString("isofficial"));
        countryLng.setLanguage(rs.getString("language"));
        countryLng.setPercentage(rs.getDouble("percentage"));
        return  countryLng;
    }
}
