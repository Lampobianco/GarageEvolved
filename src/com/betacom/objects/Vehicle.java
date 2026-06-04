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

    private Integer id;               // PK di vehicles
    private String  vehicleType;      // es. "SUV", "Berlina", "Moto"
    private String  model;            // es. "Panda", "Golf", "Model 3"
    private String  alimentationType; // es. "Benzina", "Elettrico", "Nessuna"
    private String  brand;            // es. "Ferrari", "Fiat", "Tesla"
    private String  colorName;        // es. "Rosso", "Nero" — dalla tabella colors via JOIN
    private Integer numberOfWheels;   // es. 4, 2
    private int     gears;            // es. 6, 8, 0 (elettrico/bici)
    private Integer productionYear;   // es. 2022, 2019
}
