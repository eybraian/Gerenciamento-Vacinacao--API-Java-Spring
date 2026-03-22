package com.dosecerta.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.dosecerta.config.DatabaseConnection;

public class EstatisticaService {

    private DatabaseConnection db = new DatabaseConnection();

    // TOTAL DE DOSES
    public int totalAplicadas() throws Exception {

        String sql = "SELECT COUNT(*) FROM imunizacao";

        try (Connection c = db.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);
        }

        return 0;
    }

    // POR VACINA
    public int totalPorVacina(int idVacina) throws Exception {

        String sql = """
            SELECT COUNT(*)
            FROM imunizacao i
            JOIN dose d ON i.id_dose = d.id_dose
            WHERE d.id_vacina = ?
        """;

        try (Connection c = db.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idVacina);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getInt(1);
        }

        return 0;
    }
}
