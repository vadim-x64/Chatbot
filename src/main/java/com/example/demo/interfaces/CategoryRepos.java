package com.example.demo.interfaces;

import com.example.demo.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepos extends JpaRepository<Category, Long> {}