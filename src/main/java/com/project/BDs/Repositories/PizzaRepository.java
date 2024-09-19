package com.project.BDs.Repositories;

import com.project.BDs.Pizzas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface PizzaRepository extends JpaRepository<Pizzas, Long> {
    @Query (
            value = "SELECT * FROM pizzas ORDER BY id_p LIMIT 100000",
            countQuery = "SELECT COUNT(*) FROM pizzas",
            nativeQuery = true
    )
    Page<Pizzas> findAllPageable(Pageable pageable);

    Page<Pizzas> findAllByCost(int cost, Pageable pageable);
}
