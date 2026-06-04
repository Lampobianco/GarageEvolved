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

    // Carica auto dal file e le inserisce tramite il service
    public static void loadCars(String filePath, VehicleService service) {
        log.info("Caricamento auto da file: {}", filePath);
        readLines(filePath).forEach(params -> service.insertCar(
            i(params, 0),   // typeId
            i(params, 1),   // modelId
            i(params, 2),   // alimentId
            i(params, 3),   // brandId
            i(params, 4),   // colorId
            i(params, 5),   // ruote
            i(params, 6),   // marce
            i(params, 7),   // anno
            s(params, 8),   // targa
            i(params, 9),   // cc
            i(params, 10)   // porte
        ));
    }

    // Carica moto dal file
    public static void loadMotorbikes(String filePath, VehicleService service) {
        log.info("Caricamento moto da file: {}", filePath);
        readLines(filePath).forEach(params -> service.insertMotorbike(
            i(params, 0),   // typeId
            i(params, 1),   // modelId
            i(params, 2),   // alimentId
            i(params, 3),   // brandId
            i(params, 4),   // colorId
            i(params, 5),   // ruote
            i(params, 6),   // marce
            i(params, 7),   // anno
            s(params, 8),   // targa
            i(params, 9)    // cc
        ));
    }

    // Carica bici dal file
    public static void loadBikes(String filePath, VehicleService service) {
        log.info("Caricamento bici da file: {}", filePath);
        readLines(filePath).forEach(params -> service.insertBike(
            i(params, 0),   // typeId
            i(params, 1),   // modelId
            i(params, 2),   // alimentId
            i(params, 3),   // brandId
            i(params, 4),   // colorId
            i(params, 5),   // ruote
            i(params, 6),   // marce
            i(params, 7),   // anno
            i(params, 8),   // idBrakeType
            i(params, 9),   // idSuspType
            b(params, 10)   // foldable
        ));
    }

    // Legge il file riga per riga, salta commenti (#) e righe vuote,
    // restituisce ogni riga come lista di stringhe già splittate e trimmate
    private static List<List<String>> readLines(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty() && !line.startsWith("#"))
                    .map(line -> Arrays.stream(line.split(","))
                                       .map(String::trim)
                                       .collect(Collectors.toList()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Impossibile leggere il file {}: {}", filePath, e.getMessage());
            return List.of();
        }
    }

    // Helper: converte la stringa in Integer
    private static Integer i(List<String> p, int idx) {
        return Integer.parseInt(p.get(idx));
    }

    // Helper: restituisce la stringa così com'è
    private static String s(List<String> p, int idx) {
        return p.get(idx);
    }

    // Helper: converte la stringa in Boolean
    private static Boolean b(List<String> p, int idx) {
        return Boolean.parseBoolean(p.get(idx));
    }
}
