package com.betacom.data;

import com.betacom.services.VehicleService;
import com.betacom.utils.Printer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SearchDemo {

	public static void run(VehicleService service) {
		log.info("=== DEMO RICERCHE ===");

		// 1. Ricerca targa parziale — cerca "000" in tutte le auto
		Printer.printSearchCars(
			service.searchCarsByPlate("000"),
			"targa contiene '000'"
		);

		// 2. Ricerca per brand — Fiat (id=1)
		Printer.printSearchCars(
			service.findCarsByBrand(1),
			"brand = Fiat"
		);

		// 3. Ricerca per colore — Rosso (id=1)
		Printer.printSearchCars(
			service.findCarsByColor(1),
			"colore = Rosso"
		);

		// 4. Ricerca per anno — 2022
		Printer.printSearchCars(
			service.findCarsByYear(2022),
			"anno = 2022"
		);

		// 5. Ricerca moto per brand — Ducati (id=21)
		Printer.printSearchMotorbikes(
			service.findMotoByBrand(21),
			"brand = Ducati"
		);

		// 6. Ricerca targa moto parziale — cerca "YA" (trova YA09981 Yamaha)
		Printer.printSearchMotorbikes(
			service.searchMotoByPlate("YA"),
			"targa contiene 'YA'"
		);

		// 7. Ricerca bici per tipo — Bici da Corsa (id=11)
		Printer.printSearchBikes(
			service.findBikesByType(11),
			"tipo = Bici da Corsa"
		);

		// 8. Ricerca bici per brand — Trek (id=28)
		Printer.printSearchBikes(
			service.findBikesByBrand(28),
			"brand = Trek"
		);
	}
}
