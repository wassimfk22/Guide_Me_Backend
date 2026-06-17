package com.guide.me.guider.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guide.me.guider.Services.PaysService;
import com.guide.me.guider.entities.Pays;
import com.guide.me.guider.entities.Ville;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping ("/api/pays")
@RequiredArgsConstructor
public class PaysController {
	
	private final PaysService paysService;

    @GetMapping
    public List<Pays> getAllPays() {
        return paysService.getAllPays();
    }

    

}
