package com.betacom.objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Bike extends Vehicle {

	private Integer numberOfGears;  // numero di marce  →  es. 3, 6, 21
	private String  brakeType;      // tipo di freno  →  es. "Disco", "V-Brake", "Idraulico"
	private String  suspensionType; // tipo di sospensione  →  es. "Rigida", "Anteriore", "Full"
	private Boolean foldable;       // è pieghevole?  →  es. true, false

	@Override
	public String toString() {
		return "Bike [" + super.toString() +
			", numberOfGears=" + numberOfGears +
			", brakeType=" + brakeType +
			", suspensionType=" + suspensionType +
			", foldable=" + foldable + "]";
	}
}
