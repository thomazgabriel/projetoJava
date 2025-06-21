package entities;

public class TesteEntity {
    private String dataTeste;
    private String cpfPaciente;
    private String resultado; // "positivo" ou "negativo"

    public TesteEntity() {}

    public TesteEntity(String dataTeste, String cpfPaciente, String resultado) {
        this.dataTeste = dataTeste;
        this.cpfPaciente = cpfPaciente;
        this.resultado = resultado;
    }

    public String getDataTeste() { return dataTeste; }
    public void setDataTeste(String dataTeste) { this.dataTeste = dataTeste; }

    public String getCpfPaciente() { return cpfPaciente; }
    public void setCpfPaciente(String cpfPaciente) { this.cpfPaciente = cpfPaciente; }

    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }
}
