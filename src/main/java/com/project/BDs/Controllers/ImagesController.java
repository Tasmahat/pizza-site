package com.project.BDs.Controllers;

import com.project.BDs.Images;
import com.project.BDs.Ingredients;
import com.project.BDs.Pizzas;
import com.project.BDs.Services.ImagesService;
import com.project.BDs.Services.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ImagesController {
    private final ImagesService imagesService;

    @Autowired
    public ImagesController(ImagesService imagesService) {
        this.imagesService = imagesService;
    }

    @GetMapping("/pizzas")
    public Page<Images> getAllImages() {
        return imagesService.getAllImages();
    }


    @GetMapping("/{id}")
    public Images getImageById(@PathVariable Long id) {
        return imagesService.getImageById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ADDER')")
    @PostMapping
    public ResponseEntity<Images> upload(@RequestParam MultipartFile file) {
        System.out.println("blahlbd");
        try {
            return new ResponseEntity<>(imagesService.uploadImage(file), HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
