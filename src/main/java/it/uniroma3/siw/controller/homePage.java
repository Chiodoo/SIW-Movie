package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homePage {


    // @GetMapping("/")
    // public String home(Model model) {
    //     return "homePage";
    // }

    @GetMapping("/artistIndex")
    public String homeArtist(Model model) {
        return "artistIndex";
    }

    @GetMapping("/movieIndex")
    public String homeMovie(Model model) {
        return "movieIndex";
    }
}
