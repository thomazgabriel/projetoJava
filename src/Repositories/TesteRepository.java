package repositories;

import entities.TesteEntity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TesteRepository extends SQLiteBaseRepository {

    public TesteRepository() {
        super();
        inicializarTabela();
    }

    private void inicializarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS testes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "dataTeste TEXT," +
                "cpfPaciente TEXT," +
                "resultado TEXT" +
                ");";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserir(TesteEntity teste) {
        String sql = "INSERT INTO testes (dataTeste, cpfPaciente, resultado) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, teste.getDataTeste());
            pstmt.setString(2, teste.getCpfPaciente());
            pstmt.setString(3, teste.getResultado());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TesteEntity> listarTodos() {
        List<TesteEntity> lista = new ArrayList<>();
        String sql = "SELECT dataTeste, cpfPaciente, resultado FROM testes";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new TesteEntity(
                    rs.getString("dataTeste"),
                    rs.getString("cpfPaciente"),
                    rs.getString("resultado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<TesteEntity> buscarPorCpf(String cpfPaciente) {
        List<TesteEntity> lista = new ArrayList<>();
        String sql = "SELECT dataTeste, cpfPaciente, resultado FROM testes WHERE cpfPaciente = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cpfPaciente);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                lista.add(new TesteEntity(
                    rs.getString("dataTeste"),
                    rs.getString("cpfPaciente"),
                    rs.getString("resultado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}