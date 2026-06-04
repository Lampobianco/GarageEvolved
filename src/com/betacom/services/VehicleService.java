package com.betacom.services;

import java.util.List;

import com.betacom.config.SQLConfiguration;
import com.betacom.dao.entity.BikeDao;
import com.betacom.dao.entity.CarDao;
import com.betacom.dao.entity.MotorbikeDao;
import com.betacom.dao.entity.VehicleDao;
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

	private final CarDao                     carDao      = new CarDao();
	private final MotorbikeDao               motoDao     = new MotorbikeDao();
	private final BikeDao                    bikeDao     = new BikeDao();
	private final VehicleDao                 vehicleDao  = new CarDao();
	private final VehicleTypeDao             typeDao     = new VehicleTypeDao();
	private final VehicleBrandDao            brandDao    = new VehicleBrandDao();
	private final VehicleModelDao            modelDao    = new VehicleModelDao();
	private final VehicleAlimentationTypeDao alimentDao  = new VehicleAlimentationTypeDao();
	private final ColorDao                   colorDao    = new ColorDao();
	private final BrakeTypeDao               brakeDao    = new BrakeTypeDao();
	private final SuspensionTypeDao          suspDao     = new SuspensionTypeDao();
	private final SQLConfiguration           config      = SQLConfiguration.getInstance();

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
			int idVehicle = vehicleDao.insert(typeId, modelId, alimentId, brandId, colorId, wheels, gears, year);
			int idCar     = carDao.insert(idVehicle, plate, cc, doors);
			config.commit();
			log.info("Auto inserita — id_vehicle:{} id_car:{}", idVehicle, idCar);
			return idCar;
		} catch (Exception e) {
			config.rollback();
			log.error("Errore inserimento auto: {}", e.getMessage());
			return -1;
		}
	}

	public int insertMotorbike(Integer typeId, Integer modelId, Integer alimentId, Integer brandId, Integer colorId,
							   Integer wheels, Integer gears, Integer year,
							   String plate, Integer cc) {
		try {
			config.setTransaction();
			int idVehicle   = vehicleDao.insert(typeId, modelId, alimentId, brandId, colorId, wheels, gears, year);
			int idMotorbike = motoDao.insert(idVehicle, plate, cc);
			config.commit();
			log.info("Moto inserita — id_vehicle:{} id_motorbike:{}", idVehicle, idMotorbike);
			return idMotorbike;
		} catch (Exception e) {
			config.rollback();
			log.error("Errore inserimento moto: {}", e.getMessage());
			return -1;
		}
	}

	public int insertBike(Integer typeId, Integer modelId, Integer alimentId, Integer brandId, Integer colorId,
						  Integer wheels, Integer gears, Integer year,
						  Integer idBrakeType, Integer idSuspensionType, Boolean foldable) {
		try {
			config.setTransaction();
			int idVehicle = vehicleDao.insert(typeId, modelId, alimentId, brandId, colorId, wheels, gears, year);
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
			vehicleDao.delete(car.getId());
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
			vehicleDao.delete(m.getId());
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
			vehicleDao.delete(b.getId());
			config.commit();
			log.info("Bici eliminata — {} {}", b.getBrand(), b.getModel());
		} catch (Exception e) {
			config.rollback();
			log.error("Errore eliminazione bici: {}", e.getMessage());
		}
	}
}
