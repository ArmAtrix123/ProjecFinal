package com.example.calculatror.controller;

import com.example.calculatror.model.Color;
import com.example.calculatror.model.MacBook;
import com.example.calculatror.repo.ColorRepository;
import com.example.calculatror.repo.MacBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Color")
public class ColorController {
    @Autowired
    private ColorRepository colorRepository;

    @GetMapping("/")
    public  String Index(Model model){
        Iterable<Color> colors =  colorRepository.findAll();
        model.addAttribute("colors", colors);
        return "Color/index";
    }

    @GetMapping("/addView")
    public  String addView(Model model){
        model.addAttribute("colors", new Color());
        return "/Color/add";
    }
    @PostMapping("/add")
    public String add1(@ModelAttribute("colors") @Valid Color posts,
                       BindingResult bindingResult,
                       Model model)
    {
        if(bindingResult.hasFieldErrors())
            return  "Color/add";

        colorRepository.save(posts);
        return "redirect:/Color/";
    }

    @GetMapping("/{id}")
    public  String read(@PathVariable("id") Long id, Model model)
    {
        ArrayList<Color> colorArrayList = new ArrayList<>();
        Optional<Color> colorOptional = colorRepository.findById(id);
        colorOptional.ifPresent(colorArrayList::add);
        model.addAttribute("colors", colorArrayList);
        return "/Color/color-info";
    }
    @GetMapping("/color-del/{id}")
    public  String delete(@PathVariable("id") Long id, Model model)
    {
        colorRepository.deleteById(id);
        return "redirect:/Color/";
    }
    @GetMapping("/color-edit/{id}")
    public  String edit(@PathVariable("id") Long id, Model model)
    {
        if (!colorRepository.existsById(id))
        {
            return "redirect:/Color/";
        }
        ArrayList<Color> colorArrayList = new ArrayList<>();
        Optional<Color> colorOptional = colorRepository.findById(id);
        colorOptional.ifPresent(colorArrayList::add);
        model.addAttribute("colors", colorArrayList);
        return "/Color/color-edit";
    }
    @PostMapping("/color-edit/{id}")
    public  String editpost(@PathVariable("id") Long id, Model model,
                            @ModelAttribute("colors") @Valid Color edit,
                            BindingResult bindingResul
    )
    {
        if(!colorRepository.existsById(id))
        {
            return "redirect:/Color/";
        }
        if(bindingResul.hasErrors())
        {
            return "Color/color-edit";
        }

        edit.setId(id);
        colorRepository.save(edit);
        return "redirect:/Color/";
    }
}
