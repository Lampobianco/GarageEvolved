package com.betacom.dao.lookup;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.betacom.config.DBManager;
import com.betacom.config.SQLConfiguration;
import com.betacom.objects.VehicleType;

public class VehicleTypeDao {

	private final DBManager         db     = new DBManager();
	private final SQLConfiguration  config = SQLConfiguration.getInstance();

	public List<VehicleType> findAll() {
		return db.query(config.getQuery("query.vehicleType.findAll"))
				.stream()
				.map(this::build)
				.collect(Collectors.toList());
	}

	public VehicleType findById(Integer id) {
		Map<String, Object> row = db.get(config.getQuery("query.vehicleType.findById"), id);
		return row == null ? null : build(row);
	}

	// Converte una riga del DB nell'oggetto Java corrispondente
	private VehicleType build(Map<String, Object> row) {
		return VehicleType.builder()
				.id((Integer) row.get("id_vehicle_type"))
				.name((String)  row.get("vehicle_type_name"))
				.build();
	}
}
