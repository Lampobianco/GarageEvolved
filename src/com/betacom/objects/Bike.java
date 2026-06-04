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

	private Integer idBike;
	private String  brakeTypeName;      // es. "Disco Idraulico", "V-Brake" — dalla tabella brake_types
	private String  suspensionTypeName; // es. "Full", "Rigida" — dalla tabella suspension_types
	private Boolean foldable;           // true = pieghevole

	@Override
	public String toString() {
		return "Bike [" + super.toString() +
			", brakeType=" + brakeTypeName +
			", suspension=" + suspensionTypeName +
			", foldable=" + foldable + "]";
	}
}
