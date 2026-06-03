package com.betacom.services;

import java.util.List;

import com.betacom.config.SQLConfiguration;
import com.betacom.dao.entity.BikeDao;
import com.betacom.dao.entity.CarDao;
import com.betacom.dao.entity.MotorbikeDao;
import com.betacom.dao.entity.VehicleDao;
import com.betacom.dao.lookup.VehicleAlimentationTypeDao;
import com.betacom.dao.lookup.VehicleBrandDao;
import com.betacom.dao.lookup.VehicleModelDao;
import com.betacom.dao.lookup.VehicleTypeDao;
import com.betacom.objects.Bike;
import com.betacom.objects.Car;
import com.betacom.objects.Motorbike;
import com.betacom.objects.VehicleAlimentationType;
import com.betacom.objects.VehicleBrand;
import com.betacom.objects.VehicleModel;
import com.betacom.objects.VehicleType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VehicleService {

	// DAO istanziati una volta sola e riutilizzati in tutti i metodi
	private final CarDao                    carDao      = new CarDao();
	private final MotorbikeDao              motoDao     = new MotorbikeDao();
	private final BikeDao                   bikeDao     = new BikeDao();
	private final VehicleDao                vehicleDao  = new CarDao(); // usato solo per insert/delete su vehicle
	private final VehicleTypeDao            typeDao     = new VehicleTypeDao();
	private final VehicleBrandDao           brandDao    = new VehicleBrandDao();
	private final VehicleModelDao           modelDao    = new VehicleModelDao();
	private final VehicleAlimentationTypeDao alimentDao = new VehicleAlimentationTypeDao();
	private final SQLConfiguration          config      = SQLConfiguration.getInstance();

	// -------------------------
	// READ — lettura dati
	// -------------------------

	public List<Car>        findAllCars()       { return carDao.findAll();  }
	public List<Motorbike>  findAllMotorbikes()  { return motoDao.findAll(); }
	public List<Bike>       findAllBikes()       { return bikeDao.findAll(); }

	public Car       findCarById(Integer id)       { return carDao.findById(id);  }
	public Motorbike findMotorbikeById(Integer id) { return motoDao.findById(id); }
	public Bike      findBikeById(Integer id)      { return bikeDao.findById(id); }

	// -------------------------
	// READ — lookup
	// -------------------------

	public List<VehicleType>            findAllTypes()        { return typeDao.findAll();    }
	public List<VehicleBrand>           findAllBrands()       { return brandDao.findAll();   }
	public List<VehicleModel>           findAllModels()       { return modelDao.findAll();   }
	public List<VehicleModel>           findModelsByBrand(Integer idBrand) { return modelDao.findByBrand(idBrand); }
	public List<VehicleAlimentationType> findAllAlimentations() { return alimentDao.findAll(); }

	// -------------------------
	// INSERT — inserimento
	// -------------------------

	// Ogni insert usa una transazione:
	// 1. inserisce nella tabella vehicle → ottiene la PK generata
	// 2. inserisce nella tabella specifica (cars/motorbikes/bikes) usando quella PK
	// Se qualcosa va storto → rollback, nulla viene salvato

	public int insertCar(Integer typeId, Integer modelId, Integer alimentId, Integer brandId,
						 String color, Integer wheels, Integer gears, Integer year,
						 String plate, Integer cc, Integer doors) {
		try {
			config.setTransaction();
			int idVehicle = vehicleDao.insert(typeId, modelId, alimentId, brandId, color, wheels, gears, year);
			int idCar     = carDao.insert(idVehicle, plate, cc, doors);
			config.commit();
			log.info("Auto inserita — id_vehicle: {}, id_car: {}", idVehicle, idCar);
			return idCar;
		} catch (Exception e) {
			config.rollback();
			log.error("Errore inserimento auto: {}", e.getMessage());
			return -1;
		}
	}

	public int insertMotorbike(Integer typeId, Integer modelId, Integer alimentId, Integer brandId,
							   String color, Integer wheels, Integer gears, Integer year,
							   String plate, Integer cc) {
		try {
			config.setTransaction();
			int idVehicle    = vehicleDao.insert(typeId, modelId, alimentId, brandId, color, wheels, gears, year);
			int idMotorbike  = motoDao.insert(idVehicle, plate, cc);
			config.commit();
			log.info("Moto inserita — id_vehicle: {}, id_motorbike: {}", idVehicle, idMotorbike);
			return idMotorbike;
		} catch (Exception e) {
			config.rollback();
			log.error("Errore inserimento moto: {}", e.getMessage());
			return -1;
		}
	}

	public int insertBike(Integer typeId, Integer modelId, Integer alimentId, Integer brandId,
						  String color, Integer wheels, Integer gears, Integer year,
						  String brakeType, String suspensionType, Boolean foldable) {
		try {
			config.setTransaction();
			int idVehicle = vehicleDao.insert(typeId, modelId, alimentId, brandId, color, wheels, gears, year);
			int idBike    = bikeDao.insert(idVehicle, brakeType, suspensionType, foldable);
			config.commit();
			log.info("Bici inserita — id_vehicle: {}, id_bike: {}", idVehicle, idBike);
			return idBike;
		} catch (Exception e) {
			config.rollback();
			log.error("Errore inserimento bici: {}", e.getMessage());
			return -1;
		}
	}

	// -------------------------
	// DELETE — cancellazione
	// -------------------------

	// L'ordine è obbligatorio: prima il record specifico (cars/motorbikes/bikes),
	// poi quello padre (vehicle) — altrimenti la FK blocca la cancellazione

	public void deleteCar(Integer idCar, Integer idVehicle) {
		try {
			config.setTransaction();
			carDao.delete(idCar);
			vehicleDao.delete(idVehicle);
			config.commit();
			log.info("Auto eliminata — id_car: {}, id_vehicle: {}", idCar, idVehicle);
		} catch (Exception e) {
			config.rollback();
			log.error("Errore eliminazione auto: {}", e.getMessage());
		}
	}

	public void deleteMotorbike(Integer idMotorbike, Integer idVehicle) {
		try {
			config.setTransaction();
			motoDao.delete(idMotorbike);
			vehicleDao.delete(idVehicle);
			config.commit();
			log.info("Moto eliminata — id_motorbike: {}, id_vehicle: {}", idMotorbike, idVehicle);
		} catch (Exception e) {
			config.rollback();
			log.error("Errore eliminazione moto: {}", e.getMessage());
		}
	}

	public void deleteBike(Integer idBike, Integer idVehicle) {
		try {
			config.setTransaction();
			bikeDao.delete(idBike);
			vehicleDao.delete(idVehicle);
			config.commit();
			log.info("Bici eliminata — id_bike: {}, id_vehicle: {}", idBike, idVehicle);
		} catch (Exception e) {
			config.rollback();
			log.error("Errore eliminazione bici: {}", e.getMessage());
		}
	}
}
