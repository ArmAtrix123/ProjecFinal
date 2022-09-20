package com.example.calculatror.controller;

import com.example.calculatror.model.Passport;
import com.example.calculatror.repo.PassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/Passport")
public class PassportController {
    @Autowired
    private PassportRepository passportRepository;

    @GetMapping("/")
    public  String Index(Model model){
        Iterable<Passport> passports =  passportRepository.findAll();
        model.addAttribute("passports", passports);
        return "Passport/index";
    }

    @GetMapping("/addView")
    public  String addView(Model model){
        model.addAttribute("passports", new Passport());
        return "/Passport/add";
    }
    @PostMapping("/add")
    public String add1(@ModelAttribute("passports") @Valid Passport posts,
                       BindingResult bindingResult,
                       Model model)
    {
        if(bindingResult.hasFieldErrors())
            return  "Passport/add";

        passportRepository.save(posts);
        return "redirect:/Passport/";
    }

    @GetMapping("/{id}")
    public  String read(@PathVariable("id") Long id, Model model)
    {
        ArrayList<Passport> passportArrayList = new ArrayList<>();
        Optional<Passport> passportOptional = passportRepository.findById(id);
        passportOptional.ifPresent(passportArrayList::add);
        model.addAttribute("passports", passportArrayList);
        return "/Passport/passport-info";
    }
    @GetMapping("/passport-del/{id}")
    public  String delete(@PathVariable("id") Long id, Model model)
    {
        passportRepository.deleteById(id);
        return "redirect:/Passport/";
    }
    @GetMapping("/passport-edit/{id}")
    public  String edit(@PathVariable("id") Long id, Model model)
    {
        if (!passportRepository.existsById(id))
        {
            return "redirect:/Passport/";
        }
        ArrayList<Passport> passportArrayList = new ArrayList<>();
        Optional<Passport> passportOptional = passportRepository.findById(id);
        passportOptional.ifPresent(passportArrayList::add);
        model.addAttribute("passports", passportArrayList);
        return "/Passport/passport-edit";
    }
    @PostMapping("/passport-edit/{id}")
    public  String editpost(@PathVariable("id") Long id, Model model,
                            @ModelAttribute("passports") @Valid Passport edit,
                            BindingResult bindingResul
    )
    {
        if(!passportRepository.existsById(id))
        {
            return "redirect:/Passport/";
        }
        if(bindingResul.hasErrors())
        {
            return "Passport/passport-edit";
        }

        edit.setId(id);
        passportRepository.save(edit);
        return "redirect:/Passport/";
    }
}
