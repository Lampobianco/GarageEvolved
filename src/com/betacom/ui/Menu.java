package com.betacom.ui;

import java.util.Map;
import java.util.Optional;
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

    private static final String LINE  = "─────────────────────────────────────";
    private static final String SPACE = "\n\n\n";

    // ─── Avvio ────────────────────────────────────────────────────────────────

    public void start() {
        while (true) {
            System.out.print(SPACE);
            System.out.println(LINE);
            System.out.println("   GARAGE EVOLVED — Scegli cosa vuoi fare:");
            System.out.println(LINE);
            System.out.println("   1  →  Carica veicoli dal file");
            System.out.println("   2  →  Modifica un veicolo");
            System.out.println("   3  →  Cerca un veicolo");
            System.out.println("   4  →  Elimina un veicolo");
            System.out.println("   5  →  Stampa tutti i veicoli");
            System.out.println("   6  →  Esci");
            System.out.println(LINE);
            // Map scelta → azione — nessun switch
            Map<Integer, Runnable> actions = Map.of(
                1, this::addVehicles,
                2, this::updateVehicle,
                3, this::searchVehicle,
                4, this::deleteVehicle,
                5, this::printVehicles,
                6, this::exit
            );
            int scelta = ask("Scelta");
            System.out.print(SPACE);
            Optional.ofNullable(actions.get(scelta))
                    .orElse(() -> System.out.println("   Scelta non valida — scegli da 1 a 6."))
                    .run();
            waitEnter();
        }
    }

    // ─── 1 · Carica ───────────────────────────────────────────────────────────

    private void addVehicles() {
        System.out.println(LINE);
        System.out.println("   CARICA VEICOLI DAL FILE");
        System.out.println(LINE);
        service.clearAllVehicles();
        FileLoader.load("data-files/vehicles_data.txt", service);
        System.out.println("\n   Veicoli caricati.");
    }

    // ─── 2 · Modifica ─────────────────────────────────────────────────────────

    private void updateVehicle() {
        System.out.println(LINE);
        System.out.println("   MODIFICA VEICOLO");
        System.out.println(LINE);
        int vehicleId = ask("ID veicolo da modificare (0 annulla)");
        if (vehicleId <= 0) return;
        System.out.print(SPACE);

        Car car = service.findCarByVehicleId(vehicleId);
        if (car != null)       { updateCarMenu(car);  return; }
        Motorbike m = service.findMotoByVehicleId(vehicleId);
        if (m != null)         { updateMotoMenu(m);   return; }
        Bike b = service.findBikeByVehicleId(vehicleId);
        if (b != null)         { updateBikeMenu(b);   return; }
        System.out.println("   Nessun veicolo con ID: " + vehicleId);
    }

    private void updateCarMenu(Car c) {
        System.out.println(LINE);
        System.out.printf("   MODIFICA — %s %s  (ID %d)%n", c.getBrand(), c.getModel(), c.getId());
        System.out.println(LINE);
        System.out.printf("   1  →  Colore       attuale: %s%n", c.getColorName());
        System.out.printf("   2  →  Anno         attuale: %d%n", c.getProductionYear());
        System.out.printf("   3  →  Targa        attuale: %s%n", c.getLicensePlate());
        System.out.printf("   4  →  Cilindrata   attuale: %d cc%n", c.getCc());
        System.out.printf("   5  →  N. porte     attuale: %d%n", c.getNumberOfDoors());
        System.out.printf("   6  →  N. marce     attuale: %d%n", c.getGears());
        System.out.println("   0  →  Annulla");
        System.out.println(LINE);
        Car upd = new Car();
        // Map campo → lambda che imposta il valore sull'oggetto vuoto
        Map<Integer, Runnable> fields = Map.of(
            1, () -> { printColors(); upd.setIdColor(ask("Nuovo colore (numero)")); },
            2, () -> upd.setProductionYear(ask("Nuovo anno")),
            3, () -> upd.setLicensePlate(askString("Nuova targa")),
            4, () -> upd.setCc(ask("Nuova cilindrata")),
            5, () -> upd.setNumberOfDoors(ask("N. porte")),
            6, () -> upd.setGears(ask("N. marce"))
        );
        int campo = ask("Campo da modificare");
        if (campo == 0) return;
        System.out.print(SPACE);
        Runnable action = fields.get(campo);
        if (action == null) { System.out.println("   Campo non valido."); return; }
        action.run();
        service.updateCar(c.getId(), upd);
        System.out.println("\n   Aggiornamento eseguito.");
    }

    private void updateMotoMenu(Motorbike m) {
        System.out.println(LINE);
        System.out.printf("   MODIFICA — %s %s  (ID %d)%n", m.getBrand(), m.getModel(), m.getId());
        System.out.println(LINE);
        System.out.printf("   1  →  Colore       attuale: %s%n", m.getColorName());
        System.out.printf("   2  →  Anno         attuale: %d%n", m.getProductionYear());
        System.out.printf("   3  →  Targa        attuale: %s%n", m.getLicensePlate());
        System.out.printf("   4  →  Cilindrata   attuale: %d cc%n", m.getCc());
        System.out.printf("   5  →  N. marce     attuale: %d%n", m.getGears());
        System.out.println("   0  →  Annulla");
        System.out.println(LINE);
        int campo = ask("Campo da modificare");
        if (campo == 0) return;
        System.out.print(SPACE);

        Motorbike upd = new Motorbike();
        Map<Integer, Runnable> fields = Map.of(
            1, () -> { printColors(); upd.setIdColor(ask("Nuovo colore (numero)")); },
            2, () -> upd.setProductionYear(ask("Nuovo anno")),
            3, () -> upd.setLicensePlate(askString("Nuova targa")),
            4, () -> upd.setCc(ask("Nuova cilindrata")),
            5, () -> upd.setGears(ask("N. marce"))
        );
        Runnable action = fields.get(campo);
        if (action == null) { System.out.println("   Campo non valido."); return; }
        action.run();
        service.updateMotorbike(m.getId(), upd);
        System.out.println("\n   Aggiornamento eseguito.");
    }

    private void updateBikeMenu(Bike b) {
        System.out.println(LINE);
        System.out.printf("   MODIFICA — %s %s  (ID %d)%n", b.getBrand(), b.getModel(), b.getId());
        System.out.println(LINE);
        System.out.printf("   1  →  Colore       attuale: %s%n", b.getColorName());
        System.out.printf("   2  →  Anno         attuale: %d%n", b.getProductionYear());
        System.out.printf("   3  →  Tipo freno   attuale: %s%n", b.getBrakeTypeName());
        System.out.printf("   4  →  Sospensione  attuale: %s%n", b.getSuspensionTypeName());
        System.out.printf("   5  →  Pieghevole   attuale: %s%n", Boolean.TRUE.equals(b.getFoldable()) ? "Si" : "No");
        System.out.printf("   6  →  N. marce     attuale: %d%n", b.getGears());
        System.out.println("   0  →  Annulla");
        System.out.println(LINE);
        int campo = ask("Campo da modificare");
        if (campo == 0) return;
        System.out.print(SPACE);

        Bike upd = new Bike();
        Map<Integer, Runnable> fields = Map.of(
            1, () -> { printColors(); upd.setIdColor(ask("Nuovo colore (numero)")); },
            2, () -> upd.setProductionYear(ask("Nuovo anno")),
            3, () -> { printBrakeTypes(); upd.setIdBrakeType(ask("Tipo freno (numero)")); },
            4, () -> { printSuspensions(); upd.setIdSuspensionType(ask("Sospensione (numero)")); },
            5, () -> upd.setFoldable(askString("Pieghevole? (s/n)").equalsIgnoreCase("s")),
            6, () -> upd.setGears(ask("N. marce"))
        );
        Runnable action = fields.get(campo);
        if (action == null) { System.out.println("   Campo non valido."); return; }
        action.run();
        service.updateBike(b.getId(), upd);
        System.out.println("\n   Aggiornamento eseguito.");
    }

    // ─── 3 · Cerca ────────────────────────────────────────────────────────────

    private void searchVehicle() {
        System.out.println(LINE);
        System.out.println("   CERCA VEICOLO");
        System.out.println(LINE);
        System.out.println("   Inserisci targa (auto/moto) oppure ID numerico:");
        String input = askString("→");

        try {
            int id = Integer.parseInt(input);
            Car car = service.findCarByVehicleId(id);
            if (car != null) { Printer.printCar(car); return; }
            Motorbike m = service.findMotoByVehicleId(id);
            if (m != null) { Printer.printMotorbike(m); return; }
            Bike b = service.findBikeByVehicleId(id);
            if (b != null) { Printer.printBike(b); return; }
            System.out.println("   Nessun veicolo con ID: " + id);
        } catch (NumberFormatException e) {
            Car car = service.findCarByPlate(input.toUpperCase());
            if (car != null) { Printer.printCar(car); return; }
            Motorbike m = service.findMotoByPlate(input.toUpperCase());
            if (m != null) { Printer.printMotorbike(m); return; }
            System.out.println("   Nessun veicolo con targa: " + input);
        }
    }

    // ─── 4 · Elimina ──────────────────────────────────────────────────────────

    private void deleteVehicle() {
        System.out.println(LINE);
        System.out.println("   ELIMINA VEICOLO");
        System.out.println(LINE);
        int vehicleId = ask("ID veicolo da eliminare (0 annulla)");
        if (vehicleId <= 0) return;

        Car car = service.findCarByVehicleId(vehicleId);
        if (car != null) { service.deleteCar(car); System.out.println("\n   Auto eliminata."); return; }
        Motorbike m = service.findMotoByVehicleId(vehicleId);
        if (m != null) { service.deleteMotorbike(m); System.out.println("\n   Moto eliminata."); return; }
        Bike b = service.findBikeByVehicleId(vehicleId);
        if (b != null) { service.deleteBike(b); System.out.println("\n   Bici eliminata."); return; }
        System.out.println("   Nessun veicolo con ID: " + vehicleId);
    }

    // ─── 5 · Stampa ───────────────────────────────────────────────────────────

    private void printVehicles() {
        var cars  = service.findAllCars();
        var motos = service.findAllMotorbikes();
        var bikes = service.findAllBikes();
        if (cars.isEmpty() && motos.isEmpty() && bikes.isEmpty()) {
            System.out.println("   Nessun veicolo. Usa opzione 1 per caricarli.");
            return;
        }
        Printer.printCars(cars);
        Printer.printMotorbikes(motos);
        Printer.printBikes(bikes);
    }

    // ─── 6 · Esci ─────────────────────────────────────────────────────────────

    private void exit() {
        SQLConfiguration.getInstance().closeConnection();
        System.out.println("   Arrivederci!");
        System.exit(0);
    }

    // ─── Lookup display ───────────────────────────────────────────────────────

    private void printColors() {
        System.out.println("\n   Colori disponibili:");
        service.findAllColors().forEach(c -> System.out.printf("   %-2d  %s%n", c.getId(), c.getName()));
    }
    private void printBrakeTypes() {
        System.out.println("\n   Tipi di freno:");
        service.findAllBrakeTypes().forEach(b -> System.out.printf("   %-2d  %s%n", b.getId(), b.getName()));
    }
    private void printSuspensions() {
        System.out.println("\n   Tipi di sospensione:");
        service.findAllSuspensions().forEach(s -> System.out.printf("   %-2d  %s%n", s.getId(), s.getName()));
    }

    // ─── Input helpers ────────────────────────────────────────────────────────

    // Stampa il prompt, fa il flush e legge un intero
    private int ask(String prompt) {
        System.out.print("   " + prompt + ": ");
        System.out.flush();
        try { return Integer.parseInt(sc.nextLine().trim()); }
        catch (Exception e) { return -1; }
    }

    // Stampa il prompt, fa il flush e legge una stringa
    private String askString(String prompt) {
        System.out.print("   " + prompt + ": ");
        System.out.flush();
        return sc.nextLine().trim();
    }

    // Aspetta che l'utente prema INVIO
    private void waitEnter() {
        System.out.print("\n   Premi INVIO per continuare...");
        System.out.flush();
        sc.nextLine();
    }
}
