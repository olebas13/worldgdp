package org.olebas.worldgdp.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.olebas.worldgdp.config.TestDBConfiguration;
import org.olebas.worldgdp.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringJUnitConfig(classes = {TestDBConfiguration.class, CountryDAO.class})
public class CountryDAOTest {

    @Autowired
    private CountryDAO countryDAO;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Before
    public void setup() {
        countryDAO.setNamedParameterJdbcTemplate(namedParameterJdbcTemplate);
    }

    @Test
    public void testGetCountries() {
        List<Country> countries = countryDAO.getCountries(new HashMap<>());
        assertThat(countries).hasSize(20);
    }

    @Test
    public void testGetCountries_searchByName() {
        Map<String, Object> params = new HashMap<>();
        params.put("search", "Aruba");
        List<Country> countries = countryDAO.getCountries(params);
        assertThat(countries).hasSize(1);
    }

    @Test
    public void testGetCountries_searchByContinent() {
        Map<String, Object> params = new HashMap<>();
        params.put("continent", "Asia");
        List<Country> countries = countryDAO.getCountries(params);
        assertThat(countries).hasSize(20);
    }

    @Test
    public void testGetCountryDetail() {
        Country c = countryDAO.getCountryDetail("IND");
        assertThat(c).isNotNull();
        assertThat(c.toString()).isEqualTo("Country(code=IND, name=India, "
                + "continent=Asia, region=Southern and Central Asia, "
                + "surfaceArea=3287263.0, indepYear=1947, population=1013662000, "
                + "lifeExpectancy=62.5, gnp=447114.0, localName=Bharat/India, "
                + "governmentForm=Federal Republic, headOfState=Kocheril Raman Narayanan, "
                + "capital=City(id=1109, name=New Delhi, countryCode=null, "
                + "country=null, district=null, population=null), code2=IN)");
    }

//    @Test
//    public void testEditCountryDetail() {
//        Country c = countryDAO.getCountryDetail("IND");
//        c.setHeadOfState("Ram Nath Kovind");
//        c.setPopulation(1324171354l);
//        countryDAO.editCountryDetail("IND", c);
//
//        c = countryDAO.getCountryDetail("IND");
//        assertThat(c.getHeadOfState()).isEqualTo("Ram Nath Kovind");
//        assertThat(c.getPopulation()).isEqualTo(1324171354l);
//        assertThat(c.getName()).isEqualTo("India");
//    }
}
