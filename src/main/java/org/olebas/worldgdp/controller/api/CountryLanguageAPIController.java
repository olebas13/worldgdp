package org.olebas.worldgdp.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.olebas.worldgdp.dao.CountryLanguageDAO;
import org.olebas.worldgdp.model.CountryLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/languages")
@Slf4j
public class CountryLanguageAPIController {

    @Autowired
    private CountryLanguageDAO countryLanguageDAO;

    @GetMapping("/{countryCode}")
    public ResponseEntity<?> getLanguages(@PathVariable String countryCode,
                                          @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        try {
            return ResponseEntity.ok(countryLanguageDAO.getLanguages(countryCode, pageNo));
        } catch (Exception ex) {
            log.error("Error while getting languages for country: {}", countryCode, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while languages cities");
        }
    }

    @PostMapping("/{countryCode}")
    public ResponseEntity<?> addLanguage(@PathVariable String countryCode,
                                         @Valid @RequestBody CountryLanguage language) {
        try {
            if (countryLanguageDAO.languageExists(countryCode, language.getLanguage())) {
                return ResponseEntity.badRequest().body("Language already exists for country");
            }
            countryLanguageDAO.addLanguage(countryCode, language);
            return ResponseEntity.ok(language);
        } catch (Exception ex) {
            log.error("Error while adding language: {} to country: {}", language, countryCode, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while adding language");
        }
    }

    @DeleteMapping("/{countryCode}/language/{language}")
    public ResponseEntity<?> deleteLanguage(@PathVariable String countryCode, @PathVariable String language) {
        try {
            countryLanguageDAO.deleteLanguage(countryCode, language);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            log.error("Error occurred while deleting language : {}, for country: {}", language, countryCode, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting the language");
        }
    }
}
