package com.betacom.data;

import com.betacom.objects.Motorbike;
import com.betacom.services.VehicleService;
import com.betacom.utils.Printer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MotorbikeData {

	public static void load(VehicleService service) {
		log.info("--- Demo moto: insert → lettura → delete ---");

		// Kawasaki Ninja ZX-10R Verde / Triumph Speed Triple Nero
		// (typeId, modelId, alimentId, brandId, colorId, ruote, marce, anno, targa, cc)
		// formato targa moto italiana: 2 lettere + 5 numeri  es. KA09981
		int id1 = service.insertMotorbike(5, 37, 1, 22, 6, 2, 6, 2022, "KA09981", 998);
		int id2 = service.insertMotorbike(5, 40, 1, 24, 2, 2, 6, 2021, "TR11601", 1160);

		Motorbike m1 = service.findMotorbikeById(id1);
		Motorbike m2 = service.findMotorbikeById(id2);
		if (m1 != null) Printer.printMotorbike(m1);
		if (m2 != null) Printer.printMotorbike(m2);

		if (m1 != null) service.deleteMotorbike(m1);
		if (m2 != null) service.deleteMotorbike(m2);
		log.info("--- Moto temporanee rimosse ---");
	}
}
