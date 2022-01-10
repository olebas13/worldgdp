package org.olebas.worldgdp.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import static org.junit.Assert.assertNotNull;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(TestDBConfiguration.class)
public class DBConfigurationTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testDbConfiguration() {
        assertNotNull(dataSource);
    }
}
