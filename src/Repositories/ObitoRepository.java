package repositories;

import entities.ObitoEntity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObitoRepository extends SQLiteBaseRepository {
    public void inserir(ObitoEntity obito) {
        String sql = "INSERT INTO obitos (dataObito, cpfPaciente) VALUES (?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, obito.getDataObito());
            pstmt.setString(2, obito.getCpfPaciente());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ObitoEntity> listarTodos() {
        List<ObitoEntity> lista = new ArrayList<>();
        String sql = "SELECT dataObito, cpfPaciente FROM obitos";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new ObitoEntity(
                    rs.getString("dataObito"),
                    rs.getString("cpfPaciente")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<ObitoEntity> buscarPorCpf(String cpfPaciente) {
        List<ObitoEntity> lista = new ArrayList<>();
        String sql = "SELECT dataObito, cpfPaciente FROM obitos WHERE cpfPaciente = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cpfPaciente);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                lista.add(new ObitoEntity(
                    rs.getString("dataObito"),
                    rs.getString("cpfPaciente")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}