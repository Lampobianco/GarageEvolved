package com.betacom.dao.lookup;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.betacom.config.DBManager;
import com.betacom.config.SQLConfiguration;
import com.betacom.objects.VehicleModel;

public class VehicleModelDao {

	private final DBManager        db     = new DBManager();
	private final SQLConfiguration config = SQLConfiguration.getInstance();

	public List<VehicleModel> findAll() {
		return db.query(config.getQuery("query.vehicleModel.findAll"))
				.stream()
				.map(this::build)
				.collect(Collectors.toList());
	}

	public VehicleModel findById(Integer id) {
		Map<String, Object> row = db.get(config.getQuery("query.vehicleModel.findById"), id);
		return row == null ? null : build(row);
	}

	// Filtra i modelli per marca — utile per popolare dropdown nell'interfaccia
	public List<VehicleModel> findByBrand(Integer idBrand) {
		return db.query(config.getQuery("query.vehicleModel.findByBrand"), idBrand)
				.stream()
				.map(this::build)
				.collect(Collectors.toList());
	}

	private VehicleModel build(Map<String, Object> row) {
		return VehicleModel.builder()
				.id((Integer) row.get("id_vehicle_model"))
				.name((String)  row.get("vehicle_model_name"))
				.idBrand((Integer) row.get("id_vehicle_brand"))
				.build();
	}
}
