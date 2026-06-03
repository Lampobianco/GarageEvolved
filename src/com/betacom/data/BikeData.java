package com.betacom.data;

import com.betacom.objects.Bike;
import com.betacom.services.VehicleService;
import com.betacom.utils.Printer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BikeData {

	public static void load(VehicleService service) {
		log.info("--- Demo bici temporanee: insert → lettura → delete ---");

		int id1 = service.insertBike(6, 11, 5, 7,  "Verde",  2, 0, 2023, "Disco",   "Full",      false);
		int id2 = service.insertBike(6, 12, 5, 12, "Bianca", 2, 0, 2022, "V-Brake", "Anteriore", true);

		Bike b1 = service.findBikeById(id1);
		Bike b2 = service.findBikeById(id2);
		if (b1 != null) Printer.printBike(b1);
		if (b2 != null) Printer.printBike(b2);

		if (b1 != null) service.deleteBike(b1);
		if (b2 != null) service.deleteBike(b2);

		log.info("--- Bici temporanee rimosse ---");
	}
}
