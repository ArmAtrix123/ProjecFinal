package com.example.calculatror.controller;

import com.example.calculatror.model.onetomany.Adress;
import com.example.calculatror.model.onetomany.Sklad;
import com.example.calculatror.repo.AdressRepository;
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
@RequestMapping("/Sklad")
public class SkladController {
    @Autowired
    public AdressRepository addressRepository;
    @Autowired
    public SkladRepository skladRepository;

    @GetMapping("/")
    public String Main(Model model){
        Iterable<Adress> address = addressRepository.findAll();
        model.addAttribute("adrs",address);

        Iterable<Sklad> sklads = skladRepository.findAll();
        model.addAttribute("sklad",sklads);

        return "Sklad/index";
    }
    @PostMapping("/add")
    public String blogPostAdd(@ModelAttribute("sklad") @Valid Sklad posts,
                              BindingResult bindingResult, @RequestParam String name, @RequestParam String street, Model model)
    {
        if(bindingResult.hasFieldErrors()) {
            Iterable<Adress> address = addressRepository.findAll();
            model.addAttribute("adrs", address);
            return "Sklad/index";
        }
        Iterable<Sklad> sklads = skladRepository.findAll();
        model.addAttribute("sklad",sklads);

        List<Adress> adress = addressRepository.findByStreet(street);
        Sklad sklad = new Sklad(name, adress.get(0));
        skladRepository.save(sklad);
        return "redirect:/Sklad/";
    }
    @GetMapping("/search")
    public  String searchMetrhod(
            @RequestParam("name") String name,
            Model model) {
        List<Sklad> skladList = skladRepository.findByName(name);
        model.addAttribute("sklad", skladList);
        return "Sklad/index";
    }
    @GetMapping("/searchContains")
    public  String searchMetrhodContains(
            @RequestParam("name") String name,
            Model model)
    {
        List<Sklad> skladList = skladRepository.findByNameContains(name);
        model.addAttribute("sklad", skladList);
        return "Sklad/index";
    }
    @GetMapping("/{id}")
    public  String read(@PathVariable("id") Long id, Model model)
    {
        ArrayList<Sklad> skladArrayList = new ArrayList<>();
        Optional<Sklad> skladOptional = skladRepository.findById(id);
        skladOptional.ifPresent(skladArrayList::add);
        model.addAttribute("sklad", skladArrayList);
        return "/Sklad/sklad-info";
    }
    @GetMapping("/sklad-del/{id}")
    public  String delete(@PathVariable("id") Long id, Model model)
    {
        skladRepository.deleteById(id);
        return "redirect:/Sklad/";
    }
    @GetMapping("/sklad-edit/{id}")
    public  String edit(@PathVariable("id") Long id, Model model)
    {
        if (!skladRepository.existsById(id))
        {
            return "redirect:/Sklad/";
        }
        ArrayList<Sklad> skladArrayList = new ArrayList<>();
        Optional<Sklad> skladOptional = skladRepository.findById(id);
        skladOptional.ifPresent(skladArrayList::add);
        model.addAttribute("sklad", skladArrayList);

        Iterable<Adress> adresses = addressRepository.findAll();
        model.addAttribute("adresss",adresses);
        return "/Sklad/sklad-edit";
    }
    @PostMapping("/sklad-edit/{id}")
    public  String editpost(@PathVariable("id") Long id, Model model,
                            @ModelAttribute("sklad") @Valid Sklad edit,
                            BindingResult bindingResul, @RequestParam String street
    )
    {
        if(!skladRepository.existsById(id))
        {
            return "redirect:/Sklad/";
        }
        if(bindingResul.hasErrors())
        {
            Iterable<Adress> adresses = addressRepository.findAll();
            model.addAttribute("adresss",adresses);
            return "Sklad/sklad-edit";
        }
        List<Adress> adresses = addressRepository.findByStreet(street);
        edit.setAddress(adresses.get(0));
        edit.setId(id);
        skladRepository.save(edit);
        return "redirect:/Sklad/";
    }
}
