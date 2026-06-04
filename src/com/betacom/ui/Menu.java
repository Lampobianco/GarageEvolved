package com.betacom.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.betacom.config.SQLConfiguration;
import com.betacom.objects.Bike;
import com.betacom.objects.Car;
import com.betacom.objects.Motorbike;
import com.betacom.services.VehicleService;
import com.betacom.utils.FileLoader;
import com.betacom.utils.Printer;

public class Menu {

    private final Scanner        sc      = new Scanner(System.in);
    private final VehicleService service = new VehicleService();

    private static final String LINE = "─────────────────────────────────────";

    // ─── Avvio ────────────────────────────────────────────────────────────────

    public void start() {
        clear();
        while (true) {
            showMain();
            int scelta = readInt();
            switch (scelta) {
                case 1 -> { addVehicles();   pause(); }
                case 2 -> { updateVehicle(); pause(); }
                case 3 -> { searchVehicle(); pause(); }
                case 4 -> { deleteVehicle(); pause(); }
                case 5 -> { printVehicles(); pause(); }
                case 6 -> exit();
                default -> { msg("   Scelta non valida — scegli un numero tra 1 e 6."); pause(); }
            }
        }
    }

    // ─── Menu principale ──────────────────────────────────────────────────────

    private void showMain() {
        clear();
        System.out.println(LINE);
        System.out.println("   GARAGE EVOLVED");
        System.out.println(LINE);
        System.out.println("   Scegli cosa vuoi fare:\n");
        System.out.println("   1  →  Carica veicoli dal file");
        System.out.println("   2  →  Modifica un veicolo");
        System.out.println("   3  →  Cerca un veicolo");
        System.out.println("   4  →  Elimina un veicolo");
        System.out.println("   5  →  Stampa tutti i veicoli");
        System.out.println("   6  →  Esci");
        System.out.println(LINE);
        System.out.print("   Scelta: ");
    }

    // ─── 1 · Carica dal file ──────────────────────────────────────────────────

    private void addVehicles() {
        clear();
        System.out.println(LINE);
        System.out.println("   CARICA VEICOLI");
        System.out.println(LINE);
        service.clearAllVehicles();
        FileLoader.load("data-files/vehicles_data.txt", service);
        msg("Veicoli caricati correttamente.");
    }

    // ─── 2 · Modifica ─────────────────────────────────────────────────────────

