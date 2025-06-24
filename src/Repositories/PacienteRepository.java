package repositories;

import entities.PacienteEntity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepository extends SQLiteBaseRepository {
    public void inserir(PacienteEntity paciente) {
        String sql = "INSERT INTO pacientes (cpf, nome, dataNascimento, idade, estado, cidade) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, paciente.getCpf());
            pstmt.setString(2, paciente.getNome());
            pstmt.setString(3, paciente.getDataNascimento());
            pstmt.setInt(4, paciente.getIdade());
            pstmt.setString(5, paciente.getEstado());
            pstmt.setString(6, paciente.getCidade());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(PacienteEntity paciente) {
        String sql = "UPDATE pacientes SET nome = ?, dataNascimento = ?, idade = ?, estado = ?, cidade = ? WHERE cpf = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, paciente.getNome());
            pstmt.setString(2, paciente.getDataNascimento());
            pstmt.setInt(3, paciente.getIdade());
            pstmt.setString(4, paciente.getEstado());
            pstmt.setString(5, paciente.getCidade());
            pstmt.setString(6, paciente.getCpf());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remover(String cpf) {
        String sql = "DELETE FROM pacientes WHERE cpf = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cpf);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PacienteEntity buscarPorCpf(String cpf) {
        String sql = "SELECT cpf, nome, dataNascimento, idade, estado, cidade FROM pacientes WHERE cpf = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cpf);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new PacienteEntity(
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("dataNascimento"),
                    rs.getInt("idade"),
                    rs.getString("estado"),
                    rs.getString("cidade")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PacienteEntity> listarTodos() {
        List<PacienteEntity> lista = new ArrayList<>();
        String sql = "SELECT cpf, nome, dataNascimento, idade, estado, cidade FROM pacientes";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new PacienteEntity(
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("dataNascimento"),
                    rs.getInt("idade"),
                    rs.getString("estado"),
                    rs.getString("cidade")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}