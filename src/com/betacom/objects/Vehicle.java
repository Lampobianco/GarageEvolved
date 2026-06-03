package com.betacom.objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ToString
@NoArgsConstructor
public abstract class Vehicle {

    private Integer id;              // chiave primaria generata dal DB  →  es. 1, 2, 42

    private String vehicleType;      // tipo veicolo dalla tabella lookup  →  es. "SUV", "Berlina", "Moto"

    private Integer numberOfWheels;  // numero di ruote fisiche  →  es. 4 (auto), 2 (moto), 3 (triciclo)

    private String alimentationType; // carburante dalla tabella lookup  →  es. "Benzina", "Elettrico", "Ibrida"

    private String category;         // categoria merceologica libera  →  es. "Luxury", "Sportiva", "Commerciale"

    private String color;            // colore come stringa libera  →  es. "Rosso", "Nero metallizzato", "Bianco"

    private int gears;               // numero di marce  →  es. 6 (manuale), 8 (automatico), 0 (elettrico)

    private String brand;            // marca dalla tabella lookup  →  es. "Ferrari", "Fiat", "Tesla"

    private Integer productionYear;  // anno di produzione come numero intero  →  es. 2021, 2023, 1998

    private String model;            // modello dalla tabella lookup  →  es. "Panda", "Golf", "Model 3"
}
