package com.project.BDs.Repositories;

import com.project.BDs.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IngredientsRepository extends JpaRepository<Ingredients, Long> {
}
