package com.dosecerta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.dosecerta.config.DatabaseConnection;
import com.dosecerta.model.VacinaModel;

@Repository
public class VacinaDAO {

    //CONEXAO AO BANCO
    private DatabaseConnection db = new DatabaseConnection();

    //---CONTRATOS---

    //INSERIR 
    public int inserir(VacinaModel v) throws SQLException {

        String sql = "INSERT INTO vacina (nome_vacina, descricao_vacina, limite_aplicacao, publico_alvo) VALUES (?, ?, ?, ?)";

        try (Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, v.getNome_vacina());
            stmt.setString(2, v.getDescricao_vacina());

            // limite_aplicacao pode ser NULL
            if (v.getLimite_aplicacao() == null) {
                stmt.setNull(3, Types.INTEGER);
            } else {
                stmt.setInt(3, v.getLimite_aplicacao());
            }

            stmt.setString(4, v.getPublico_alvo());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }

        return 0;
    }

    //BUSCAR POR ID
    public VacinaModel buscarPorId(int id) throws SQLException {

        String sql = "SELECT * FROM vacina WHERE id_vacina = ?";

        try (Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Integer limite = rs.getObject("limite_aplicacao") != null
                        ? rs.getInt("limite_aplicacao")
                        : null;

                return new VacinaModel(
                        rs.getInt("id_vacina"),
                        rs.getString("nome_vacina"),
                        rs.getString("descricao_vacina"),
                        limite,
                        rs.getString("publico_alvo")
                );
            }
        }

        return null;
    }

    //LISTAR
    public List<VacinaModel> listar() throws SQLException {

        List<VacinaModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM vacina";

        try (Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Integer limite = rs.getObject("limite_aplicacao") != null
                        ? rs.getInt("limite_aplicacao")
                        : null;

                VacinaModel v = new VacinaModel(
                        rs.getInt("id_vacina"),
                        rs.getString("nome_vacina"),
                        rs.getString("descricao_vacina"),
                        limite,
                        rs.getString("publico_alvo")
                );

                lista.add(v);
            }
        }

        return lista;
    }

    //ATUALIZAR
    public boolean atualizar(int id, VacinaModel v) throws SQLException {

        String sql = "UPDATE vacina SET nome_vacina=?, descricao_vacina=?, limite_aplicacao=?, publico_alvo=? WHERE id_vacina=?";

        try (Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, v.getNome_vacina());
            stmt.setString(2, v.getDescricao_vacina());

            if (v.getLimite_aplicacao() == null) {
                stmt.setNull(3, Types.INTEGER);
            } else {
                stmt.setInt(3, v.getLimite_aplicacao());
            }

            stmt.setString(4, v.getPublico_alvo());
            stmt.setInt(5, id);

            return stmt.executeUpdate() > 0;
        }
    }

    //DELETAR
    public boolean deletar(int id) throws SQLException {

        String sql = "DELETE FROM vacina WHERE id_vacina=?";

        try (Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    //CONSULTAR POR PUBLICO ALVO
    public List<VacinaModel> buscarPorPublicoAlvo(String publico) throws SQLException {

        List<VacinaModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM vacina WHERE publico_alvo = ?";

        try (Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, publico);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Integer limite = rs.getObject("limite_aplicacao") != null
                        ? rs.getInt("limite_aplicacao")
                        : null;

                VacinaModel v = new VacinaModel(
                        rs.getInt("id_vacina"),
                        rs.getString("nome_vacina"),
                        rs.getString("descricao_vacina"),
                        limite,
                        rs.getString("publico_alvo")
                );

                lista.add(v);
            }
        }

        return lista;
    }

    //CONSULTAR POR IDADE EM MESES
    public List<VacinaModel> buscarPorIdadeMaiorQue(int meses) throws SQLException {

        List<VacinaModel> lista = new ArrayList<>();

        String sql = """
            SELECT DISTINCT v.*
            FROM vacina v
            JOIN dose d ON v.id_vacina = d.id_vacina
            WHERE d.idade_recomendada_aplicacao > ?
        """;

        try (Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, meses);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Integer limite = rs.getObject("limite_aplicacao") != null
                        ? rs.getInt("limite_aplicacao")
                        : null;

                VacinaModel v = new VacinaModel(
                        rs.getInt("id_vacina"),
                        rs.getString("nome_vacina"),
                        rs.getString("descricao_vacina"),
                        limite,
                        rs.getString("publico_alvo")
                );

                lista.add(v);
            }
        }

        return lista;
    }

}