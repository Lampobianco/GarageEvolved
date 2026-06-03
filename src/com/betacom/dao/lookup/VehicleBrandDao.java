package com.betacom.dao.lookup;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.betacom.config.DBManager;
import com.betacom.config.SQLConfiguration;
import com.betacom.objects.VehicleBrand;

public class VehicleBrandDao {

	private final DBManager        db     = new DBManager();
	private final SQLConfiguration config = SQLConfiguration.getInstance();

	public List<VehicleBrand> findAll() {
		return db.query(config.getQuery("query.vehicleBrand.findAll"))
				.stream()
				.map(this::build)
				.collect(Collectors.toList());
	}

	public VehicleBrand findById(Integer id) {
		Map<String, Object> row = db.get(config.getQuery("query.vehicleBrand.findById"), id);
		return row == null ? null : build(row);
	}

	private VehicleBrand build(Map<String, Object> row) {
		return VehicleBrand.builder()
				.id((Integer) row.get("id_vehicle_brand"))
				.name((String)  row.get("vehicle_brand_name"))
				.build();
	}
}
