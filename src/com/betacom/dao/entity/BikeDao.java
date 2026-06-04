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
				.stream().map(this::build).collect(Collectors.toList());
	}

	public Bike findById(Integer id) {
		Map<String, Object> row = db.get(config.getQuery("query.bike.findById"), id);
		return row == null ? null : build(row);
	}

	public Bike findByVehicleId(Integer vehicleId) {
		Map<String, Object> row = db.get(config.getQuery("query.bike.findByVehicleId"), vehicleId);
		return row == null ? null : build(row);
	}

	public List<Bike> findByType(Integer idType) {
		return db.query(config.getQuery("query.bike.findByType"), idType)
				.stream().map(this::build).collect(Collectors.toList());
	}

	public List<Bike> findByBrand(Integer idBrand) {
		return db.query(config.getQuery("query.bike.findByBrand"), idBrand)
				.stream().map(this::build).collect(Collectors.toList());
	}

	// idBrakeType e idSuspensionType sono FK verso le rispettive tabelle lookup
	public int insert(Integer idVehicle, Integer idBrakeType, Integer idSuspensionType, Boolean foldable) {
		return db.save(config.getQuery("update.bike.insert"), true,
				idVehicle, idBrakeType, idSuspensionType, foldable);
	}

	public int delete(Integer id) {
		return db.save(config.getQuery("update.bike.delete"), false, id);
	}

	protected Bike build(Map<String, Object> row) {
		Bike b = new Bike();
		fillVehicleFields(b, row);
		b.setIdBike((Integer)             row.get("id_bike"));
		b.setFoldable((Boolean)           row.get("foldable"));
		b.setBrakeTypeName((String)       row.get("brake_type_name"));
		b.setSuspensionTypeName((String)  row.get("suspension_type_name"));
		return b;
	}
}
