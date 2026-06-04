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
	private String  brakeTypeName;      // display — dalla tabella brake_types via JOIN
	private String  suspensionTypeName; // display — dalla tabella suspension_types via JOIN
	private Boolean foldable;

	// Usati solo negli UPDATE dinamici — non popolati nelle SELECT
	private Integer idBrakeType;
	private Integer idSuspensionType;

	@Override
	public String toString() {
		return "Bike [" + super.toString() +
			", brakeType=" + brakeTypeName +
			", suspension=" + suspensionTypeName +
			", foldable=" + foldable + "]";
	}
}
