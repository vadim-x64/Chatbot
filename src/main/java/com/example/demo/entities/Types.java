package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "types")
public class Types {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "types", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TypesImages> images;

    @OneToMany(mappedBy = "types", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<VideoPaths> videos;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}