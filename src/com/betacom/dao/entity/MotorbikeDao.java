package com.betacom.dao.entity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.betacom.config.DBManager;
import com.betacom.config.SQLConfiguration;
import com.betacom.objects.Motorbike;

public class MotorbikeDao extends VehicleDao {

	private final DBManager        db     = new DBManager();
	private final SQLConfiguration config = SQLConfiguration.getInstance();

	public List<Motorbike> findAll() {
		return db.query(config.getQuery("query.motorbike.findAll"))
				.stream()
				.map(this::build)
				.collect(Collectors.toList());
	}

	public Motorbike findById(Integer id) {
		Map<String, Object> row = db.get(config.getQuery("query.motorbike.findById"), id);
		return row == null ? null : build(row);
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
		m.setLicensePlate((String)  row.get("licence_plate"));
		m.setCc((Integer)           row.get("cc"));
		return m;
	}
}
