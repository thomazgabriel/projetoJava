package services;

import entities.TesteEntity;
import repositories.TesteRepository;
import java.util.List;

public class TesteService {
    private final TesteRepository testeRepository = new TesteRepository();

    // Inserir um novo teste
    public void inserirTeste(TesteEntity teste) {
        testeRepository.inserir(teste);
    }

    // Listar todos os testes
    public List<TesteEntity> listarTestes() {
        return testeRepository.listarTodos();
    }

    // Buscar testes por CPF do paciente
    public List<TesteEntity> buscarTestesPorCpf(String cpfPaciente) {
        return testeRepository.buscarPorCpf(cpfPaciente);
    }
}