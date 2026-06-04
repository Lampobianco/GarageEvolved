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
				.stream().map(this::build).collect(Collectors.toList());
	}

	public Car findById(Integer id) {
		Map<String, Object> row = db.get(config.getQuery("query.car.findById"), id);
		return row == null ? null : build(row);
	}

	// Ricerca per targa parziale — ILIKE, case-insensitive
	// Il chiamante deve wrappare il testo con % (es. "%" + text + "%")
	public List<Car> searchByPlate(String pattern) {
		return db.query(config.getQuery("query.car.searchByPlate"), pattern)
				.stream().map(this::build).collect(Collectors.toList());
	}

	public List<Car> findByBrand(Integer idBrand) {
		return db.query(config.getQuery("query.car.findByBrand"), idBrand)
				.stream().map(this::build).collect(Collectors.toList());
	}

	public List<Car> findByColor(Integer idColor) {
		return db.query(config.getQuery("query.car.findByColor"), idColor)
				.stream().map(this::build).collect(Collectors.toList());
	}

	public List<Car> findByYear(Integer year) {
		return db.query(config.getQuery("query.car.findByYear"), year)
				.stream().map(this::build).collect(Collectors.toList());
	}

	public int insert(Integer idVehicle, String plate, Integer cc, Integer doors) {
		return db.save(config.getQuery("update.car.insert"), true, idVehicle, plate, cc, doors);
	}

	public int delete(Integer id) {
		return db.save(config.getQuery("update.car.delete"), false, id);
	}

	protected Car build(Map<String, Object> row) {
		Car car = new Car();
		fillVehicleFields(car, row);
		car.setIdCar((Integer)         row.get("id_car"));
		car.setLicensePlate((String)   row.get("licence_plate"));
		car.setCc((Integer)            row.get("cc"));
		car.setNumberOfDoors((Integer) row.get("number_of_doors"));
		return car;
	}
}
