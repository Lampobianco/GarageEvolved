package com.betacom.dao.entity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.betacom.config.DBManager;
import com.betacom.config.SQLConfiguration;
import com.betacom.objects.Bike;

public class BikeDao extends VehicleDao {

	private final DBManager        db     = new DBManager();
	private final SQLConfiguration config = SQLConfiguration.getInstance();

	public List<Bike> findAll() {
		return db.query(config.getQuery("query.bike.findAll"))
				.stream()
				.map(this::build)
				.collect(Collectors.toList());
	}

	public Bike findById(Integer id) {
		Map<String, Object> row = db.get(config.getQuery("query.bike.findById"), id);
		return row == null ? null : build(row);
	}

	public int insert(Integer idVehicle, String brakeType, String suspensionType, Boolean foldable) {
		return db.save(config.getQuery("update.bike.insert"), true, idVehicle, brakeType, suspensionType, foldable);
	}

	public int delete(Integer id) {
		return db.save(config.getQuery("update.bike.delete"), false, id);
	}

	protected Bike build(Map<String, Object> row) {
		Bike b = new Bike();
		fillVehicleFields(b, row);
		b.setIdBike((Integer)        row.get("id_bike"));
		b.setBrakeType((String)      row.get("brake_type"));
		b.setSuspensionType((String) row.get("suspension_type"));
		b.setFoldable((Boolean)      row.get("foldable"));
		return b;
	}
}
