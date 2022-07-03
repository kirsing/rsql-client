package com.example.demo_project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@Data
@AllArgsConstructor

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clients_id")
    private Long id;
    @Column(name = "clients_name")
    private String name;
    @Column(name = "clients_number")
    private String number;
    @Column(name = "clients_citizenship")
    private String citizenship;
    @JsonFormat(pattern="yyyy-MM-dd,HH:mm")
    @Column(name = "local_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateTimeAppointment;
    @Column(name = "offset_date_time", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime offsetDateTimeAppointment;
}
