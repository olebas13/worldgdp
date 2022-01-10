package org.olebas.worldgdp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LookupDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParamJdbcTemplate;

    public List<String> getContinents(){
        MapSqlParameterSource params = new MapSqlParameterSource();
        return namedParamJdbcTemplate.queryForList("SELECT DISTINCT continent FROM country "
                        + " ORDER BY continent",
                params, String.class);
    }

    public List<String> getRegions(){
        MapSqlParameterSource params = new MapSqlParameterSource();
        return namedParamJdbcTemplate.queryForList("SELECT DISTINCT region FROM country "
                        + " ORDER BY region",
                params, String.class);
    }

    public List<String> getHeadOfStates(){
        MapSqlParameterSource params = new MapSqlParameterSource();
        return namedParamJdbcTemplate.queryForList("SELECT DISTINCT headofstate FROM country "
                        + " ORDER BY headofstate",
                params, String.class);
    }

    public List<String> getGovernmentTypes(){
        MapSqlParameterSource params = new MapSqlParameterSource();
        return namedParamJdbcTemplate.queryForList("SELECT DISTINCT governmentform FROM country "
                        + " ORDER BY governmentform",
                params, String.class);
    }
}
