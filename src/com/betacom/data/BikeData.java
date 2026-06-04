package com.betacom.data;

import com.betacom.objects.Bike;
import com.betacom.services.VehicleService;
import com.betacom.utils.Printer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BikeData {

	public static void load(VehicleService service) {
		log.info("--- Demo bici: insert → lettura → delete ---");

		// Bianchi Oltre RC bici da corsa Celeste / Trek Domane city bike Nero
		// (typeId, modelId, alimentId, brandId, colorId, ruote, marce, anno, idBrakeType, idSuspType, foldable)
		int id1 = service.insertBike(11, 52, 7, 30, 9,  2, 11, 2023, 3, 1, false);
		int id2 = service.insertBike(13, 48, 7, 28, 2,  2, 8,  2022, 1, 2, true);

		Bike b1 = service.findBikeById(id1);
		Bike b2 = service.findBikeById(id2);
		if (b1 != null) Printer.printBike(b1);
		if (b2 != null) Printer.printBike(b2);

		if (b1 != null) service.deleteBike(b1);
		if (b2 != null) service.deleteBike(b2);
		log.info("--- Bici temporanee rimosse ---");
	}
}
