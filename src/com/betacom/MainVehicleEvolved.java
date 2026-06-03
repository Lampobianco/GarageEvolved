package com.betacom;

import com.betacom.config.SQLConfiguration;
import com.betacom.data.BikeData;
import com.betacom.data.CarData;
import com.betacom.data.MotorbikeData;
import com.betacom.services.VehicleService;
import com.betacom.utils.Printer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainVehicleEvolved {

	public static void main(String[] args) {
		log.info("=== GarageEvolved avviato ===");
		VehicleService service = new VehicleService();

		// Stampa le tabelle di lookup
		Printer.printTypes(service.findAllTypes());
		Printer.printBrands(service.findAllBrands());
		Printer.printAlimentations(service.findAllAlimentations());

		// Inserisce e stampa i veicoli di test
		CarData.load(service);
		MotorbikeData.load(service);
		BikeData.load(service);

		// Lista completa finale
		Printer.printCars(service.findAllCars());
		Printer.printMotorbikes(service.findAllMotorbikes());
		Printer.printBikes(service.findAllBikes());

		SQLConfiguration.getInstance().closeConnection();
		log.info("=== GarageEvolved terminato ===");
	}
}
