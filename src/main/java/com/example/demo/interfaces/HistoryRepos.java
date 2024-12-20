package com.example.demo.interfaces;

import com.example.demo.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepos extends JpaRepository<History, Long> {}