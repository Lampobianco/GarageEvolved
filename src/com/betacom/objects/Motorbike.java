package com.betacom.objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Motorbike extends Vehicle {

	private String  licensePlate; // targa  →  es. "AB123CD", "EF456GH"
	private Integer cc;           // cilindrata in cc  →  es. 500, 900, 1200

	@Override
	public String toString() {
		return "Motorbike [" + super.toString() +
			", licensePlate=" + licensePlate +
			", cc=" + cc + "]";
	}
}
