package org.olebas.worldgdp.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.olebas.worldgdp.AppConfiguration;
import org.olebas.worldgdp.dao.CityDAO;
import org.olebas.worldgdp.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringJUnitWebConfig(classes = {AppConfiguration.class})
public class CityAPIControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Autowired
    private CityDAO cityDao;

    @Autowired
    // @Qualifier("namedParamJdbcTemplate")
    private NamedParameterJdbcTemplate namedParamJdbcTemplate;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        cityDao.setNamedParameterJdbcTemplate(namedParamJdbcTemplate);
    }

    @Test
    public void testGetCities() throws Exception {
        String countryCode = "IND";
        this.mockMvc.perform(get("/api/cities/{countryCode}", countryCode)
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].name", is("Mumbai (Bombay)")));
    }

    @Test
    public void testAddCity() throws Exception{
        String countryCode = "IND";
        City city = new City();
        city.setCountryCode(countryCode);
        city.setDistrict("Karnataka");
        city.setName("Large State");
        city.setPopulation(10500000L + 100);

        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult result = this.mockMvc.perform(
                        post("/api/cities/{countryCode}",countryCode)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(city))
                ).andExpect(status().isCreated())
                .andReturn();

        List<City> cities = cityDao.getCities(countryCode, 1);
        City first = cities.get(0);
        assertThat(first.getName()).isEqualTo("Large State");
        cityDao.deleteCity(first.getId());
    }

    @Test
    public void testDeleteCity() throws Exception {
        String countryCode = "IND";
        City city = new City();
        city.setCountryCode(countryCode);
        city.setDistrict("Karnataka");
        city.setName("Large State");
        city.setPopulation(10500000L + 100);

        Long cityId = cityDao.addCity(countryCode, city);
        this.mockMvc.perform(
                        delete("/api/cities/{cityId}", cityId)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        List<City> cities = cityDao.getCities(countryCode, 1);
        City first = cities.get(0);
        assertThat(first.getName()).isEqualTo("Mumbai (Bombay)");
    }
}
