package com.example.demo.interfaces;

import com.example.demo.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CarRepos extends JpaRepository<Car, Long> {
    List<Car> findByCategoryName(String category);
}