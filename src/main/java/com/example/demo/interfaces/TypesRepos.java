package com.example.demo.interfaces;

import com.example.demo.entities.Types;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TypesRepos extends JpaRepository<Types, Long> {
    List<Types> findByName(String typesName);
}