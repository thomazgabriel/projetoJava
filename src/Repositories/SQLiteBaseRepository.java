package repositories;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class SQLiteBaseRepository {

    private static final String DB_FILE = "database.db";
    private static final String _STRING_CONEXAO_ = "jdbc:sqlite:" + DB_FILE;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro ao carregar o driver SQLite: " + e.getMessage());
        }
    }

    public SQLiteBaseRepository() {
        inicializarBancoSeNecessario();
    }

    protected Connection connect() throws SQLException {
        return DriverManager.getConnection(_STRING_CONEXAO_);
    }

    private void inicializarBancoSeNecessario() {
        File dbFile = new File(DB_FILE);
        boolean bancoExiste = dbFile.exists();

        if (!bancoExiste) {
            try (Connection conn = connect();
                 Statement stmt = conn.createStatement()) {

                System.out.println("Criando novo banco de dados e tabelas...");

                // Criação da tabela "pacientes"
                String criarTabelaPacientes = "CREATE TABLE IF NOT EXISTS pacientes (" +
                        "cpf TEXT PRIMARY KEY," +
                        "nome TEXT NOT NULL," +
                        "dataNascimento TEXT," +
                        "idade INTEGER," +
                        "estado TEXT," +
                        "cidade TEXT" +
                        ");";
                stmt.execute(criarTabelaPacientes);

                // Criação da tabela "testes"
                String criarTabelaTestes = "CREATE TABLE IF NOT EXISTS testes (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "dataTeste TEXT," +
                        "cpfPaciente TEXT," +
                        "resultado TEXT," +
                        "FOREIGN KEY (cpfPaciente) REFERENCES pacientes(cpf)" +
                        ");";
                stmt.execute(criarTabelaTestes);

                // Criação da tabela "obitos"
                String criarTabelaObitos = "CREATE TABLE IF NOT EXISTS obitos (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "dataObito TEXT," +
                        "cpfPaciente TEXT," +
                        "FOREIGN KEY (cpfPaciente) REFERENCES pacientes(cpf)" +
                        ");";
                stmt.execute(criarTabelaObitos);

            } catch (SQLException e) {
                System.err.println("Erro ao inicializar o banco de dados: " + e.getMessage());
            }
        }
    }
}
