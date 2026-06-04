package com.betacom.config;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.betacom.exception.AppException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DBManager {

    private final SQLConfiguration config = SQLConfiguration.getInstance();

    // SELECT — chiude sempre stmt e rs con try-with-resources
    public List<Map<String, Object>> query(String sql, Object... params) {
        try (PreparedStatement stmt = config.getConnection().prepareStatement(sql)) {
            setParams(stmt, params);
            log.debug("Query: {}", sql);
            try (ResultSet rs = stmt.executeQuery()) {
                return toList(rs);
            }
        } catch (Exception e) {
            throw new AppException("Query error: " + e.getMessage());
        }
    }

    // SELECT riga singola — usato per findById
    public Map<String, Object> get(String sql, Object... params) {
        List<Map<String, Object>> result = query(sql, params);
        return result.isEmpty() ? null : result.get(0);
    }

    // INSERT / UPDATE / DELETE
    // returnKey = true  → ritorna la primary key generata
    // returnKey = false → ritorna il numero di righe modificate
    public int save(String sql, boolean returnKey, Object... params) {
        int flag = returnKey ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS;
        try (PreparedStatement stmt = config.getConnection().prepareStatement(sql, flag)) {
            setParams(stmt, params);
            stmt.executeUpdate();
            if (returnKey) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) return keys.getInt(1);
                }
            }
            return stmt.getUpdateCount();
        } catch (Exception e) {
            throw new AppException("Save error: " + e.getMessage());
        }
    }

    // Converte un ResultSet in una lista di Map — una Map per ogni riga
    private List<Map<String, Object>> toList(ResultSet rs) throws Exception {
        ResultSetMetaData meta = rs.getMetaData();
        List<Map<String, Object>> rows = new ArrayList<>();
        while (rs.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= meta.getColumnCount(); i++)
                row.put(meta.getColumnLabel(i), rs.getObject(i));
            rows.add(row);
        }
        return rows;
    }

    // Associa ogni parametro al suo segnaposto ? nel PreparedStatement
    private void setParams(PreparedStatement stmt, Object[] params) throws Exception {
        for (int i = 0; i < params.length; i++)
            stmt.setObject(i + 1, params[i]);
    }
}
