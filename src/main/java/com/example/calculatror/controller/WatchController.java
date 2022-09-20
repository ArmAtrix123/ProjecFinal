package com.example.calculatror.controller;

import com.example.calculatror.model.Color;
import com.example.calculatror.model.Country;
import com.example.calculatror.model.Proizvoditel;
import com.example.calculatror.model.Watch;
import com.example.calculatror.model.onetomany.Sklad;
import com.example.calculatror.repo.*;
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
@RequestMapping("/Watch")
public class WatchController {

    @Autowired
    public CountryRepository countryRepository;
    @Autowired
    public WatchRepository watchRepository;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private SkladRepository skladRepository;

    @GetMapping("/")
    public String Main(Model model){
        Iterable<Country> countries = countryRepository.findAll();
        model.addAttribute("count",countries);

        Iterable<Watch> watches = watchRepository.findAll();
        model.addAttribute("wat",watches);

        return "Watch/index";
    }

    @GetMapping("/addView")
    public  String addView(Model model){
        model.addAttribute("wat", new Watch());

        Iterable<Country> countries = countryRepository.findAll();
        model.addAttribute("count",countries);

        Iterable<Color> colors = colorRepository.findAll();
        model.addAttribute("colors",colors);

        Iterable<Sklad> sklads = skladRepository.findAll();
        model.addAttribute("sklad", sklads);
        return "/Watch/add";
    }

    @PostMapping("/add")
    public String add1(@ModelAttribute("wat") @Valid Watch posts,
                       BindingResult bindingResult,
                       Model model, @RequestParam String countr, @RequestParam String colorName, @RequestParam String skladName)
    {
        if(bindingResult.hasFieldErrors()){
            Iterable<Country> countries = countryRepository.findAll();
            model.addAttribute("count",countries);

            Iterable<Sklad> sklads = skladRepository.findAll();
            model.addAttribute("sklad", sklads);
            return  "Watch/add";
        }
        List<Country> countries = countryRepository.findByName(countr);
        posts.setCo(countries.get(0));

        List<Color> colors = colorRepository.findByName(colorName);
        posts.setColorwatch(colors.get(0));

        List<Sklad> sklads = skladRepository.findByName(skladName);
        posts.setSkladwatch(sklads.get(0));
        watchRepository.save(posts);
        return "redirect:/Watch/";
    }

    @GetMapping("/search")
    public  String searchMetrhod(
            @RequestParam("name") String name,
            Model model) {
        List<Watch> watchList = watchRepository.findByName(name);
        model.addAttribute("wat", watchList);
        return "Watch/index";
    }

    @GetMapping("/searchContains")
    public  String searchMetrhodContains(
            @RequestParam("name") String name,
            Model model)
    {
        List<Watch> watchList = watchRepository.findByNameContains(name);
        model.addAttribute("wat", watchList);
        return "Watch/index";
    }

    @GetMapping("/{id}")
    public  String read(@PathVariable("id") Long id, Model model)
    {
        ArrayList<Watch> watchArrayList = new ArrayList<>();
        Optional<Watch> watchOptional = watchRepository.findById(id);
        watchOptional.ifPresent(watchArrayList::add);
        model.addAttribute("wat", watchArrayList);
        return "/Watch/watch-info";
    }

    @GetMapping("/watch-del/{id}")
    public  String delete(@PathVariable("id") Long id, Model model)
    {
        watchRepository.deleteById(id);
        return "redirect:/Watch/";
    }

    @GetMapping("/watch-edit/{id}")
    public  String edit(@PathVariable("id") Long id, Model model)
    {
        if (!watchRepository.existsById(id))
        {
            return "redirect:/Watch/";
        }
        ArrayList<Watch> watchArrayList = new ArrayList<>();
        Optional<Watch> watchOptional = watchRepository.findById(id);
        watchOptional.ifPresent(watchArrayList::add);
        model.addAttribute("wat", watchArrayList);

        Iterable<Country> countries = countryRepository.findAll();
        model.addAttribute("count",countries);

        Iterable<Color> colors = colorRepository.findAll();
        model.addAttribute("colors",colors);

        Iterable<Sklad> sklads = skladRepository.findAll();
        model.addAttribute("sklad", sklads);
        return "/Watch/watch-edit";
    }

    @PostMapping("/watch-edit/{id}")
    public  String editpost(@PathVariable("id") Long id, Model model,
                            @ModelAttribute("wat") @Valid Watch edit,
                            BindingResult bindingResul, @RequestParam String countr, @RequestParam String colorName, @RequestParam String skladName
    )
    {
        if(!watchRepository.existsById(id))
        {
            return "redirect:/Watch/";
        }
        if(bindingResul.hasErrors())
        {
            Iterable<Country> countries = countryRepository.findAll();
            model.addAttribute("count", countries);

            Iterable<Color> colors = colorRepository.findAll();
            model.addAttribute("colors",colors);

            Iterable<Sklad> sklads = skladRepository.findAll();
            model.addAttribute("sklad", sklads);
            return "Watch/watch-edit";
        }
        List<Country> countries = countryRepository.findByName(countr);
        edit.setCo(countries.get(0));

        List<Color> colors = colorRepository.findByName(colorName);
        edit.setColorwatch(colors.get(0));

        List<Sklad> sklads = skladRepository.findByName(skladName);
        edit.setSkladwatch(sklads.get(0));

        edit.setId(id);
        watchRepository.save(edit);
        return "redirect:/Watch/";
    }
}
