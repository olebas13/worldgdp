package org.olebas.worldgdp.controller.view;

import org.olebas.worldgdp.dao.CityDAO;
import org.olebas.worldgdp.dao.CountryDAO;
import org.olebas.worldgdp.dao.LookupDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/")
public class ViewController {

    @Autowired
    private CountryDAO countryDAO;

    @Autowired
    private LookupDAO lookupDAO;

    @Autowired
    private CityDAO cityDAO;

    @GetMapping({"/countries", "/"})
    public String countries(Model model, @RequestParam Map<String, Object> params) {
        model.addAttribute("continents", lookupDAO.getContinents());
        model.addAttribute("regions", lookupDAO.getRegions());
        model.addAttribute("countries", countryDAO.getCountries(params));
        model.addAttribute("count", countryDAO.getCountriesCount(params));
        return "countries";
    }

    @GetMapping("/countries/{code}")
    public String countryDetail(@PathVariable String code, Model model) {
        model.addAttribute("c", countryDAO.getCountryDetail(code));
        return "country";
    }

    @GetMapping("/countries/{code}/form")
    public String editCountry(@PathVariable String code, Model model) {
        model.addAttribute("c", countryDAO.getCountryDetail(code));
        model.addAttribute("cities", cityDAO.getCities(code));
        model.addAttribute("continents", lookupDAO.getContinents());
        model.addAttribute("regions", lookupDAO.getRegions());
        model.addAttribute("heads", lookupDAO.getHeadOfStates());
        model.addAttribute("govs", lookupDAO.getGovernmentTypes());
        return "country-form";
    }
}
