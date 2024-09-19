package com.project.BDs.Controllers;

import com.project.BDs.Ingredients;
import com.project.BDs.Services.IngredientsService;
import com.project.BDs.Services.ImagesService;
import com.project.BDs.Pizzas;
import com.project.BDs.Services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class PizzaController {

    private final PizzaService pizzaService;
    private final IngredientsService ingredientsService;
    private final ImagesService imagesService;

    @Autowired
    public PizzaController(PizzaService pizzaService, IngredientsService ingredientsService, ImagesService imagesService) {
        this.pizzaService = pizzaService;
        this.ingredientsService = ingredientsService;
        this.imagesService = imagesService;
    }

    @GetMapping("/pizzas")
    public Page<Pizzas> getAllPizzas() {
        return pizzaService.getAllPizzas();
    }

    @GetMapping("/pizzas/{id}")
    public Pizzas getPizzasById(@PathVariable Long id) {
        return pizzaService.getPizzaById(id);
    }

    @GetMapping("/page/{pageNumber}")
    public Page<Pizzas> pizzasPageable(@PathVariable int pageNumber) {
        return pizzaService.showByPages(pageNumber);
    }

    @GetMapping("/find/cost/{cost}")
    public Page<Pizzas> pizzasByCost(@PathVariable int cost) {
        return pizzaService.getPizzaByCost(cost);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ADDER')")
    @ResponseBody
    public void createPizza(
            @RequestParam String name,
            @RequestParam int cost,
            @RequestParam List<Long> ingredient_id,
            @RequestParam Long image_id
    ) {
        Set<Ingredients> ingredientsSet = new HashSet<>();
        for (Long iter : ingredient_id) {
            ingredientsSet.add(ingredientsService.getIngredientsById(iter));
        }
        Pizzas pizza = new Pizzas();
        pizza.setName(name);
        pizza.setCost(cost);
        pizza.setIngredientsSet(ingredientsSet);
        pizza.setImage(imagesService.getImageById(image_id));
        pizzaService.createPizza(pizza);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DELETER')")
    @ResponseBody
    public void deletePizza(@RequestParam Long id) {
        pizzaService.deletePizza(id);
    }
}