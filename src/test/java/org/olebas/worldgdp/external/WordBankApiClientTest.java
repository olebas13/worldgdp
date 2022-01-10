package org.olebas.worldgdp.external;

import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.olebas.worldgdp.model.CountryGDP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringJUnitConfig(classes = {WorldBankApiClient.class})
public class WordBankApiClientTest {

    @Autowired
    private WorldBankApiClient worldBankApiClient;

    @Test
    public void testGetGDP() throws ParseException {
        List<CountryGDP> gdpData = worldBankApiClient.getGDP("IN");
        assertThat(gdpData).hasSize(13);
        CountryGDP gdp = gdpData.get(0);
        assertThat(gdp.getYear()).isEqualTo(Short.valueOf("2020"));
        gdp = gdpData.get(12);
        assertThat(gdp.getYear()).isEqualTo(Short.valueOf("2008"));
    }
}
