package com.betacom.data;

import com.betacom.objects.Motorbike;
import com.betacom.services.VehicleService;
import com.betacom.utils.Printer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MotorbikeData {

	public static void load(VehicleService service) {
		log.info("--- Demo moto temporanee: insert → lettura → delete ---");

		int id1 = service.insertMotorbike(5, 9,  1, 4, "Rossa",  2, 6, 2020, "TMP003CC", 900);
		int id2 = service.insertMotorbike(5, 10, 1, 5, "Nera",   2, 6, 2019, "TMP004DD", 1200);

		Motorbike m1 = service.findMotorbikeById(id1);
		Motorbike m2 = service.findMotorbikeById(id2);
		if (m1 != null) Printer.printMotorbike(m1);
		if (m2 != null) Printer.printMotorbike(m2);

		if (m1 != null) service.deleteMotorbike(m1);
		if (m2 != null) service.deleteMotorbike(m2);

		log.info("--- Moto temporanee rimosse ---");
	}
}
