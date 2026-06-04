package com.betacom.utils;

import java.util.List;

import com.betacom.objects.Bike;
import com.betacom.objects.BrakeType;
import com.betacom.objects.Car;
import com.betacom.objects.Color;
import com.betacom.objects.Motorbike;
import com.betacom.objects.SuspensionType;
import com.betacom.objects.VehicleAlimentationType;
import com.betacom.objects.VehicleBrand;
import com.betacom.objects.VehicleModel;
import com.betacom.objects.VehicleType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Printer {

	private static final String LINE = "─────────────────────────────────────";

	public static void section(String title) {
		log.info("\n{}\n  {}\n{}", LINE, title.toUpperCase(), LINE);
	}

	private static void noResults() {
		log.info("  (nessun risultato)");
	}

	// -------------------------
	// Lookup
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
		list.forEach(m -> log.info("  [{}]  {}  (brand id:{})", m.getId(), m.getName(), m.getIdBrand()));
	}

	public static void printAlimentations(List<VehicleAlimentationType> list) {
		section("Alimentazioni");
		list.forEach(a -> log.info("  [{}]  {}", a.getId(), a.getName()));
	}

	public static void printColors(List<Color> list) {
		section("Colori disponibili");
		list.forEach(c -> log.info("  [{}]  {}", c.getId(), c.getName()));
	}

	public static void printBrakeTypes(List<BrakeType> list) {
		section("Tipi di freno");
		list.forEach(b -> log.info("  [{}]  {}", b.getId(), b.getName()));
	}

	public static void printSuspensions(List<SuspensionType> list) {
		section("Tipi di sospensione");
		list.forEach(s -> log.info("  [{}]  {}", s.getId(), s.getName()));
	}

	// -------------------------
	// Veicoli singoli
	// -------------------------

	public static void printCar(Car c) {
		log.info("\n{}\n  AUTO  —  {} {}\n{}\n"  +
				 "  ID          : {}\n"           +
				 "  Colore      : {}\n"           +
				 "  Targa       : {}\n"           +
				 "  Cilindrata  : {} cc\n"        +
				 "  Porte       : {}\n"           +
				 "  Anno        : {}\n"           +
				 "  Tipo        : {}\n"           +
				 "  Alimentaz.  : {}\n"           +
				 "{}",
			LINE, c.getBrand(), c.getModel(), LINE,
			c.getId(), c.getColorName(), c.getLicensePlate(), c.getCc(),
			c.getNumberOfDoors(), c.getProductionYear(),
			c.getVehicleType(), c.getAlimentationType(), LINE);
	}

	public static void printMotorbike(Motorbike m) {
		log.info("\n{}\n  MOTO  —  {} {}\n{}\n" +
				 "  ID          : {}\n"          +
				 "  Colore      : {}\n"          +
				 "  Targa       : {}\n"          +
				 "  Cilindrata  : {} cc\n"       +
				 "  Anno        : {}\n"          +
				 "  Tipo        : {}\n"          +
				 "  Alimentaz.  : {}\n"          +
				 "{}",
			LINE, m.getBrand(), m.getModel(), LINE,
			m.getId(), m.getColorName(), m.getLicensePlate(), m.getCc(),
			m.getProductionYear(), m.getVehicleType(),
			m.getAlimentationType(), LINE);
	}

	public static void printBike(Bike b) {
		log.info("\n{}\n  BICI  —  {} {}\n{}\n" +
				 "  ID          : {}\n"          +
				 "  Colore      : {}\n"          +
				 "  Tipo        : {}\n"          +
				 "  Freni       : {}\n"          +
				 "  Sospensioni : {}\n"          +
				 "  Marce       : {}\n"          +
				 "  Pieghevole  : {}\n"          +
				 "  Anno        : {}\n"          +
				 "{}",
			LINE, b.getBrand(), b.getModel(), LINE,
			b.getId(), b.getColorName(), b.getVehicleType(),
			b.getBrakeTypeName(), b.getSuspensionTypeName(),
			b.getGears(),
			Boolean.TRUE.equals(b.getFoldable()) ? "Si" : "No",
			b.getProductionYear(), LINE);
	}

	// -------------------------
	// Liste veicoli
	// -------------------------

	public static void printCars(List<Car> list) {
		section("Tutte le auto");
		if (list.isEmpty()) { noResults(); return; }
		list.forEach(Printer::printCar);
	}

	public static void printMotorbikes(List<Motorbike> list) {
		section("Tutte le moto");
		if (list.isEmpty()) { noResults(); return; }
		list.forEach(Printer::printMotorbike);
	}

	public static void printBikes(List<Bike> list) {
		section("Tutte le bici");
		if (list.isEmpty()) { noResults(); return; }
		list.forEach(Printer::printBike);
	}

	// -------------------------
	// Risultati di ricerca
	// -------------------------

	public static void printSearchCars(List<Car> list, String criteria) {
		section("Ricerca auto — " + criteria);
		if (list.isEmpty()) { noResults(); return; }
		list.forEach(Printer::printCar);
	}

	public static void printSearchMotorbikes(List<Motorbike> list, String criteria) {
		section("Ricerca moto — " + criteria);
		if (list.isEmpty()) { noResults(); return; }
		list.forEach(Printer::printMotorbike);
	}

	public static void printSearchBikes(List<Bike> list, String criteria) {
		section("Ricerca bici — " + criteria);
		if (list.isEmpty()) { noResults(); return; }
		list.forEach(Printer::printBike);
	}
}
