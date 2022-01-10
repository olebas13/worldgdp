package org.olebas.worldgdp.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.olebas.worldgdp.config.TestDBConfiguration;
import org.olebas.worldgdp.model.CountryLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringJUnitConfig(classes = {TestDBConfiguration.class, CountryLanguageDAO.class})
public class CountryLanguageDAOTest {

    @Autowired
    private CountryLanguageDAO countryLangDao;

    @Autowired
    private NamedParameterJdbcTemplate namedParamJdbcTemplate;

    @Before
    public void before() {
        countryLangDao.setNamedParamJdbcTemplate(namedParamJdbcTemplate);
    }

    @Test
    public void testGetLanguages() {
        List<CountryLanguage> languages = countryLangDao.getLanguages("IND", 1);
        assertThat(languages).hasSize(10);
    }

    @Test public void testAddLanguage() {
        String countryCode = "IND";
        CountryLanguage cl = createNewLanguage(countryCode);
        countryLangDao.addLanguage(countryCode, cl);
        List<CountryLanguage> languages = countryLangDao.getLanguages(countryCode, 2);
        assertThat(languages).hasSize(3);
    }

    @Test public void testLanguageExists() {
        String countryCode = "IND";
        CountryLanguage cl = createNewLanguage(countryCode);
        countryLangDao.addLanguage(countryCode, cl);

        assertThat(countryLangDao.languageExists(
                countryCode, cl.getLanguage())).isTrue();
        countryLangDao.deleteLanguage(countryCode, cl.getLanguage());
    }

    @Test public void testDeleteLanguage() {
        String countryCode = "IND";
        CountryLanguage cl = createNewLanguage(countryCode);
        countryLangDao.addLanguage(countryCode, cl);
        List<CountryLanguage> languages = countryLangDao.getLanguages(countryCode, 2);
        assertThat(languages).hasSize(3);

        countryLangDao.deleteLanguage(countryCode, "Test");
        languages = countryLangDao.getLanguages(countryCode, 2);
        assertThat(languages).hasSize(2);

    }

    private CountryLanguage createNewLanguage(String countryCode) {
        CountryLanguage cl = new CountryLanguage();
        cl.setCountryCode(countryCode);
        cl.setIsOfficial("T");
        cl.setLanguage("Test");
        cl.setPercentage(12.3);
        return cl;
    }
}
