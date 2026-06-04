package com.betacom.dao.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.betacom.config.DBManager;
import com.betacom.config.SQLConfiguration;
import com.betacom.objects.Vehicle;

public abstract class VehicleDao {

	private final DBManager        db     = new DBManager();
	private final SQLConfiguration config = SQLConfiguration.getInstance();

	// INSERT nella tabella vehicles
	public int insert(Integer typeId, Integer modelId, Integer alimentId,
					  Integer brandId, Integer colorId, Integer wheels, Integer gears, Integer year) {
		return db.save(config.getQuery("update.vehicle.insert"), true,
				typeId, modelId, alimentId, brandId, colorId, wheels, gears,
				Date.valueOf(year + "-01-01"));
	}

	// DELETE dalla tabella vehicles
	public int delete(Integer id) {
		return db.save(config.getQuery("update.vehicle.delete"), false, id);
	}

	// UPDATE DINAMICO — aggiorna solo i campi di vehicles che sono non-null nell'oggetto
	// Stessa logica del prof: costruisce la SET clause solo con i campi valorizzati
	public int updateVehicleFields(Integer vehicleId, Vehicle v) {
		List<Object> params = new ArrayList<>();
		List<String> fields = new ArrayList<>();

		if (v.getIdColor()        != null) { fields.add("id_color = ?");         params.add(v.getIdColor()); }
		if (v.getProductionYear() != null) { fields.add("production_year = ?");  params.add(Date.valueOf(v.getProductionYear() + "-01-01")); }
		if (v.getGears()          != null) { fields.add("numbers_of_gears = ?"); params.add(v.getGears()); }

		if (fields.isEmpty()) return 0;

		String query = "UPDATE vehicles SET " + String.join(", ", fields) + " WHERE id_vehicle = ?";
		params.add(vehicleId);
		return db.save(query, false, params.toArray());
	}

	// Popola i campi comuni di Vehicle dalla riga del JOIN
	protected void fillVehicleFields(Vehicle v, Map<String, Object> row) {
		v.setId((Integer)              row.get("id_vehicle"));
		v.setVehicleType((String)      row.get("vehicle_type_name"));
		v.setModel((String)            row.get("vehicle_model_name"));
		v.setAlimentationType((String) row.get("vehicle_alimentation_type_name"));
		v.setBrand((String)            row.get("vehicle_brand_name"));
		v.setColorName((String)        row.get("color_name"));
		v.setNumberOfWheels((Integer)  row.get("number_of_wheels"));
		v.setGears((Integer)           row.get("numbers_of_gears"));
		if (row.get("production_year") != null)
			v.setProductionYear(((Date) row.get("production_year")).toLocalDate().getYear());
	}
}
