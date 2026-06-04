package com.betacom.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.betacom.services.VehicleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileLoader {

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

    private static void process(String line, VehicleService service) {
        String[] parts = line.split("\\|");
        if (parts.length < 3) { log.warn("Riga non valida ignorata: {}", line); return; }

        String op   = parts[0].trim().toUpperCase();
        String type = parts[1].trim().toUpperCase();

        // Parsing: scompone la parte dati in una lista di token già trimmed
        List<String> d = Arrays.stream(parts[2].split(","))
                                .map(String::trim)
                                .collect(Collectors.toList());

        // Map operazione → Consumer<List<String>>
        // Sostituisce il switch — ogni chiave mappa direttamente all'azione da eseguire
        Map<String, Consumer<List<String>>> handlers = Map.of(
            "INS|CAR",  t -> service.insertCar(i(t,0),i(t,1),i(t,2),i(t,3),i(t,4),i(t,5),i(t,6),i(t,7),t.get(8),i(t,9),i(t,10)),
            "INS|MOTO", t -> service.insertMotorbike(i(t,0),i(t,1),i(t,2),i(t,3),i(t,4),i(t,5),i(t,6),i(t,7),t.get(8),i(t,9)),
            "INS|BIKE", t -> service.insertBike(i(t,0),i(t,1),i(t,2),i(t,3),i(t,4),i(t,5),i(t,6),i(t,7),i(t,8),i(t,9),b(t,10)),
            "UPD|CAR",  t -> service.updateCarColor(t.get(0), i(t,1)),
            "UPD|MOTO", t -> service.updateMotoColor(t.get(0), i(t,1)),
            "DEL|CAR",  t -> service.deleteCarByPlate(t.get(0)),
            "DEL|MOTO", t -> service.deleteMotoByPlate(t.get(0))
        );

        Optional.ofNullable(handlers.get(op + "|" + type))
                .ifPresentOrElse(
                    handler -> handler.accept(d),
                    () -> log.warn("Operazione non riconosciuta: {}|{}", op, type)
                );
    }

    private static Integer i(List<String> d, int idx) { return Integer.parseInt(d.get(idx)); }
    private static Boolean b(List<String> d, int idx) { return Boolean.parseBoolean(d.get(idx)); }
}
