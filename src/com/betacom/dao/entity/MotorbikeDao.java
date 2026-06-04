package com.betacom.dao.entity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.ArrayList;
import java.util.List;

import com.betacom.config.DBManager;
import com.betacom.config.SQLConfiguration;
import com.betacom.objects.Motorbike;

public class MotorbikeDao extends VehicleDao {

	private final DBManager        db     = new DBManager();
	private final SQLConfiguration config = SQLConfiguration.getInstance();

	public List<Motorbike> findAll() {
		return db.query(config.getQuery("query.motorbike.findAll"))
				.stream().map(this::build).collect(Collectors.toList());
	}

	public Motorbike findById(Integer id) {
		Map<String, Object> row = db.get(config.getQuery("query.motorbike.findById"), id);
		return row == null ? null : build(row);
	}

	public Motorbike findByVehicleId(Integer vehicleId) {
		Map<String, Object> row = db.get(config.getQuery("query.motorbike.findByVehicleId"), vehicleId);
		return row == null ? null : build(row);
	}

	public Motorbike findByPlate(String plate) {
		Map<String, Object> row = db.get(config.getQuery("query.motorbike.findByPlate"), plate);
		return row == null ? null : build(row);
	}

	public List<Motorbike> searchByPlate(String pattern) {
		return db.query(config.getQuery("query.motorbike.searchByPlate"), pattern)
				.stream().map(this::build).collect(Collectors.toList());
	}

	public List<Motorbike> findByBrand(Integer idBrand) {
		return db.query(config.getQuery("query.motorbike.findByBrand"), idBrand)
				.stream().map(this::build).collect(Collectors.toList());
	}

	// UPDATE DINAMICO — aggiorna solo i campi di motorbikes non-null
	public int update(Integer vehicleId, Motorbike m) {
		List<Object> params = new ArrayList<>();
		List<String> fields = new ArrayList<>();

		if (m.getLicensePlate() != null) { fields.add("licence_plate = ?"); params.add(m.getLicensePlate()); }
		if (m.getCc()           != null) { fields.add("cc = ?");            params.add(m.getCc()); }

		if (fields.isEmpty()) return 0;

		String query = "UPDATE motorbikes SET " + String.join(", ", fields) + " WHERE id_vehicle = ?";
		params.add(vehicleId);
		return db.save(query, false, params.toArray());
	}

	public int insert(Integer idVehicle, String plate, Integer cc) {
		return db.save(config.getQuery("update.motorbike.insert"), true, idVehicle, plate, cc);
	}

	public int delete(Integer id) {
		return db.save(config.getQuery("update.motorbike.delete"), false, id);
	}

	protected Motorbike build(Map<String, Object> row) {
		Motorbike m = new Motorbike();
		fillVehicleFields(m, row);
		m.setIdMotorbike((Integer) row.get("id_motorbike"));
		m.setLicensePlate((String) row.get("licence_plate"));
		m.setCc((Integer)          row.get("cc"));
		return m;
	}
}
