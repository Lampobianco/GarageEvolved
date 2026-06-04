package com.betacom.dao.entity;

import java.sql.Date;
import java.util.Map;

import com.betacom.config.DBManager;
import com.betacom.config.SQLConfiguration;
import com.betacom.objects.Vehicle;

public abstract class VehicleDao {

	private final DBManager        db     = new DBManager();
	private final SQLConfiguration config = SQLConfiguration.getInstance();

	// Inserisce nella tabella vehicles — ritorna la PK generata
	// idColor è ora obbligatorio (FK verso colors)
	public int insert(Integer typeId, Integer modelId, Integer alimentId,
					  Integer brandId, Integer colorId, Integer wheels, Integer gears, Integer year) {
		return db.save(config.getQuery("update.vehicle.insert"), true,
				typeId, modelId, alimentId, brandId, colorId, wheels, gears,
				Date.valueOf(year + "-01-01"));
	}

	public int delete(Integer id) {
		return db.save(config.getQuery("update.vehicle.delete"), false, id);
	}

	// Popola i campi comuni di Vehicle leggendo la riga del JOIN
	protected void fillVehicleFields(Vehicle v, Map<String, Object> row) {
		v.setId((Integer)           row.get("id_vehicle"));
		v.setVehicleType((String)   row.get("vehicle_type_name"));
		v.setModel((String)         row.get("vehicle_model_name"));
		v.setAlimentationType((String) row.get("vehicle_alimentation_type_name"));
		v.setBrand((String)         row.get("vehicle_brand_name"));
		v.setColorName((String)     row.get("color_name"));
		v.setNumberOfWheels((Integer) row.get("number_of_wheels"));
		v.setGears((int)            row.get("numbers_of_gears"));
		if (row.get("production_year") != null)
			v.setProductionYear(((Date) row.get("production_year")).toLocalDate().getYear());
	}
}
