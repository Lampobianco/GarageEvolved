package com.betacom.data;

import com.betacom.services.VehicleService;
import com.betacom.utils.Printer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CarData {

	// Inserisce auto di test e ne stampa il risultato
	public static void load(VehicleService service) {
		log.info("Caricamento dati auto...");

		// typeId, modelId, alimentId(1-6), brandId, colore, ruote, marce, anno, targa, cc, porte
		int id1 = service.insertCar(1, 1, 1, 1,  "Rosso",  4, 5, 2022, "AB123CD", 1200, 5);
		int id2 = service.insertCar(2, 5, 2, 2,  "Nero",   4, 8, 2023, "EF456GH", 2000, 5);
		int id3 = service.insertCar(3, 7, 6, 7,  "Bianco", 4, 6, 2021, "IL789MN", 1600, 3);

		if (id1 > 0) Printer.printCar(service.findCarById(id1));
		if (id2 > 0) Printer.printCar(service.findCarById(id2));
		if (id3 > 0) Printer.printCar(service.findCarById(id3));
	}
}
