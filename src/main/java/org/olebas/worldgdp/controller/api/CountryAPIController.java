package org.olebas.worldgdp.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.olebas.worldgdp.dao.CountryDAO;
import org.olebas.worldgdp.external.WorldBankApiClient;
import org.olebas.worldgdp.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/countries")
@Slf4j
public class CountryAPIController {

    @Autowired
    private CountryDAO countryDAO;

    @Autowired
    private WorldBankApiClient worldBankApiClient;

    @GetMapping
    public ResponseEntity<?> getCountries(
            @RequestParam(name = "search", required = false) String searchTerm,
            @RequestParam(name = "continent", required = false) String continent,
            @RequestParam(name = "region", required = false) String region,
            @RequestParam(name = "pageNo", required = false) Integer pageNo
    ) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("search", searchTerm);
            params.put("continent", continent);
            params.put("region", region);
            if (pageNo != null) {
                params.put("pageNo", pageNo.toString());
            }

            List<Country> countries = countryDAO.getCountries(params);
            System.out.println(countries);
            Map<String, Object> response = new HashMap<>();
            response.put("list", countries);
            response.put("count", countryDAO.getCountriesCount(params));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Error while getting countries", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while getting countries");
        }
    }

    @PostMapping("/{countryCode}")
    public ResponseEntity<?> editCountry(@PathVariable String countryCode, @Valid @RequestBody Country country) {
        try {
            countryDAO.editCountryDetail(countryCode, country);
            Country countryFromDb = countryDAO.getCountryDetail(countryCode);
            return ResponseEntity.ok(countryFromDb);
        } catch (Exception ex) {
            log.error("Error while editing the country: {} with data: {}", countryCode, country, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while editing the country");
        }
    }

    @GetMapping("/{countryCode}/gdp")
    public ResponseEntity<?> getGDP(@PathVariable String countryCode) {
        try {
            return ResponseEntity.ok(worldBankApiClient.getGDP(countryCode));
        } catch (Exception ex) {
            log.error("Error while getting GDP for country: {}", countryCode, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while getting GDP");
        }
    }
}
