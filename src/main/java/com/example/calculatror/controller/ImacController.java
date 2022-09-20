package com.example.calculatror.controller;

import com.example.calculatror.model.Country;
import com.example.calculatror.model.Imac;
import com.example.calculatror.model.Passport;
import com.example.calculatror.model.Proizvoditel;
import com.example.calculatror.model.onetomany.Sklad;
import com.example.calculatror.repo.CountryRepository;
import com.example.calculatror.repo.ImacRepository;
import com.example.calculatror.repo.PassportRepository;
import com.example.calculatror.repo.SkladRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Imac")
public class ImacController {
    @Autowired
    private PassportRepository passportRepository;
    @Autowired
    private ImacRepository imacRepository;
    @Autowired
    public CountryRepository countryRepository;
    @Autowired
    private SkladRepository skladRepository;

    @GetMapping("/")
    public  String Main(Model model){

        Iterable<Passport> passport = passportRepository.findAll();
        model.addAttribute("passport", passport);

        Iterable<Country> countries = countryRepository.findAll();
        model.addAttribute("count",countries);

        Iterable<Imac> imacs = imacRepository.findAll();
        model.addAttribute("imac",imacs);

        Iterable<Sklad> sklads = skladRepository.findAll();
        model.addAttribute("sklad", sklads);

        return "Imac/index";
    }
    @GetMapping("/addView")
    public  String addView(Model model){
        model.addAttribute("imac", new Imac());

        Iterable<Passport> passports = passportRepository.findAll();
        model.addAttribute("passport", passports);

        Iterable<Country> countries = countryRepository.findAll();
        model.addAttribute("count",countries);

        Iterable<Sklad> sklads = skladRepository.findAll();
        model.addAttribute("sklad", sklads);
        return "/Imac/add";
    }

    @PostMapping("/add")
    public String add1(@ModelAttribute("imac") @Valid Imac posts,
                       BindingResult bindingResult,
                       Model model, @RequestParam String name, @RequestParam String number, @RequestParam String countr, @RequestParam String skladName)
    {
        if(bindingResult.hasFieldErrors()){
            Iterable<Country> countries = countryRepository.findAll();
            model.addAttribute("count",countries);

            Iterable<Sklad> sklads = skladRepository.findAll();
            model.addAttribute("sklad", sklads);

            Iterable<Passport> passports = passportRepository.findAll();
            model.addAttribute("passport",passports);
            return  "Imac/add";
        }
        List<Country> countries = countryRepository.findByName(countr);
        posts.setIm(countries.get(0));

        List<Sklad> sklads = skladRepository.findByName(skladName);
        posts.setSkladimac(sklads.get(0));

        List<Passport> passport = passportRepository.findBySeries(number);
        posts.setPassport(passport.get(0));

        imacRepository.save(posts);
        return "redirect:/Imac/";
    }

    @GetMapping("/search")
    public  String searchMetrhod(
            @RequestParam("name") String name,
            Model model) {
        List<Imac> imacList = imacRepository.findByName(name);
        model.addAttribute("imac", imacList);
        return "Imac/index";
    }

    @GetMapping("/searchContains")
    public  String searchMetrhodContains(
            @RequestParam("name") String name,
            Model model)
    {
        List<Imac> imacList = imacRepository.findByNameContains(name);
        model.addAttribute("count", imacList);
        return "Imac/index";
    }
    @GetMapping("/{id}")
    public  String read(@PathVariable("id") Long id, Model model)
    {
        ArrayList<Imac> imacArrayList = new ArrayList<>();
        Optional<Imac> imacOptional = imacRepository.findById(id);
        imacOptional.ifPresent(imacArrayList::add);
        model.addAttribute("imac", imacArrayList);
        return "/Imac/imac-info";
    }

    @GetMapping("/imac-del/{id}")
    public  String delete(@PathVariable("id") Long id, Model model)
    {
        imacRepository.deleteById(id);
        return "redirect:/Imac/";
    }

    @GetMapping("/imac-edit/{id}")
    public  String edit(@PathVariable("id") Long id, Model model)
    {
        if (!imacRepository.existsById(id))
        {
            return "redirect:/Imac/";
        }
        ArrayList<Imac> imacArrayList = new ArrayList<>();
        Optional<Imac> imacOptional = imacRepository.findById(id);
        imacOptional.ifPresent(imacArrayList::add);
        model.addAttribute("imac", imacArrayList);

        Iterable<Country> countries = countryRepository.findAll();
        model.addAttribute("count", countries);

        Iterable<Passport> passports = passportRepository.findAll();
        model.addAttribute("passport", passports);

        Iterable<Sklad> sklads = skladRepository.findAll();
        model.addAttribute("sklad", sklads);
        return "/Imac/imac-edit";
    }

    @PostMapping("/imac-edit/{id}")
    public  String editpost(@PathVariable("id") Long id, Model model,
                            @ModelAttribute("imac") @Valid Imac edit,
                            BindingResult bindingResul, @RequestParam String number, @RequestParam String countr, @RequestParam String skladName
    )
    {
        if(!imacRepository.existsById(id))
        {
            return "redirect:/Imac/";
        }
        if(bindingResul.hasErrors())
        {
            Iterable<Passport> passports = passportRepository.findAll();
            model.addAttribute("passport",passports);

            Iterable<Country> countries = countryRepository.findAll();
            model.addAttribute("count",countries);

            Iterable<Sklad> sklads = skladRepository.findAll();
            model.addAttribute("sklad", sklads);
            return "Imac/imac-edit";
        }
        List<Country> countries = countryRepository.findByName(countr);
        edit.setIm(countries.get(0));
        List<Passport> passports = passportRepository.findBySeries(number);
        edit.setPassport(passports.get(0));
        List<Sklad> sklads = skladRepository.findByName(skladName);
        edit.setSkladimac(sklads.get(0));
        edit.setId(id);
        imacRepository.save(edit);
        return "redirect:/Imac/";
    }
}
