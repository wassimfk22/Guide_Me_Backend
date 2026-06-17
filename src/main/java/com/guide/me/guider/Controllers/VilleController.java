package com.guide.me.guider.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guide.me.guider.Services.VilleService;
import com.guide.me.guider.entities.Ville;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping ("/api/villes")
@RequiredArgsConstructor
public class VilleController {
	
	private final VilleService villeService;

    @GetMapping("/{id}")
    public Ville getVilleById(@PathVariable Long id) {
        return villeService.getVilleById(id);
    }

}
