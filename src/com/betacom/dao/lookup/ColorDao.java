package com.betacom.dao.lookup;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.betacom.config.DBManager;
import com.betacom.config.SQLConfiguration;
import com.betacom.objects.Color;

public class ColorDao {

	private final DBManager        db     = new DBManager();
	private final SQLConfiguration config = SQLConfiguration.getInstance();

	public List<Color> findAll() {
		return db.query(config.getQuery("query.color.findAll"))
				.stream().map(this::build).collect(Collectors.toList());
	}

	public Color findById(Integer id) {
		Map<String, Object> row = db.get(config.getQuery("query.color.findById"), id);
		return row == null ? null : build(row);
	}

	private Color build(Map<String, Object> row) {
		return Color.builder()
				.id((Integer) row.get("id_color"))
				.name((String)  row.get("color_name"))
				.build();
	}
}
