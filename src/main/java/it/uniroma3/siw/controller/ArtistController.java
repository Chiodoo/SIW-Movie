package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.service.ArtistService;

public class ArtistController {

    @Autowired ArtistService artistService;

    @GetMapping("/artist/{id}")
    public String getArtist(@PathVariable("id") Long id, Model model) {
        model.addAttribute("artist",this.artistService.getArtistById(id));
        return "artist.html";
    }

    @GetMapping("/artist")
    public String showArtist(Model model) {
        model.addAttribute("artists", this.artistService.getAllArtist());
        return "artists.html";
    }

}
