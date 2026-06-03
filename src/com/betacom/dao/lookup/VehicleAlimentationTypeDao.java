package com.betacom.dao.lookup;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.betacom.config.DBManager;
import com.betacom.config.SQLConfiguration;
import com.betacom.objects.VehicleAlimentationType;

public class VehicleAlimentationTypeDao {

	private final DBManager        db     = new DBManager();
	private final SQLConfiguration config = SQLConfiguration.getInstance();

	public List<VehicleAlimentationType> findAll() {
		return db.query(config.getQuery("query.vehicleAlimentationType.findAll"))
				.stream()
				.map(this::build)
				.collect(Collectors.toList());
	}

	public VehicleAlimentationType findById(Integer id) {
		Map<String, Object> row = db.get(config.getQuery("query.vehicleAlimentationType.findById"), id);
		return row == null ? null : build(row);
	}

	private VehicleAlimentationType build(Map<String, Object> row) {
		return VehicleAlimentationType.builder()
				.id((Integer) row.get("id_vehicle_alimentation_type"))
				.name((String)  row.get("vehicle_alimentation_type_name"))
				.build();
	}
}
