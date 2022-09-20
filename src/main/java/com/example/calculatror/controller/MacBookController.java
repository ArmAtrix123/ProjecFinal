package com.example.calculatror.controller;

import com.example.calculatror.model.Color;
import com.example.calculatror.model.Iphone;
import com.example.calculatror.model.MacBook;
import com.example.calculatror.model.onetomany.Sklad;
import com.example.calculatror.repo.ColorRepository;
import com.example.calculatror.repo.IphoneRepository;
import com.example.calculatror.repo.MacBookRepository;
import com.example.calculatror.repo.SkladRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/MacBook")
//@PreAuthorize("hasAnyAuthority('ADMIN')")
public class MacBookController {

    @Autowired
    private MacBookRepository MacBookRepository;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private SkladRepository skladRepository;

    @GetMapping("/")
    public  String Index(Model model){
        Iterable<MacBook> macbooks =  MacBookRepository.findAll();
        model.addAttribute("macbooks", macbooks);
        return "MacBook/index";
    }

    @GetMapping("/addView")
    public  String addView(Model model){
        model.addAttribute("macbooks", new MacBook());

        Iterable<Color> colors = colorRepository.findAll();
        model.addAttribute("colors",colors);

        Iterable<Sklad> sklads = skladRepository.findAll();
        model.addAttribute("sklad", sklads);
        return "/MacBook/add";
    }

    @PostMapping("/add")
    public String add1(@ModelAttribute("macbooks") @Valid MacBook posts,
                       BindingResult bindingResult,
                       Model model, @RequestParam String colorName, @RequestParam String skladName)
    {
        if(bindingResult.hasFieldErrors()){
            Iterable<Color> colors = colorRepository.findAll();
            model.addAttribute("colors",colors);

            Iterable<Sklad> sklads = skladRepository.findAll();
            model.addAttribute("sklad", sklads);
            return  "MacBook/add";
        }
        List<Color> colors = colorRepository.findByName(colorName);
        posts.setColor(colors.get(0));

        List<Sklad> sklads = skladRepository.findByName(skladName);
        posts.setSkladmac(sklads.get(0));
        MacBookRepository.save(posts);
        return "redirect:/MacBook/";
    }

    @GetMapping("/search")
    public  String searchMetrhod(
            @RequestParam("title") String title,
            Model model) {
        List<MacBook> macBookList = MacBookRepository.findByTitle(title);
        model.addAttribute("macbooks", macBookList);
        return "MacBook/index";
    }
    @GetMapping("/searchContains")
    public  String searchMetrhodContains(
            @RequestParam("title") String title,
            Model model)
    {
        List<MacBook> macBookList = MacBookRepository.findByTitleContains(title);
        model.addAttribute("macbooks", macBookList);
        return "MacBook/index";
    }
    @GetMapping("/{id}")
    public  String read(@PathVariable("id") Long id, Model model)
    {
        ArrayList<MacBook> MacBookArray = new ArrayList<>();
        Optional<MacBook> MacBookPost = MacBookRepository.findById(id);
        MacBookPost.ifPresent(MacBookArray::add);
        model.addAttribute("macbooks", MacBookArray);
        return "/MacBook/mac-info";
    }
    @GetMapping("/mac-del/{id}")
    public  String delete(@PathVariable("id") Long id, Model model)
    {
        MacBookRepository.deleteById(id);
        return "redirect:/MacBook/";
    }
    @GetMapping("/mac-edit/{id}")
    public  String edit(@PathVariable("id") Long id, Model model)
    {
        if (!MacBookRepository.existsById(id))
        {
            return "redirect:/MacBook/";
        }
        ArrayList<MacBook> MacBookArray = new ArrayList<>();
        Optional<MacBook> MacBookPost = MacBookRepository.findById(id);
        MacBookPost.ifPresent(MacBookArray::add);
        model.addAttribute("macbooks", MacBookArray);

        Iterable<Color> colors = colorRepository.findAll();
        model.addAttribute("colors",colors);

        Iterable<Sklad> sklads = skladRepository.findAll();
        model.addAttribute("sklad", sklads);
        return "/MacBook/mac-edit";
    }

    @PostMapping("/mac-edit/{id}")
    public  String editpost(@PathVariable("id") Long id, Model model,
                            @ModelAttribute("macbooks") @Valid MacBook edit,
                            BindingResult bindingResul, @RequestParam String colorName, @RequestParam String skladName
    )
    {
        if(!MacBookRepository.existsById(id))
        {
            return "redirect:/MacBook/";
        }
        if(bindingResul.hasErrors())
        {
            Iterable<Color> colors = colorRepository.findAll();
            model.addAttribute("colors",colors);

            Iterable<Sklad> sklads = skladRepository.findAll();
            model.addAttribute("sklad", sklads);
            return "MacBook/mac-edit";
        }
        List<Color> colors = colorRepository.findByName(colorName);
        edit.setColor(colors.get(0));

        List<Sklad> sklads = skladRepository.findByName(skladName);
        edit.setSkladmac(sklads.get(0));
        edit.setId(id);
        MacBookRepository.save(edit);
        return "redirect:/MacBook/";
    }

}
