package entities;

public class ObitoEntity {
    private String dataObito;
    private String cpfPaciente;

    public ObitoEntity() {}

    public ObitoEntity(String dataObito, String cpfPaciente) {
        this.dataObito = dataObito;
        this.cpfPaciente = cpfPaciente;
    }

    public String getDataObito() {
        return dataObito;
    }

    public void setDataObito(String dataObito) {
        this.dataObito = dataObito;
    }

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public void setCpfPaciente(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }
}
