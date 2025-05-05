package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.service.ArtistService;
import jakarta.validation.Valid;

@Controller
public class ArtistController {

    @Autowired ArtistService artistService;

    @GetMapping("/artist/{id}")
    public String getArtist(@PathVariable("id") Long id, Model model) {
        model.addAttribute("artist", this.artistService.getArtistById(id));    
        return "artist.html";
    }

    @GetMapping("/artist")
    public String showArtist(Model model) {
        model.addAttribute("artists", this.artistService.getAllArtists());
        return "artists.html";
    }

    @GetMapping("/formNewArtist")
    public String formNewArtist(Model model) {
        model.addAttribute("artist", new Artist());
        return "formNewArtist.html";
    }

    @PostMapping("/artist")
    public String newArtist(@Valid @ModelAttribute("artist") Artist artist,BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {         //Sono emersi errori nel binding
            model.addAttribute("messaggioErroreTitolo", "Campo obbligatorio");
            return "formNewArtist.html";
        } else {
            this.artistService.save(artist);
            model.addAttribute("artist", artist);
            return "redirect:/artist/"+artist.getId();
        }
    }
}
