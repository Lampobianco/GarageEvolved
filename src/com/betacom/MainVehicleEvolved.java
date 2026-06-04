package com.betacom;

import com.betacom.config.SQLConfiguration;
import com.betacom.data.BikeData;
import com.betacom.data.CarData;
import com.betacom.data.MotorbikeData;
import com.betacom.data.SearchDemo;
import com.betacom.services.VehicleService;
import com.betacom.utils.FileLoader;
import com.betacom.utils.Printer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainVehicleEvolved {

	public static void main(String[] args) {
		log.info("=== GarageEvolved avviato ===");
		VehicleService service = new VehicleService();

		// Lookup — tabelle di riferimento
		Printer.printTypes(service.findAllTypes());
		Printer.printBrands(service.findAllBrands());
		Printer.printAlimentations(service.findAllAlimentations());
		Printer.printColors(service.findAllColors());
		Printer.printBrakeTypes(service.findAllBrakeTypes());
		Printer.printSuspensions(service.findAllSuspensions());

		// Svuota il DB e ricarica tutto dal file (INS, UPD, DEL gestiti dal loader)
		service.clearAllVehicles();
		FileLoader.load("data-files/vehicles_data.txt", service);

		// Demo CRUD runtime — inserisce, verifica e cancella veicoli temporanei
		CarData.load(service);
		MotorbikeData.load(service);
		BikeData.load(service);

		// Dati default — rimasti dopo la pulizia dei demo
		Printer.printCars(service.findAllCars());
		Printer.printMotorbikes(service.findAllMotorbikes());
		Printer.printBikes(service.findAllBikes());

		// Demo ricerche
		SearchDemo.run(service);

		SQLConfiguration.getInstance().closeConnection();
		log.info("=== GarageEvolved terminato ===");
	}
}
