package services;

import entities.PacienteEntity;
import repositories.PacienteRepository;
import java.util.List;

public class PacienteService {
    private final PacienteRepository pacienteRepository = new PacienteRepository();

    // Inserir paciente
    public boolean inserirPaciente(PacienteEntity paciente) {
        if (pacienteRepository.buscarPorCpf(paciente.getCpf()) != null) {
            return false; // Já existe paciente com esse CPF
        }
        pacienteRepository.inserir(paciente);
        return true;
    }

    // Remover paciente
    public boolean removerPaciente(String cpf) {
        if (pacienteRepository.buscarPorCpf(cpf) == null) {
            return false; // Não existe paciente com esse CPF
        }
        pacienteRepository.remover(cpf);
        return true;
    }

    // Buscar paciente por CPF
    public PacienteEntity buscarPaciente(String cpf) {
        return pacienteRepository.buscarPorCpf(cpf);
    }

    // Listar todos os pacientes
    public List<PacienteEntity> listarPacientes() {
        return pacienteRepository.listarTodos();
    }

    // Filtrar pacientes por nome, cidade ou estado
    public List<PacienteEntity> filtrarPacientes(String termo) {
        return listarPacientes().stream()
            .filter(p -> p.getNome().toLowerCase().contains(termo.toLowerCase()) ||
                         p.getCidade().toLowerCase().contains(termo.toLowerCase()) ||
                         p.getEstado().toLowerCase().contains(termo.toLowerCase()))
            .toList();
    }

    // Alterar dados do paciente (exceto CPF)
    public boolean alterarPaciente(PacienteEntity pacienteAtualizado) {
        PacienteEntity existente = pacienteRepository.buscarPorCpf(pacienteAtualizado.getCpf());
        if (existente == null) {
            return false;
        }
        pacienteRepository.atualizar(pacienteAtualizado);
        return true;
    }
}