package com.betacom.data;

import com.betacom.objects.Car;
import com.betacom.services.VehicleService;
import com.betacom.utils.Printer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CarData {

	public static void load(VehicleService service) {
		log.info("--- Demo auto temporanee: insert → lettura → delete ---");

		// Alfa Romeo Giulia SUV Ibrida / Volkswagen Golf Berlina Benzina
		int id1 = service.insertCar(2, 5,  6, 2,  "Grigio",  4, 7, 2022, "TMP001AA", 2000, 5);
		int id2 = service.insertCar(3, 19, 1, 10, "Bianco",  4, 6, 2021, "TMP002BB", 1500, 5);

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
