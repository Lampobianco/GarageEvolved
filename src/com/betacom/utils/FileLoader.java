package com.betacom.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.betacom.services.VehicleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileLoader {

    // Legge vehicles_data.txt ed esegue ogni operazione CRUD riga per riga
    public static void load(String filePath, VehicleService service) {
        log.info("Elaborazione file: {}", filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.lines()
                  .map(String::trim)
                  .filter(line -> !line.isEmpty() && !line.startsWith("#"))
                  .forEach(line -> process(line, service));
        } catch (Exception e) {
            log.error("Impossibile leggere il file {}: {}", filePath, e.getMessage());
        }
    }

    // Scompone la riga in 3 parti: OPERAZIONE | TIPO | dati
    // poi esegue l'operazione corrispondente
    private static void process(String line, VehicleService service) {
        String[] parts = line.split("\\|");
        if (parts.length < 3) { log.warn("Riga non valida ignorata: {}", line); return; }

        String op   = parts[0].trim().toUpperCase(); // INS, UPD, DEL
        String type = parts[1].trim().toUpperCase(); // CAR, MOTO, BIKE
        List<String> d = Arrays.stream(parts[2].split(","))
                                .map(String::trim)
                                .collect(Collectors.toList());
        switch (op + "|" + type) {
            case "INS|CAR"  -> service.insertCar(i(d,0),i(d,1),i(d,2),i(d,3),i(d,4),i(d,5),i(d,6),i(d,7),d.get(8),i(d,9),i(d,10));
            case "INS|MOTO" -> service.insertMotorbike(i(d,0),i(d,1),i(d,2),i(d,3),i(d,4),i(d,5),i(d,6),i(d,7),d.get(8),i(d,9));
            case "INS|BIKE" -> service.insertBike(i(d,0),i(d,1),i(d,2),i(d,3),i(d,4),i(d,5),i(d,6),i(d,7),i(d,8),i(d,9),b(d,10));
            case "UPD|CAR"  -> service.updateCarColor(d.get(0), i(d,1));
            case "UPD|MOTO" -> service.updateMotoColor(d.get(0), i(d,1));
            case "DEL|CAR"  -> service.deleteCarByPlate(d.get(0));
            case "DEL|MOTO" -> service.deleteMotoByPlate(d.get(0));
            default         -> log.warn("Operazione non riconosciuta: {}|{}", op, type);
        }
    }

    private static Integer i(List<String> d, int idx) { return Integer.parseInt(d.get(idx)); }
    private static Boolean b(List<String> d, int idx) { return Boolean.parseBoolean(d.get(idx)); }
}
