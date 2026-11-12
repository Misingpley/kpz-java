package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cameras")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CameraEntity {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "device_type", nullable = false)
    private String deviceType;

    @Column(nullable = false)
    private String status;

    private String room;

    private Integer floor;
}
