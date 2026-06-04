package com.betacom.services;

import java.util.List;

import com.betacom.config.DBManager;
import com.betacom.config.SQLConfiguration;
import com.betacom.dao.entity.BikeDao;
import com.betacom.dao.entity.CarDao;
import com.betacom.dao.entity.MotorbikeDao;
import com.betacom.dao.lookup.BrakeTypeDao;
import com.betacom.dao.lookup.ColorDao;
import com.betacom.dao.lookup.SuspensionTypeDao;
import com.betacom.dao.lookup.VehicleAlimentationTypeDao;
import com.betacom.dao.lookup.VehicleBrandDao;
import com.betacom.dao.lookup.VehicleModelDao;
import com.betacom.dao.lookup.VehicleTypeDao;
import com.betacom.objects.Bike;
import com.betacom.objects.BrakeType;
import com.betacom.objects.Car;
import com.betacom.objects.Color;
import com.betacom.objects.Motorbike;
import com.betacom.objects.SuspensionType;
import com.betacom.objects.VehicleAlimentationType;
import com.betacom.objects.VehicleBrand;
import com.betacom.objects.VehicleModel;
import com.betacom.objects.VehicleType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VehicleService {

	private final DBManager                  db          = new DBManager();
	private final CarDao                     carDao      = new CarDao();
	private final MotorbikeDao               motoDao     = new MotorbikeDao();
	private final BikeDao                    bikeDao     = new BikeDao();
	private final VehicleTypeDao             typeDao     = new VehicleTypeDao();
	private final VehicleBrandDao            brandDao    = new VehicleBrandDao();
	private final VehicleModelDao            modelDao    = new VehicleModelDao();
	private final VehicleAlimentationTypeDao alimentDao  = new VehicleAlimentationTypeDao();
	private final ColorDao                   colorDao    = new ColorDao();
	private final BrakeTypeDao               brakeDao    = new BrakeTypeDao();
	private final SuspensionTypeDao          suspDao     = new SuspensionTypeDao();
	private final SQLConfiguration           config      = SQLConfiguration.getInstance();

	// ─── Operazioni dirette sulla tabella vehicles ──────────────────────────────
	// Metodi privati per evitare il bug di vehicleDao=new CarDao()
	// (CarDao sovrascrive delete() con la versione per la tabella cars)

	private int insertVehicleRow(Integer typeId, Integer modelId, Integer alimentId,
								 Integer brandId, Integer colorId,
								 Integer wheels, Integer gears, Integer year) {
		return db.save(config.getQuery("update.vehicle.insert"), true,
				typeId, modelId, alimentId, brandId, colorId, wheels, gears,
				java.sql.Date.valueOf(year + "-01-01"));
	}

	private void deleteVehicleRow(Integer vehicleId) {
		db.save(config.getQuery("update.vehicle.delete"), false, vehicleId);
	}

	// -------------------------
	// SETUP — gestione dati
	// -------------------------

	// Svuota tutti i veicoli dal DB (rispetta l'ordine FK)
	// Usato all'avvio per ricaricare i dati freschi dai file
	public void clearAllVehicles() {
		try {
			config.setTransaction();
			db.save("DELETE FROM cars",       false);
			db.save("DELETE FROM motorbikes", false);
			db.save("DELETE FROM bikes",      false);
			db.save("DELETE FROM vehicles",   false);
			config.commit();
		} catch (Exception e) {
			config.rollback();
			log.error("Errore svuotamento DB: {}", e.getMessage());
		}
	}

	// -------------------------
	// READ — lista completa
	// -------------------------

	public List<Car>       findAllCars()        { return carDao.findAll();  }
	public List<Motorbike> findAllMotorbikes()   { return motoDao.findAll(); }
	public List<Bike>      findAllBikes()        { return bikeDao.findAll(); }

	public Car       findCarById(Integer id)        { return carDao.findById(id);  }
	public Motorbike findMotorbikeById(Integer id)  { return motoDao.findById(id); }
	public Bike      findBikeById(Integer id)       { return bikeDao.findById(id); }

	// -------------------------
	// READ — lookup
	// -------------------------

	public List<VehicleType>             findAllTypes()         { return typeDao.findAll();    }
	public List<VehicleBrand>            findAllBrands()        { return brandDao.findAll();   }
	public List<VehicleModel>            findAllModels()        { return modelDao.findAll();   }
	public List<VehicleModel>            findModelsByBrand(Integer id) { return modelDao.findByBrand(id); }
	public List<VehicleAlimentationType> findAllAlimentations() { return alimentDao.findAll(); }
	public List<Color>                   findAllColors()        { return colorDao.findAll();   }
	public List<BrakeType>               findAllBrakeTypes()    { return brakeDao.findAll();   }
	public List<SuspensionType>          findAllSuspensions()   { return suspDao.findAll();    }

	// -------------------------
	// FIND per id_vehicle e targa
	// -------------------------

	public Car      findCarByVehicleId(Integer id)  { return carDao.findByVehicleId(id);  }
	public Motorbike findMotoByVehicleId(Integer id) { return motoDao.findByVehicleId(id); }
	public Bike     findBikeByVehicleId(Integer id) { return bikeDao.findByVehicleId(id); }
	public Car      findCarByPlate(String plate)    { return carDao.findByPlate(plate);   }
	public Motorbike findMotoByPlate(String plate)  { return motoDao.findByPlate(plate);  }

	// -------------------------
	// UPDATE campo per campo (tutti usano id_vehicle come chiave)
	// -------------------------

	public void updateVehicleColor(Integer vehicleId, Integer colorId) {
		db.save(config.getQuery("update.vehicle.updateColor"), false, colorId, vehicleId);
	}
	public void updateVehicleYear(Integer vehicleId, Integer year) {
		db.save(config.getQuery("update.vehicle.updateYear"), false,
				java.sql.Date.valueOf(year + "-01-01"), vehicleId);
	}
	public void updateVehicleGears(Integer vehicleId, Integer gears) {
		db.save(config.getQuery("update.vehicle.updateGears"), false, gears, vehicleId);
	}
	public void updateCarPlate(Integer vehicleId, String plate) {
		db.save(config.getQuery("update.car.updatePlate"), false, plate, vehicleId);
	}
	public void updateCarCc(Integer vehicleId, Integer cc) {
		db.save(config.getQuery("update.car.updateCc"), false, cc, vehicleId);
	}
	public void updateCarDoors(Integer vehicleId, Integer doors) {
		db.save(config.getQuery("update.car.updateDoors"), false, doors, vehicleId);
	}
	public void updateMotoPlate(Integer vehicleId, String plate) {
		db.save(config.getQuery("update.motorbike.updatePlate"), false, plate, vehicleId);
	}
	public void updateMotoCc(Integer vehicleId, Integer cc) {
		db.save(config.getQuery("update.motorbike.updateCc"), false, cc, vehicleId);
	}
	public void updateBikeBrakeType(Integer vehicleId, Integer brakeTypeId) {
		db.save(config.getQuery("update.bike.updateBrakeType"), false, brakeTypeId, vehicleId);
	}
	public void updateBikeSuspension(Integer vehicleId, Integer suspTypeId) {
		db.save(config.getQuery("update.bike.updateSuspension"), false, suspTypeId, vehicleId);
	}
	public void updateBikeFoldable(Integer vehicleId, Boolean foldable) {
		db.save(config.getQuery("update.bike.updateFoldable"), false, foldable, vehicleId);
	}

	// -------------------------
	// UPDATE (vecchi metodi da file — aggiornamento colore per targa)
	// -------------------------

	// Aggiorna il colore di un veicolo tramite targa
	public void updateCarColor(String plate, Integer newColorId) {
		Car car = carDao.findByPlate(plate);
		if (car == null) { log.error("Auto non trovata per targa: {}", plate); return; }
		db.save(config.getQuery("update.vehicle.updateColor"), false, newColorId, car.getId());
		log.info("Colore aggiornato — {} (color id: {})", plate, newColorId);
	}

	public void updateMotoColor(String plate, Integer newColorId) {
		Motorbike m = motoDao.findByPlate(plate);
		if (m == null) { log.error("Moto non trovata per targa: {}", plate); return; }
		db.save(config.getQuery("update.vehicle.updateColor"), false, newColorId, m.getId());
		log.info("Colore aggiornato — {} (color id: {})", plate, newColorId);
	}

	// -------------------------
	// DELETE per targa
	// -------------------------

	public void deleteCarByPlate(String plate) {
		Car car = carDao.findByPlate(plate);
		if (car != null) deleteCar(car);
		else log.warn("Auto non trovata per targa: {}", plate);
	}

	public void deleteMotoByPlate(String plate) {
		Motorbike m = motoDao.findByPlate(plate);
		if (m != null) deleteMotorbike(m);
		else log.warn("Moto non trovata per targa: {}", plate);
	}

	// -------------------------
	// RICERCA — auto
	// -------------------------

	// Cerca auto per targa (parziale, case-insensitive)
	// es. searchCarsByPlate("AA") trova "AA000AA", "BAA123"
	public List<Car> searchCarsByPlate(String partial) {
		return carDao.searchByPlate("%" + partial + "%");
	}

	public List<Car> findCarsByBrand(Integer idBrand) { return carDao.findByBrand(idBrand); }
	public List<Car> findCarsByColor(Integer idColor) { return carDao.findByColor(idColor); }
	public List<Car> findCarsByYear(Integer year)     { return carDao.findByYear(year);     }

	// -------------------------
	// RICERCA — moto
	// -------------------------

	public List<Motorbike> searchMotoByPlate(String partial)     { return motoDao.searchByPlate("%" + partial + "%"); }
	public List<Motorbike> findMotoByBrand(Integer idBrand)      { return motoDao.findByBrand(idBrand); }

	// -------------------------
	// RICERCA — bici
	// -------------------------

	public List<Bike> findBikesByType(Integer idType)   { return bikeDao.findByType(idType);   }
	public List<Bike> findBikesByBrand(Integer idBrand) { return bikeDao.findByBrand(idBrand); }

	// -------------------------
	// INSERT
	// -------------------------

	public int insertCar(Integer typeId, Integer modelId, Integer alimentId, Integer brandId, Integer colorId,
						 Integer wheels, Integer gears, Integer year,
						 String plate, Integer cc, Integer doors) {
		try {
			config.setTransaction();
			int idVehicle = insertVehicleRow(typeId, modelId, alimentId, brandId, colorId, wheels, gears, year);
			int idCar     = carDao.insert(idVehicle, plate, cc, doors);
			config.commit();
			log.info("Auto inserita — id_vehicle:{} id_car:{}", idVehicle, idCar);
			return idCar;
		} catch (Exception e) {
			config.rollback();
			if (isDuplicatePlate(e)) log.warn("Targa gia' presente nel DB — auto non caricata: {}", plate);
			else                     log.error("Errore inserimento auto: {}", e.getMessage());
			return -1;
		}
	}

	public int insertMotorbike(Integer typeId, Integer modelId, Integer alimentId, Integer brandId, Integer colorId,
							   Integer wheels, Integer gears, Integer year,
							   String plate, Integer cc) {
		try {
			config.setTransaction();
			int idVehicle   = insertVehicleRow(typeId, modelId, alimentId, brandId, colorId, wheels, gears, year);
			int idMotorbike = motoDao.insert(idVehicle, plate, cc);
			config.commit();
			log.info("Moto inserita — id_vehicle:{} id_motorbike:{}", idVehicle, idMotorbike);
			return idMotorbike;
		} catch (Exception e) {
			config.rollback();
			if (isDuplicatePlate(e)) log.warn("Targa gia' presente nel DB — moto non caricata: {}", plate);
			else                     log.error("Errore inserimento moto: {}", e.getMessage());
			return -1;
		}
	}

	public int insertBike(Integer typeId, Integer modelId, Integer alimentId, Integer brandId, Integer colorId,
						  Integer wheels, Integer gears, Integer year,
						  Integer idBrakeType, Integer idSuspensionType, Boolean foldable) {
		try {
			config.setTransaction();
			int idVehicle = insertVehicleRow(typeId, modelId, alimentId, brandId, colorId, wheels, gears, year);
			int idBike    = bikeDao.insert(idVehicle, idBrakeType, idSuspensionType, foldable);
			config.commit();
			log.info("Bici inserita — id_vehicle:{} id_bike:{}", idVehicle, idBike);
			return idBike;
		} catch (Exception e) {
			config.rollback();
			log.error("Errore inserimento bici: {}", e.getMessage());
			return -1;
		}
	}

	// -------------------------
	// DELETE
	// -------------------------

	public void deleteCar(Car car) {
		try {
			config.setTransaction();
			carDao.delete(car.getIdCar());
			deleteVehicleRow(car.getId());
			config.commit();
			log.info("Auto eliminata — {} {}", car.getBrand(), car.getModel());
		} catch (Exception e) {
			config.rollback();
			log.error("Errore eliminazione auto: {}", e.getMessage());
		}
	}

	public void deleteMotorbike(Motorbike m) {
		try {
			config.setTransaction();
			motoDao.delete(m.getIdMotorbike());
			deleteVehicleRow(m.getId());
			config.commit();
			log.info("Moto eliminata — {} {}", m.getBrand(), m.getModel());
		} catch (Exception e) {
			config.rollback();
			log.error("Errore eliminazione moto: {}", e.getMessage());
		}
	}

	public void deleteBike(Bike b) {
		try {
			config.setTransaction();
			bikeDao.delete(b.getIdBike());
			deleteVehicleRow(b.getId());
			config.commit();
			log.info("Bici eliminata — {} {}", b.getBrand(), b.getModel());
		} catch (Exception e) {
			config.rollback();
			log.error("Errore eliminazione bici: {}", e.getMessage());
		}
	}

	// Controlla se l'eccezione è una violazione UNIQUE sulla targa
	// PostgreSQL usa il codice SQLState "23505" per le chiavi duplicate
	private boolean isDuplicatePlate(Exception e) {
		String msg = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
		return msg.contains("23505") || msg.contains("duplicate key") || msg.contains("unique");
	}
}