    private void updateVehicle() {
        clear();
        List<Object> list = showVehicleList("MODIFICA VEICOLO");
        if (list.isEmpty()) return;
        System.out.print("\n   Numero veicolo da modificare (0 annulla): ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= list.size()) return;

        Object v = list.get(idx);
        clear();
        if      (v instanceof Car c)       updateCarMenu(c);
        else if (v instanceof Motorbike m) updateMotoMenu(m);
        else if (v instanceof Bike b)      updateBikeMenu(b);
    }

    private void updateCarMenu(Car c) {
        System.out.println(LINE);
        System.out.printf("   MODIFICA AUTO — %s %s%n", c.getBrand(), c.getModel());
        System.out.println(LINE);
        System.out.printf("   1  →  Colore       (%s)%n", c.getColorName());
        System.out.printf("   2  →  Anno         (%d)%n", c.getProductionYear());
        System.out.printf("   3  →  Targa        (%s)%n", c.getLicensePlate());
        System.out.printf("   4  →  Cilindrata   (%d cc)%n", c.getCc());
        System.out.printf("   5  →  N. porte     (%d)%n", c.getNumberOfDoors());
        System.out.printf("   6  →  N. marce     (%d)%n", c.getGears());
        System.out.println("   0  →  Annulla");
        System.out.println(LINE);
        System.out.print("   Scelta: ");
        switch (readInt()) {
            case 1 -> { pickColor(); service.updateVehicleColor(c.getId(), readInt()); }
            case 2 -> { System.out.print("   Nuovo anno: "); service.updateVehicleYear(c.getId(), readInt()); }
            case 3 -> { System.out.print("   Nuova targa: "); service.updateCarPlate(c.getId(), sc.nextLine().trim()); }
            case 4 -> { System.out.print("   Nuova cilindrata: "); service.updateCarCc(c.getId(), readInt()); }
            case 5 -> { System.out.print("   N. porte: "); service.updateCarDoors(c.getId(), readInt()); }
            case 6 -> { System.out.print("   N. marce: "); service.updateVehicleGears(c.getId(), readInt()); }
        }
        msg("Aggiornamento eseguito.");
    }

    private void updateMotoMenu(Motorbike m) {
        System.out.println(LINE);
        System.out.printf("   MODIFICA MOTO — %s %s%n", m.getBrand(), m.getModel());
        System.out.println(LINE);
        System.out.printf("   1  →  Colore       (%s)%n", m.getColorName());
        System.out.printf("   2  →  Anno         (%d)%n", m.getProductionYear());
        System.out.printf("   3  →  Targa        (%s)%n", m.getLicensePlate());
        System.out.printf("   4  →  Cilindrata   (%d cc)%n", m.getCc());
        System.out.printf("   5  →  N. marce     (%d)%n", m.getGears());
        System.out.println("   0  →  Annulla");
        System.out.println(LINE);
        System.out.print("   Scelta: ");
        switch (readInt()) {
            case 1 -> { pickColor(); service.updateVehicleColor(m.getId(), readInt()); }
            case 2 -> { System.out.print("   Nuovo anno: "); service.updateVehicleYear(m.getId(), readInt()); }
            case 3 -> { System.out.print("   Nuova targa: "); service.updateMotoPlate(m.getId(), sc.nextLine().trim()); }
            case 4 -> { System.out.print("   Nuova cilindrata: "); service.updateMotoCc(m.getId(), readInt()); }
            case 5 -> { System.out.print("   N. marce: "); service.updateVehicleGears(m.getId(), readInt()); }
        }
        msg("Aggiornamento eseguito.");
    }

    private void updateBikeMenu(Bike b) {
        System.out.println(LINE);
        System.out.printf("   MODIFICA BICI — %s %s%n", b.getBrand(), b.getModel());
        System.out.println(LINE);
        System.out.printf("   1  →  Colore       (%s)%n", b.getColorName());
        System.out.printf("   2  →  Anno         (%d)%n", b.getProductionYear());
        System.out.printf("   3  →  Tipo freno   (%s)%n", b.getBrakeTypeName());
        System.out.printf("   4  →  Sospensione  (%s)%n", b.getSuspensionTypeName());
        System.out.printf("   5  →  Pieghevole   (%s)%n", Boolean.TRUE.equals(b.getFoldable()) ? "Si" : "No");
        System.out.printf("   6  →  N. marce     (%d)%n", b.getGears());
        System.out.println("   0  →  Annulla");
        System.out.println(LINE);
        System.out.print("   Scelta: ");
        switch (readInt()) {
            case 1 -> { pickColor(); service.updateVehicleColor(b.getId(), readInt()); }
            case 2 -> { System.out.print("   Nuovo anno: "); service.updateVehicleYear(b.getId(), readInt()); }
            case 3 -> { pickBrakeType(); service.updateBikeBrakeType(b.getId(), readInt()); }
            case 4 -> { pickSuspension(); service.updateBikeSuspension(b.getId(), readInt()); }
            case 5 -> { System.out.print("   Pieghevole? (s/n): ");
                        service.updateBikeFoldable(b.getId(), sc.nextLine().trim().equalsIgnoreCase("s")); }
            case 6 -> { System.out.print("   N. marce: "); service.updateVehicleGears(b.getId(), readInt()); }
        }
        msg("Aggiornamento eseguito.");
    }

    // ─── 3 · Cerca ────────────────────────────────────────────────────────────

    private void searchVehicle() {
        clear();
        System.out.println(LINE);
        System.out.println("   CERCA VEICOLO");
        System.out.println(LINE);
        System.out.println("   Inserisci targa (auto/moto) oppure ID veicolo:");
        System.out.print("   → ");
        String input = sc.nextLine().trim();

        try {
            // input numerico → cerca per id_vehicle
            int id = Integer.parseInt(input);
            Car car = service.findCarByVehicleId(id);
            if (car != null) { printVehicleSummary(car); return; }
            Motorbike m = service.findMotoByVehicleId(id);
            if (m != null) { printVehicleSummary(m); return; }
            Bike b = service.findBikeByVehicleId(id);
            if (b != null) { printVehicleSummary(b); return; }
            msg("Nessun veicolo con ID: " + id);
        } catch (NumberFormatException e) {
            // input testuale → cerca per targa
            Car car = service.findCarByPlate(input.toUpperCase());
            if (car != null) { printVehicleSummary(car); return; }
            Motorbike m = service.findMotoByPlate(input.toUpperCase());
            if (m != null) { printVehicleSummary(m); return; }
            msg("Nessun veicolo con targa: " + input);
        }
    }

    // ─── 4 · Elimina per ID ───────────────────────────────────────────────────

    private void deleteVehicle() {
        clear();
        System.out.println(LINE);
        System.out.println("   ELIMINA VEICOLO");
        System.out.println(LINE);
        System.out.print("   ID veicolo da eliminare (0 annulla): ");
        int vehicleId = readInt();
        if (vehicleId <= 0) return;

        Car car = service.findCarByVehicleId(vehicleId);
        if (car != null) { service.deleteCar(car); msg("Auto eliminata."); return; }

        Motorbike m = service.findMotoByVehicleId(vehicleId);
        if (m != null) { service.deleteMotorbike(m); msg("Moto eliminata."); return; }

        Bike b = service.findBikeByVehicleId(vehicleId);
        if (b != null) { service.deleteBike(b); msg("Bici eliminata."); return; }

        msg("Nessun veicolo trovato con ID: " + vehicleId);
    }

    // ─── 5 · Stampa veicoli ───────────────────────────────────────────────────

    private void printVehicles() {
        clear();
        System.out.println(LINE);
        System.out.println("   VEICOLI NEL DB");
        System.out.println(LINE);

        var cars   = service.findAllCars();
        var motos  = service.findAllMotorbikes();
        var bikes  = service.findAllBikes();

        if (cars.isEmpty() && motos.isEmpty() && bikes.isEmpty()) {
            msg("Nessun veicolo nel DB. Usa l'opzione 1 per caricarli.");
            return;
        }

        Printer.printCars(cars);
        Printer.printMotorbikes(motos);
        Printer.printBikes(bikes);
    }

    // ─── 6 · Esci ─────────────────────────────────────────────────────────────

    private void exit() {
        SQLConfiguration.getInstance().closeConnection();
        clear();
        System.out.println("   Arrivederci!");
        System.exit(0);
    }

    // ─── Helpers display ──────────────────────────────────────────────────────

    // Mostra la lista numerata di tutti i veicoli nel DB
    private List<Object> showVehicleList(String title) {
        List<Object> all = new ArrayList<>();
        all.addAll(service.findAllCars());
        all.addAll(service.findAllMotorbikes());
        all.addAll(service.findAllBikes());

        System.out.println(LINE);
        System.out.println("   " + title);
        System.out.println(LINE);
        if (all.isEmpty()) { msg("Nessun veicolo nel DB. Carica prima dal file (opzione 1)."); return all; }

        for (int i = 0; i < all.size(); i++) {
            Object v = all.get(i);
            if (v instanceof Car c)
                System.out.printf("   [%2d]  Auto   %-20s  %-8s  %s  (%d)%n",
                    i + 1, c.getBrand() + " " + c.getModel(), c.getColorName(), c.getLicensePlate(), c.getProductionYear());
            else if (v instanceof Motorbike m)
                System.out.printf("   [%2d]  Moto   %-20s  %-8s  %s  (%d)%n",
                    i + 1, m.getBrand() + " " + m.getModel(), m.getColorName(), m.getLicensePlate(), m.getProductionYear());
            else if (v instanceof Bike b)
                System.out.printf("   [%2d]  Bici   %-20s  %-8s  %s  (%d)%n",
                    i + 1, b.getBrand() + " " + b.getModel(), b.getColorName(), b.getVehicleType(), b.getProductionYear());
        }
        System.out.println(LINE);
        return all;
    }

    // Stampa il dettaglio di un singolo veicolo trovato
    private void printVehicleSummary(Object v) {
        System.out.println(LINE);
        if (v instanceof Car c) {
            System.out.printf("   AUTO  —  %s %s%n", c.getBrand(), c.getModel());
            System.out.printf("   ID: %-5d  Colore: %-10s  Anno: %d%n", c.getId(), c.getColorName(), c.getProductionYear());
            System.out.printf("   Targa: %-10s  Cilindrata: %d cc   Porte: %d%n", c.getLicensePlate(), c.getCc(), c.getNumberOfDoors());
            System.out.printf("   Tipo: %-10s  Alimentaz.: %s%n", c.getVehicleType(), c.getAlimentationType());
        } else if (v instanceof Motorbike m) {
            System.out.printf("   MOTO  —  %s %s%n", m.getBrand(), m.getModel());
            System.out.printf("   ID: %-5d  Colore: %-10s  Anno: %d%n", m.getId(), m.getColorName(), m.getProductionYear());
            System.out.printf("   Targa: %-10s  Cilindrata: %d cc%n", m.getLicensePlate(), m.getCc());
            System.out.printf("   Tipo: %-10s  Alimentaz.: %s%n", m.getVehicleType(), m.getAlimentationType());
        } else if (v instanceof Bike b) {
            System.out.printf("   BICI  —  %s %s%n", b.getBrand(), b.getModel());
            System.out.printf("   ID: %-5d  Colore: %-10s  Anno: %d%n", b.getId(), b.getColorName(), b.getProductionYear());
            System.out.printf("   Tipo: %-12s  Freni: %-15s  Sospensioni: %s%n", b.getVehicleType(), b.getBrakeTypeName(), b.getSuspensionTypeName());
            System.out.printf("   Marce: %-3d  Pieghevole: %s%n", b.getGears(), Boolean.TRUE.equals(b.getFoldable()) ? "Si" : "No");
        }
        System.out.println(LINE);
    }

    // Mostra i colori disponibili e chiede la scelta
    private void pickColor() {
        System.out.println("\n   Colori disponibili:");
        service.findAllColors().forEach(c -> System.out.printf("   %-2d  %s%n", c.getId(), c.getName()));
        System.out.print("   Scegli colore: ");
    }

    private void pickBrakeType() {
        System.out.println("\n   Tipi di freno:");
        service.findAllBrakeTypes().forEach(b -> System.out.printf("   %-2d  %s%n", b.getId(), b.getName()));
        System.out.print("   Scegli tipo freno: ");
    }

    private void pickSuspension() {
        System.out.println("\n   Tipi di sospensione:");
        service.findAllSuspensions().forEach(s -> System.out.printf("   %-2d  %s%n", s.getId(), s.getName()));
        System.out.print("   Scegli sospensione: ");
    }

    // ─── Utilities ────────────────────────────────────────────────────────────

    private int readInt() {
        try { return Integer.parseInt(sc.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    private void msg(String text) {
        System.out.println("\n   " + text);
    }

    private void pause() {
        System.out.print("\n   Premi INVIO per continuare...");
        sc.nextLine();
    }

    private void clear() {
        // Stampa righe vuote per "pulire" la console
        for (int i = 0; i < 3; i++) System.out.println();
    }
}
