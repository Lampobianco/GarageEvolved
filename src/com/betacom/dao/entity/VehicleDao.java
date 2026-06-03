package com.betacom.dao.entity;

import java.sql.Date;
import java.util.Map;

import com.betacom.config.DBManager;
import com.betacom.config.SQLConfiguration;
import com.betacom.objects.Vehicle;

public abstract class VehicleDao {

	private final DBManager        db     = new DBManager();
	private final SQLConfiguration config = SQLConfiguration.getInstance();

	// Insert — riceve gli ID delle tabelle lookup e i valori del veicolo
	// Ritorna la PK generata dal DB, che serve al DAO specifico (CarDao ecc.)
	public int insert(Integer typeId, Integer modelId, Integer alimentId, Integer brandId,
					  String color, Integer wheels, Integer gears, Integer year) {
		return db.save(config.getQuery("update.vehicle.insert"), true,
				typeId, modelId, alimentId, brandId, color, wheels, gears,
				Date.valueOf(year + "-01-01"));
	}

	public int delete(Integer id) {
		return db.save(config.getQuery("update.vehicle.delete"), false, id);
	}

	// Metodo condiviso che popola i campi comuni di Vehicle
	// Usato da CarDao, MotorbikeDao, BikeDao per non riscrivere lo stesso codice
	protected void fillVehicleFields(Vehicle v, Map<String, Object> row) {
		v.setId((Integer) row.get("id_vehicle"));
		v.setVehicleType((String) row.get("vehicle_type_name"));
		v.setModel((String) row.get("vehicle_model_name"));
		v.setAlimentationType((String) row.get("vehicle_alimentation_type_name"));
		v.setBrand((String) row.get("vehicle_brand_name"));
		v.setColor((String) row.get("color"));
		v.setNumberOfWheels((Integer) row.get("number_of_wheels"));
		v.setGears((int) row.get("numbers_of_gears"));
		if (row.get("production_year") != null)
			v.setProductionYear(((Date) row.get("production_year")).toLocalDate().getYear());
	}
}
