package com.example.calculatror.controller;

import com.example.calculatror.model.Color;
import com.example.calculatror.model.Iphone;
import com.example.calculatror.model.MacBook;
import com.example.calculatror.model.onetomany.Adress;
import com.example.calculatror.model.onetomany.Sklad;
import com.example.calculatror.repo.ColorRepository;
import com.example.calculatror.repo.IphoneRepository;
import com.example.calculatror.repo.PassportRepository;
import com.example.calculatror.repo.SkladRepository;
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
@RequestMapping("/Iphone")
@PreAuthorize("hasAnyAuthority('USER')")
public class IphoneController {

    @Autowired
    private IphoneRepository IphoneRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SkladRepository skladRepository;

    @GetMapping("/")
    public  String Index(Model model){
        Iterable<Iphone> iphones =  IphoneRepository.findAll();
        model.addAttribute("iphones", iphones);
        return "Iphone/index";
    }

    @GetMapping("/addView")
    public  String addView(Model model){
        model.addAttribute("iphones", new Iphone());

        Iterable<Color> colors = colorRepository.findAll();
        model.addAttribute("colors",colors);

        Iterable<Sklad> sklads = skladRepository.findAll();
        model.addAttribute("sklad", sklads);
        return "/Iphone/add";
    }

    @PostMapping("/add")
    public String add1(@ModelAttribute("iphones") @Valid Iphone posts,
                       BindingResult bindingResult, @RequestParam String colorName, @RequestParam String skladName,
                       Model model)
    {
        if(bindingResult.hasFieldErrors()){
            Iterable<Color> colors = colorRepository.findAll();
            model.addAttribute("colors", colors);

            Iterable<Sklad> sklads = skladRepository.findAll();
            model.addAttribute("sklad", sklads);
            return  "Iphone/add";
        }
        List<Color> colors = colorRepository.findByName(colorName);
        posts.setColoriphone(colors.get(0));

        List<Sklad> sklads = skladRepository.findByName(skladName);
        posts.setSkladiphone(sklads.get(0));

        IphoneRepository.save(posts);
        return "redirect:/Iphone/";
    }

    @GetMapping("/search")
    public  String searchMetrhod(
            @RequestParam("title") String title,
            Model model) {
            List<Iphone> iphoneList = IphoneRepository.findByTitle(title);
            model.addAttribute("iphones", iphoneList);
            return "Iphone/index";
    }
    @GetMapping("/searchContains")
    public  String searchMetrhodContains(
            @RequestParam("title") String title,
            Model model)
    {
        List<Iphone> iphoneList = IphoneRepository.findByTitle(title);
        model.addAttribute("iphones", iphoneList);
        return "Iphone/index";
    }
    @GetMapping("/{id}")
    public  String read(@PathVariable("id") Long id, Model model)
    {
        ArrayList<Iphone> IphoneArray = new ArrayList<>();
        Optional<Iphone> IphonePost = IphoneRepository.findById(id);
        IphonePost.ifPresent(IphoneArray::add);
        model.addAttribute("iphones", IphoneArray);
        return "/Iphone/iphone-info";
    }
    @GetMapping("/iphone-del/{id}")
    public  String delete(@PathVariable("id") Long id, Model model)
    {
        IphoneRepository.deleteById(id);
        return "redirect:/Iphone/";
    }
    @GetMapping("/iphone-edit/{id}")
    public  String edit(@PathVariable("id") Long id, Model model)
    {
        if (!IphoneRepository.existsById(id))
        {
            return "redirect:/Iphone/";
        }
        ArrayList<Iphone> IphoneArray = new ArrayList<>();
        Optional<Iphone> IphonePost = IphoneRepository.findById(id);
        IphonePost.ifPresent(IphoneArray::add);
        model.addAttribute("iphones", IphoneArray);

        Iterable<Color> colors = colorRepository.findAll();
        model.addAttribute("colors",colors);

        Iterable<Sklad> sklads = skladRepository.findAll();
        model.addAttribute("sklad", sklads);
        return "/Iphone/iphone-edit";
    }
    @PostMapping("/iphone-edit/{id}")
    public  String editpost(@PathVariable("id") Long id, Model model,
                             @ModelAttribute("iphones") @Valid Iphone edit, @RequestParam String colorName, @RequestParam String skladName,
                             BindingResult bindingResul
    )
    {
        if(!IphoneRepository.existsById(id))
        {
            return "redirect:/Iphone/";
        }
        if(bindingResul.hasErrors())
        {
            Iterable<Color> colors = colorRepository.findAll();
            model.addAttribute("colors",colors);

            Iterable<Sklad> sklads = skladRepository.findAll();
            model.addAttribute("sklad", sklads);
            return "/Iphone/iphone-edit";
        }
        List<Color> colors = colorRepository.findByName(colorName);
        edit.setColoriphone(colors.get(0));

        List<Sklad> sklads = skladRepository.findByName(skladName);
        edit.setSkladiphone(sklads.get(0));
        edit.setId(id);
        IphoneRepository.save(edit);
        return "redirect:/Iphone/";
    }

}
