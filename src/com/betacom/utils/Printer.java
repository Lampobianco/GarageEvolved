package com.betacom.utils;

import java.util.List;

import com.betacom.objects.Bike;
import com.betacom.objects.Car;
import com.betacom.objects.Motorbike;
import com.betacom.objects.VehicleAlimentationType;
import com.betacom.objects.VehicleBrand;
import com.betacom.objects.VehicleModel;
import com.betacom.objects.VehicleType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Printer {

	// Separatore visivo per dividere le sezioni nel log
	private static final String LINE = "─────────────────────────────────────";

	public static void section(String title) {
		log.info("\n{}\n  {}\n{}", LINE, title.toUpperCase(), LINE);
	}

	// -------------------------
	// Stampa lookup
	// -------------------------

	public static void printTypes(List<VehicleType> list) {
		section("Tipi di veicolo");
		list.forEach(t -> log.info("  [{}]  {}", t.getId(), t.getName()));
	}

	public static void printBrands(List<VehicleBrand> list) {
		section("Brand");
		list.forEach(b -> log.info("  [{}]  {}", b.getId(), b.getName()));
	}

	public static void printModels(List<VehicleModel> list) {
		section("Modelli");
		list.forEach(m -> log.info("  [{}]  {}  (brand id: {})", m.getId(), m.getName(), m.getIdBrand()));
	}

	public static void printAlimentations(List<VehicleAlimentationType> list) {
		section("Alimentazioni");
		list.forEach(a -> log.info("  [{}]  {}", a.getId(), a.getName()));
	}

	// -------------------------
	// Stampa veicoli
	// -------------------------

	public static void printCar(Car c) {
		log.info("\n{}\n  AUTO  —  {} {}\n{}\n"  +
				 "  Colore      : {}\n"           +
				 "  Targa       : {}\n"           +
				 "  Cilindrata  : {} cc\n"        +
				 "  Porte       : {}\n"           +
				 "  Anno        : {}\n"           +
				 "  Tipo        : {}\n"           +
				 "  Alimentaz.  : {}\n"           +
				 "{}",
			LINE, c.getBrand(), c.getModel(), LINE,
			c.getColor(), c.getLicensePlate(), c.getCc(),
			c.getNumberOfDoors(), c.getProductionYear(),
			c.getVehicleType(), c.getAlimentationType(), LINE);
	}

	public static void printMotorbike(Motorbike m) {
		log.info("\n{}\n  MOTO  —  {} {}\n{}\n" +
				 "  Colore      : {}\n"          +
				 "  Targa       : {}\n"          +
				 "  Cilindrata  : {} cc\n"       +
				 "  Anno        : {}\n"          +
				 "  Tipo        : {}\n"          +
				 "  Alimentaz.  : {}\n"          +
				 "{}",
			LINE, m.getBrand(), m.getModel(), LINE,
			m.getColor(), m.getLicensePlate(), m.getCc(),
			m.getProductionYear(), m.getVehicleType(),
			m.getAlimentationType(), LINE);
	}

	public static void printBike(Bike b) {
		log.info("\n{}\n  BICI  —  {} {}\n{}\n" +
				 "  Colore      : {}\n"          +
				 "  Freni       : {}\n"          +
				 "  Sospensioni : {}\n"          +
				 "  Pieghevole  : {}\n"          +
				 "  Anno        : {}\n"          +
				 "{}",
			LINE, b.getBrand(), b.getModel(), LINE,
			b.getColor(), b.getBrakeType(), b.getSuspensionType(),
			Boolean.TRUE.equals(b.getFoldable()) ? "Si" : "No",
			b.getProductionYear(), LINE);
	}

	public static void printCars(List<Car> list) {
		section("Tutte le auto");
		list.forEach(Printer::printCar);
	}

	public static void printMotorbikes(List<Motorbike> list) {
		section("Tutte le moto");
		list.forEach(Printer::printMotorbike);
	}

	public static void printBikes(List<Bike> list) {
		section("Tutte le bici");
		list.forEach(Printer::printBike);
	}
}
