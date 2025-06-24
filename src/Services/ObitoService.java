package services;

import entities.ObitoEntity;
import repositories.ObitoRepository;
import java.util.List;

public class ObitoService {
    private final ObitoRepository obitoRepository = new ObitoRepository();

    // Inserir um novo óbito
    public void inserirObito(ObitoEntity obito) {
        obitoRepository.inserir(obito);
    }

    // Listar todos os óbitos
    public List<ObitoEntity> listarObitos() {
        return obitoRepository.listarTodos();
    }

    // Buscar óbitos por CPF do paciente
    public List<ObitoEntity> buscarObitosPorCpf(String cpfPaciente) {
        return obitoRepository.buscarPorCpf(cpfPaciente);
    }
}