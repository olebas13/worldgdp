package org.olebas.worldgdp.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.olebas.worldgdp.dao.CityDAO;
import org.olebas.worldgdp.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cities")
@Slf4j
public class CityAPIController {

    @Autowired
    private CityDAO cityDAO;

    @GetMapping("/{countryCode}")
    public ResponseEntity<?> getCities(@PathVariable String countryCode,
                                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        try {
            return new ResponseEntity<>(cityDAO.getCities(countryCode, pageNo), HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error while getting cities for country: {}", countryCode, ex);
            return new ResponseEntity<>("Error while getting cities", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{countryCode}")
    public ResponseEntity<?> addCity(@PathVariable String countryCode, @Valid @RequestBody City city) {
        try {
            cityDAO.addCity(countryCode, city);
            return new ResponseEntity<>(city, HttpStatus.CREATED);
        } catch (Exception ex) {
            log.error("Error while adding city: {} to country: {}", city, countryCode, ex);
            return new ResponseEntity<>("Error while adding city", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<?> deleteCity(@PathVariable Long cityId) {
        try {
            cityDAO.deleteCity(cityId);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            log.error("Error occurred while deleting city: {}", cityId, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting the city: " + cityId);
        }
    }
}
