import entities.PacienteEntity;
import entities.TesteEntity;
import entities.ObitoEntity;
import models.PacienteModel;
import repositories.PacienteRepository;
import repositories.TesteRepository;
import repositories.ObitoRepository;
import services.PacienteService;
import services.TesteService;
import services.ObitoService;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        PacienteService pacienteService = new PacienteService();
        TesteService testeService = new TesteService();
        ObitoService obitoService = new ObitoService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== MENU COVID-25 ====");
            System.out.println("1. Inserir paciente");
            System.out.println("2. Remover paciente");
            System.out.println("3. Listar pacientes");
            System.out.println("4. Filtrar pacientes");
            System.out.println("5. Alterar paciente");
            System.out.println("6. Inserir teste");
            System.out.println("7. Inserir óbito");
            System.out.println("8. Listar testes");
            System.out.println("9. Listar óbitos");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            int op = Integer.parseInt(scanner.nextLine());

            switch (op) {
                case 1 -> {
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();
                    System.out.print("Data nascimento: ");
                    String dataNasc = scanner.nextLine();
                    System.out.print("Idade: ");
                    int idade = Integer.parseInt(scanner.nextLine());
                    System.out.print("Estado: ");
                    String estado = scanner.nextLine();
                    System.out.print("Cidade: ");
                    String cidade = scanner.nextLine();
                    PacienteEntity paciente = new PacienteEntity(nome, cpf, dataNasc, idade, estado, cidade);
                    if (pacienteService.inserirPaciente(paciente)) {
                        System.out.println("Paciente inserido!");
                    } else {
                        System.out.println("Já existe paciente com esse CPF.");
                    }
                }
                case 2 -> {
                    System.out.print("CPF do paciente a remover: ");
                    String cpf = scanner.nextLine();
                    if (pacienteService.removerPaciente(cpf)) {
                        System.out.println("Paciente removido.");
                    } else {
                        System.out.println("Paciente não encontrado.");
                    }
                }
                case 3 -> {
                    List<PacienteEntity> pacientes = pacienteService.listarPacientes();
                    System.out.println("Pacientes cadastrados:");
                    for (PacienteEntity p : pacientes) {
                        System.out.printf("%s | %s | %s | %d | %s | %s\n",
                                p.getNome(), p.getCpf(), p.getDataNascimento(), p.getIdade(), p.getEstado(), p.getCidade());
                    }
                }
                case 4 -> {
                    System.out.print("Termo para filtrar (nome, cidade ou estado): ");
                    String termo = scanner.nextLine();
                    List<PacienteEntity> filtrados = pacienteService.filtrarPacientes(termo);
                    System.out.println("Resultados:");
                    for (PacienteEntity p : filtrados) {
                        System.out.printf("%s | %s | %s | %d | %s | %s\n",
                                p.getNome(), p.getCpf(), p.getDataNascimento(), p.getIdade(), p.getEstado(), p.getCidade());
                    }
                }
                case 5 -> {
                    System.out.print("CPF do paciente a alterar: ");
                    String cpf = scanner.nextLine();
                    PacienteEntity existente = pacienteService.buscarPaciente(cpf);
                    if (existente == null) {
                        System.out.println("Paciente não encontrado.");
                        break;
                    }
                    System.out.print("Novo nome (" + existente.getNome() + "): ");
                    String nome = scanner.nextLine();
                    System.out.print("Nova data nascimento (" + existente.getDataNascimento() + "): ");
                    String dataNasc = scanner.nextLine();
                    System.out.print("Nova idade (" + existente.getIdade() + "): ");
                    String idadeStr = scanner.nextLine();
                    System.out.print("Novo estado (" + existente.getEstado() + "): ");
                    String estado = scanner.nextLine();
                    System.out.print("Nova cidade (" + existente.getCidade() + "): ");
                    String cidade = scanner.nextLine();
                    PacienteEntity atualizado = new PacienteEntity(
                            nome.isEmpty() ? existente.getNome() : nome,
                            cpf,
                            dataNasc.isEmpty() ? existente.getDataNascimento() : dataNasc,
                            idadeStr.isEmpty() ? existente.getIdade() : Integer.parseInt(idadeStr),
                            estado.isEmpty() ? existente.getEstado() : estado,
                            cidade.isEmpty() ? existente.getCidade() : cidade
                    );
                    if (pacienteService.alterarPaciente(atualizado)) {
                        System.out.println("Paciente alterado.");
                    } else {
                        System.out.println("Erro ao alterar paciente.");
                    }
                }
                case 6 -> {
                    System.out.print("CPF do paciente: ");
                    String cpf = scanner.nextLine();
                    System.out.print("Data do teste: ");
                    String dataTeste = scanner.nextLine();
                    System.out.print("Resultado (positivo/negativo): ");
                    String resultado = scanner.nextLine();
                    TesteEntity teste = new TesteEntity(dataTeste, cpf, resultado);
                    testeService.inserirTeste(teste);
                    System.out.println("Teste inserido.");
                }
                case 7 -> {
                    System.out.print("CPF do paciente: ");
                    String cpf = scanner.nextLine();
                    System.out.print("Data do óbito: ");
                    String dataObito = scanner.nextLine();
                    ObitoEntity obito = new ObitoEntity(dataObito, cpf);
                    obitoService.inserirObito(obito);
                    System.out.println("Óbito inserido.");
                }
                case 8 -> {
                    List<TesteEntity> testes = testeService.listarTestes();
                    System.out.println("Testes cadastrados:");
                    for (TesteEntity t : testes) {
                        System.out.printf("%s | %s | %s\n", t.getDataTeste(), t.getCpfPaciente(), t.getResultado());
                    }
                }
                case 9 -> {
                    List<ObitoEntity> obitos = obitoService.listarObitos();
                    System.out.println("Óbitos cadastrados:");
                    for (ObitoEntity o : obitos) {
                        System.out.printf("%s | %s\n", o.getDataObito(), o.getCpfPaciente());
                    }
                }
                case 0 -> {
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }
}