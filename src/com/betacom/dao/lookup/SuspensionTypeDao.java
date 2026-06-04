package com.betacom.dao.lookup;

import java.util.List;
import java.util.stream.Collectors;

import com.betacom.config.DBManager;
import com.betacom.config.SQLConfiguration;
import com.betacom.objects.SuspensionType;

public class SuspensionTypeDao {

	private final DBManager        db     = new DBManager();
	private final SQLConfiguration config = SQLConfiguration.getInstance();

	public List<SuspensionType> findAll() {
		return db.query(config.getQuery("query.suspensionType.findAll"))
				.stream()
				.map(r -> SuspensionType.builder()
						.id((Integer) r.get("id_suspension_type"))
						.name((String)  r.get("suspension_type_name"))
						.build())
				.collect(Collectors.toList());
	}
}
