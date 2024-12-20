package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "video_paths")
public class VideoPaths {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "types_id")
    private Types types;

    @ManyToOne
    @JoinColumn(name="car_id")
    private Car car;

    @Column(name = "video_path")
    private String videoPath;
}