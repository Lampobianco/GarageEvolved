package com.betacom.data;

import com.betacom.objects.Car;
import com.betacom.services.VehicleService;
import com.betacom.utils.Printer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CarData {

	public static void load(VehicleService service) {
		log.info("--- Demo auto temporanee: insert → lettura → delete ---");

		// 1. Inserimento veicoli temporanei
		int id1 = service.insertCar(1, 1,  1, 1,  "Giallo", 4, 5, 2019, "TMP001AA", 900,  5);
		int id2 = service.insertCar(3, 17, 2, 8,  "Grigio", 4, 8, 2021, "TMP002BB", 2000, 4);

		// 2. Lettura e stampa per verifica
		Car c1 = service.findCarById(id1);
		Car c2 = service.findCarById(id2);
		if (c1 != null) Printer.printCar(c1);
		if (c2 != null) Printer.printCar(c2);

		// 3. Cancellazione — il DB torna pulito
		if (c1 != null) service.deleteCar(c1);
		if (c2 != null) service.deleteCar(c2);

		log.info("--- Auto temporanee rimosse ---");
	}
}
