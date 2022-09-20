package com.example.calculatror.controller;

import com.example.calculatror.model.Color;
import com.example.calculatror.model.MacBook;
import com.example.calculatror.model.onetomany.Adress;
import com.example.calculatror.repo.AdressRepository;
import com.example.calculatror.repo.ColorRepository;
import com.example.calculatror.repo.MacBookRepository;
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
@RequestMapping("/Adress")
public class AdressController {

    @Autowired
    private AdressRepository adressRepository;

    @GetMapping("/")
    public  String Index(Model model){
        Iterable<Adress> adresses =  adressRepository.findAll();
        model.addAttribute("adresss", adresses);
        return "Adress/index";
    }

    @GetMapping("/addView")
    public  String addView(Model model){
        model.addAttribute("adresss", new Adress());
        return "/Adress/add";
    }
    @GetMapping("/search")
    public  String searchMetrhod(
            @RequestParam("street") String street,
            Model model) {
        List<Adress> adressList = adressRepository.findByStreet(street);
        model.addAttribute("adresss", adressList);
        return "Adress/index";
    }
    @GetMapping("/searchContains")
    public  String searchMetrhodContains(
            @RequestParam("street") String street,
            Model model)
    {
        List<Adress> adressList = adressRepository.findByStreetContains(street);
        model.addAttribute("adresss", adressList);
        return "Adress/index";
    }

    @PostMapping("/add")
    public String add1(@ModelAttribute("adresss") @Valid Adress posts,
                       BindingResult bindingResult,
                       Model model)
    {
        if(bindingResult.hasFieldErrors())
            return  "Adress/add";

        adressRepository.save(posts);
        return "redirect:/Adress/";
    }

    @GetMapping("/{id}")
    public  String read(@PathVariable("id") Long id, Model model)
    {
        ArrayList<Adress> adressArrayList = new ArrayList<>();
        Optional<Adress> adressOptional = adressRepository.findById(id);
        adressOptional.ifPresent(adressArrayList::add);
        model.addAttribute("adresss", adressArrayList);
        return "/Adress/adress-info";
    }
    @GetMapping("/adress-del/{id}")
    public  String delete(@PathVariable("id") Long id, Model model)
    {
        adressRepository.deleteById(id);
        return "redirect:/Adress/";
    }
    @GetMapping("/adress-edit/{id}")
    public  String edit(@PathVariable("id") Long id, Model model)
    {
        if (!adressRepository.existsById(id))
        {
            return "redirect:/Adress/";
        }
        ArrayList<Adress> adressArrayList = new ArrayList<>();
        Optional<Adress> adressOptional = adressRepository.findById(id);
        adressOptional.ifPresent(adressArrayList::add);
        model.addAttribute("adresss", adressArrayList);
        return "/Adress/adress-edit";
    }
    @PostMapping("/adress-edit/{id}")
    public  String editpost(@PathVariable("id") Long id, Model model,
                            @ModelAttribute("adresss") @Valid Adress edit,
                            BindingResult bindingResul
    )
    {
        if(!adressRepository.existsById(id))
        {
            return "redirect:/Adress/";
        }
        if(bindingResul.hasErrors())
        {
            return "Adress/adress-edit";
        }

        edit.setId(id);
        adressRepository.save(edit);
        return "redirect:/Adress/";
    }
}
