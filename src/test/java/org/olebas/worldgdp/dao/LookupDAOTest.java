package org.olebas.worldgdp.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.olebas.worldgdp.config.TestDBConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringJUnitConfig(classes = {TestDBConfiguration.class, LookupDAO.class})
public class LookupDAOTest {

    @Autowired
    private LookupDAO lookupDao;

    @Test
    public void test_getContinents() {
        List<String> continents = lookupDao.getContinents();
        assertThat(continents).hasSize(7);
        assertThat(continents.get(0)).isEqualTo("Africa");
    }

    @Test public void test_getRegions() {
        List<String> regions = lookupDao.getRegions();
        assertThat(regions).hasSize(25);
        assertThat(regions.get(0)).isEqualTo("Antarctica");
    }
}
