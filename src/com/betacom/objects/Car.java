package com.betacom.objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Car extends Vehicle {

	private Integer idCar;         // PK della tabella cars
	private String  licensePlate;  // targa  →  es. "AB123CD", "EF456GH"
	private Integer cc;            // cilindrata in cc  →  es. 1200, 2000, 3500
	private Integer numberOfDoors; // numero di porte  →  es. 2, 3, 4, 5

	@Override
	public String toString() {
		return "Car [" + super.toString() +
			", licensePlate=" + licensePlate +
			", cc=" + cc +
			", numberOfDoors=" + numberOfDoors + "]";
	}
}
