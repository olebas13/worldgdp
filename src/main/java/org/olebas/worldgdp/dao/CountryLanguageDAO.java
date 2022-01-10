package org.olebas.worldgdp.dao;

import lombok.Setter;
import org.olebas.worldgdp.dao.mapper.CountryLanguageRowMapper;
import org.olebas.worldgdp.model.CountryLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Setter
public class CountryLanguageDAO {

    private static final Integer PAGE_SIZE = 10;

    @Autowired
    private NamedParameterJdbcTemplate namedParamJdbcTemplate;

    public List<CountryLanguage> getLanguages(String countryCode, Integer pageNo){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", countryCode);

        Integer offset = (pageNo - 1) * PAGE_SIZE;
        params.put("offset", offset);
        params.put("size", PAGE_SIZE);

        return namedParamJdbcTemplate.query("SELECT * FROM countrylanguage"
                        + " WHERE countrycode = :code"
                        + " ORDER BY percentage DESC "
                        + " LIMIT :size OFFSET :offset ",
                params, new CountryLanguageRowMapper());
    }

    public void addLanguage(String countryCode, CountryLanguage cl) {
        namedParamJdbcTemplate.update("INSERT INTO countrylanguage ( "
                        + " countrycode, language, isofficial, percentage ) "
                        + " VALUES ( :country_code, :language, "
                        + " :is_official, :percentage ) ",
                getAsMap(countryCode, cl));
    }

    public boolean languageExists(String countryCode, String language) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", countryCode);
        params.put("lang", language);

        Integer langCount = namedParamJdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM countrylanguage"
                        + " WHERE countrycode = :code "
                        + " AND language = :lang", params, Integer.class);
        return langCount > 0;
    }

    public void deleteLanguage (String countryCode, String language ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", countryCode);
        params.put("lang", language);
        namedParamJdbcTemplate.update("DELETE FROM countrylanguage "
                + " WHERE countrycode = :code AND "
                + " language = :lang ", params);
    }

    private  Map<String, Object> getAsMap(String countryCode, CountryLanguage cl){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("country_code", countryCode);
        map.put("language", cl.getLanguage());
        map.put("is_official", cl.getIsOfficial());
        map.put("percentage", cl.getPercentage());
        return map;
    }
}
