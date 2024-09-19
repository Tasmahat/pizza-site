package com.project.BDs.Services;

import com.project.BDs.Ingredients;
import com.project.BDs.Repositories.IngredientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class IngredientsService {


    private final IngredientsRepository ingredientsRepository;


    @Autowired
    public IngredientsService(IngredientsRepository ingredientsRepository) {
        this.ingredientsRepository = ingredientsRepository;
    }


    public List<Ingredients> getAllIngredients() {
        return ingredientsRepository.findAll();
    }


    public Ingredients getIngredientsById(Long id) {
        return ingredientsRepository.findById(id).orElse(null);
    }


    public Ingredients createIngredients(Ingredients ingredients) {
        return ingredientsRepository.save(ingredients);
    }


    public Ingredients updateIngredients(Ingredients ingredients) {
        return ingredientsRepository.save(ingredients);
    }


    public void deleteIngredients(Long id) {
        ingredientsRepository.deleteById(id);
    }
}
