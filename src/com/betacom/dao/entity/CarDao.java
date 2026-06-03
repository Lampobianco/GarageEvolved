package com.betacom.dao.entity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.betacom.config.DBManager;
import com.betacom.config.SQLConfiguration;
import com.betacom.objects.Car;

public class CarDao extends VehicleDao {

	private final DBManager        db     = new DBManager();
	private final SQLConfiguration config = SQLConfiguration.getInstance();

	public List<Car> findAll() {
		return db.query(config.getQuery("query.car.findAll"))
				.stream()
				.map(this::build)
				.collect(Collectors.toList());
	}

	public Car findById(Integer id) {
		Map<String, Object> row = db.get(config.getQuery("query.car.findById"), id);
		return row == null ? null : build(row);
	}

	// idVehicle è la PK generata da VehicleDao.insert() — deve essere chiamato prima
	public int insert(Integer idVehicle, String plate, Integer cc, Integer doors) {
		return db.save(config.getQuery("update.car.insert"), true, idVehicle, plate, cc, doors);
	}

	public int delete(Integer id) {
		return db.save(config.getQuery("update.car.delete"), false, id);
	}

	// Costruisce un Car completo: prima i campi Vehicle (JOIN), poi quelli specifici
	protected Car build(Map<String, Object> row) {
		Car car = new Car();
		fillVehicleFields(car, row); // metodo ereditato da VehicleDao
		car.setLicensePlate((String)  row.get("licence_plate"));
		car.setCc((Integer)           row.get("cc"));
		car.setNumberOfDoors((Integer) row.get("number_of_doors"));
		return car;
	}
}
