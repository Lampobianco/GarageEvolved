package com.betacom.data;

import com.betacom.services.VehicleService;
import com.betacom.utils.Printer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MotorbikeData {

	public static void load(VehicleService service) {
		log.info("Caricamento dati moto...");

		int id1 = service.insertMotorbike(5, 9,  1, 4, "Rossa", 2, 6, 2021, "QR111ST", 900);
		int id2 = service.insertMotorbike(5, 10, 1, 5, "Nera",  2, 6, 2020, "UV222WX", 1200);

		if (id1 > 0) Printer.printMotorbike(service.findMotorbikeById(id1));
		if (id2 > 0) Printer.printMotorbike(service.findMotorbikeById(id2));
	}
}
