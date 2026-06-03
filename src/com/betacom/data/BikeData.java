package com.betacom.data;

import com.betacom.services.VehicleService;
import com.betacom.utils.Printer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BikeData {

	public static void load(VehicleService service) {
		log.info("Caricamento dati bici...");

		int id1 = service.insertBike(6, 11, 5, 7,  "Verde",  2, 0, 2023, "Disco",   "Full",      false);
		int id2 = service.insertBike(6, 12, 5, 12, "Bianca", 2, 0, 2022, "V-Brake", "Anteriore", true);

		if (id1 > 0) Printer.printBike(service.findBikeById(id1));
		if (id2 > 0) Printer.printBike(service.findBikeById(id2));
	}
}
