package com.project.BDs.Services;

import com.project.BDs.Pizzas;
import com.project.BDs.Repositories.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
public class PizzaService {


    private final PizzaRepository pizzaRepository;


    @Autowired
    public PizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    public Page<Pizzas> getAllPizzas() {
        Pageable pageable = Pageable.unpaged();
        return pizzaRepository.findAllPageable(pageable);
    }

    public Page<Pizzas> showByPages(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber,9, Sort.by("id"));
        return pizzaRepository.findAll(pageable);
    }

    public Pizzas getPizzaById(Long id) {
        return pizzaRepository.findById(id).orElse(null);
    }

    public Page<Pizzas> getPizzaByCost(int cost) {
        Pageable pageable = Pageable.unpaged();
        return pizzaRepository.findAllByCost(cost, pageable);
    }

    public Pizzas createPizza(Pizzas pizza) {
        return pizzaRepository.save(pizza);
    }

    public void deletePizza(Long id) {
        pizzaRepository.deleteById(id);
    }
}
