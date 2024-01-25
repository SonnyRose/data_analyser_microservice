package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.example.model.measurementType.MeasurementType;



import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "data_analyzer_microservice")
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sensorId;
    private LocalDateTime timeStamp;
    private double measurement;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private MeasurementType measurementType;
}
