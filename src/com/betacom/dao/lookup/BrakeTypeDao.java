package com.betacom.dao.lookup;

import java.util.List;
import java.util.stream.Collectors;

import com.betacom.config.DBManager;
import com.betacom.config.SQLConfiguration;
import com.betacom.objects.BrakeType;

public class BrakeTypeDao {

	private final DBManager        db     = new DBManager();
	private final SQLConfiguration config = SQLConfiguration.getInstance();

	public List<BrakeType> findAll() {
		return db.query(config.getQuery("query.brakeType.findAll"))
				.stream()
				.map(r -> BrakeType.builder()
						.id((Integer) r.get("id_brake_type"))
						.name((String)  r.get("brake_type_name"))
						.build())
				.collect(Collectors.toList());
	}
}
