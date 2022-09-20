package com.example.calculatror.controller;

import com.example.calculatror.model.Proizvoditel;
import com.example.calculatror.model.onetomany.Adress;
import com.example.calculatror.repo.AdressRepository;
import com.example.calculatror.repo.ProizvoditelRepository;
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
@RequestMapping("/Proizvoditel")
public class ProizvoditelController {

    @Autowired
    private ProizvoditelRepository proizvoditelRepository;

    @GetMapping("/")
    public  String Index(Model model){
        Iterable<Proizvoditel> proizvoditels =  proizvoditelRepository.findAll();
        model.addAttribute("pros", proizvoditels);
        return "Proizvoditel/index";
    }

    @GetMapping("/addView")
    public  String addView(Model model){
        model.addAttribute("pros", new Proizvoditel());
        return "/Proizvoditel/add";
    }

    @GetMapping("/search")
    public  String searchMetrhod(
            @RequestParam("name") String name,
            Model model) {
        List<Proizvoditel> proizvoditelList = proizvoditelRepository.findByName(name);
        model.addAttribute("pros", proizvoditelList);
        return "Proizvoditel/index";
    }
    @GetMapping("/searchContains")
    public  String searchMetrhodContains(
            @RequestParam("name") String name,
            Model model)
    {
        List<Proizvoditel> proizvoditelList = proizvoditelRepository.findByNameContains(name);
        model.addAttribute("pros", proizvoditelList);
        return "Proizvoditel/index";
    }

    @PostMapping("/add")
    public String add1(@ModelAttribute("pros") @Valid Proizvoditel posts,
                       BindingResult bindingResult,
                       Model model)
    {
        if(bindingResult.hasFieldErrors())
            return  "Proizvoditel/add";

        proizvoditelRepository.save(posts);
        return "redirect:/Proizvoditel/";
    }

    @GetMapping("/{id}")
    public  String read(@PathVariable("id") Long id, Model model)
    {
        ArrayList<Proizvoditel> proizvoditelArrayList = new ArrayList<>();
        Optional<Proizvoditel> proizvoditelOptional = proizvoditelRepository.findById(id);
        proizvoditelOptional.ifPresent(proizvoditelArrayList::add);
        model.addAttribute("pros", proizvoditelArrayList);
        return "/Proizvoditel/pro-info";
    }

    @GetMapping("/pro-del/{id}")
    public  String delete(@PathVariable("id") Long id, Model model)
    {
        proizvoditelRepository.deleteById(id);
        return "redirect:/Proizvoditel/";
    }
    @GetMapping("/pro-edit/{id}")
    public  String edit(@PathVariable("id") Long id, Model model)
    {
        if (!proizvoditelRepository.existsById(id))
        {
            return "redirect:/Proizvoditel/";
        }
        ArrayList<Proizvoditel> proizvoditelArrayList = new ArrayList<>();
        Optional<Proizvoditel> proizvoditelOptional = proizvoditelRepository.findById(id);
        proizvoditelOptional.ifPresent(proizvoditelArrayList::add);
        model.addAttribute("pros", proizvoditelArrayList);
        return "/Proizvoditel/pro-edit";
    }
    @PostMapping("/pro-edit/{id}")
    public  String editpost(@PathVariable("id") Long id, Model model,
                            @ModelAttribute("pros") @Valid Proizvoditel edit,
                            BindingResult bindingResul
    )
    {
        if(!proizvoditelRepository.existsById(id))
        {
            return "redirect:/Proizvoditel/";
        }
        if(bindingResul.hasErrors())
        {
            return "Proizvoditel/Proizvoditel-edit";
        }

        edit.setId(id);
        proizvoditelRepository.save(edit);
        return "redirect:/Proizvoditel/";
    }
}
