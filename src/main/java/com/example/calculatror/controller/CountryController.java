package com.example.calculatror.controller;

import com.example.calculatror.model.Color;
import com.example.calculatror.model.Country;
import com.example.calculatror.model.MacBook;
import com.example.calculatror.model.Proizvoditel;
import com.example.calculatror.model.onetomany.Adress;
import com.example.calculatror.model.onetomany.Sklad;
import com.example.calculatror.repo.AdressRepository;
import com.example.calculatror.repo.CountryRepository;
import com.example.calculatror.repo.ProizvoditelRepository;
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
@RequestMapping("/Country")
public class CountryController {

    @Autowired
    public CountryRepository countryRepository;
    @Autowired
    public ProizvoditelRepository proizvoditelRepository;

    @GetMapping("/")
    public String Main(Model model){

        Iterable<Proizvoditel> proizvoditels = proizvoditelRepository.findAll();
        model.addAttribute("pros",proizvoditels);

        Iterable<Country> countries = countryRepository.findAll();
        model.addAttribute("count",countries);

        return "Country/index";
    }

    @GetMapping("/addView")
    public  String addView(Model model){
        model.addAttribute("count", new Country());

        Iterable<Proizvoditel> proizvoditels = proizvoditelRepository.findAll();
        model.addAttribute("pros",proizvoditels);
        return "/Country/add";
    }

    @PostMapping("/add")
    public String add1(@ModelAttribute("count") @Valid Country posts,
                       BindingResult bindingResult,
                       Model model, @RequestParam String proizvod)
    {
        if(bindingResult.hasFieldErrors()){
            Iterable<Proizvoditel> proizvoditels = proizvoditelRepository.findAll();
            model.addAttribute("pros",proizvoditels);
            return  "Country/add";
        }
        List<Proizvoditel> proizvoditels = proizvoditelRepository.findByName(proizvod);
        posts.setPro(proizvoditels.get(0));
        countryRepository.save(posts);
        return "redirect:/Country/";
    }
    @GetMapping("/search")
    public  String searchMetrhod(
            @RequestParam("name") String name,
            Model model) {
        List<Country> countryList = countryRepository.findByName(name);
        model.addAttribute("count", countryList);
        return "Country/index";
    }

    @GetMapping("/searchContains")
    public  String searchMetrhodContains(
            @RequestParam("name") String name,
            Model model)
    {
        List<Country> countryList = countryRepository.findByNameContains(name);
        model.addAttribute("count", countryList);
        return "Country/index";
    }

    @GetMapping("/{id}")
    public  String read(@PathVariable("id") Long id, Model model)
    {
        ArrayList<Country> countryArrayList = new ArrayList<>();
        Optional<Country> countryOptional = countryRepository.findById(id);
        countryOptional.ifPresent(countryArrayList::add);
        model.addAttribute("count", countryArrayList);
        return "/Country/country-info";
    }
    @GetMapping("/country-del/{id}")
    public  String delete(@PathVariable("id") Long id, Model model)
    {
        countryRepository.deleteById(id);
        return "redirect:/Country/";
    }

    @GetMapping("/country-edit/{id}")
    public  String edit(@PathVariable("id") Long id, Model model)
    {
        if (!countryRepository.existsById(id))
        {
            return "redirect:/Country/";
        }
        ArrayList<Country> countryArrayList = new ArrayList<>();
        Optional<Country> countryOptional = countryRepository.findById(id);
        countryOptional.ifPresent(countryArrayList::add);
        model.addAttribute("count", countryArrayList);

        Iterable<Proizvoditel> proizvoditels = proizvoditelRepository.findAll();
        model.addAttribute("pros",proizvoditels);
        return "/Country/country-edit";
    }

    @PostMapping("/country-edit/{id}")
    public  String editpost(@PathVariable("id") Long id, Model model,
                            @ModelAttribute("count") @Valid Country edit,
                            BindingResult bindingResul, @RequestParam String proizvod
    )
    {
        if(!countryRepository.existsById(id))
        {
            return "redirect:/Country/";
        }
        if(bindingResul.hasErrors())
        {
            Iterable<Proizvoditel> proizvoditels = proizvoditelRepository.findAll();
            model.addAttribute("pros",proizvoditels);
            return "Country/country-edit";
        }
        List<Proizvoditel> proizvoditels = proizvoditelRepository.findByName(proizvod);
        edit.setPro(proizvoditels.get(0));
        edit.setId(id);
        countryRepository.save(edit);
        return "redirect:/Country/";
    }
}
