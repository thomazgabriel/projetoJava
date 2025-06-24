package models;

public class PacienteModel {
    private String cpf;
    private String nome;
    private String dataNascimento;
    private int idade;
    private String estado;
    private String cidade;
    private int posicao; // Caso queira usar em listagens ordenadas

    public PacienteModel() {}

    public PacienteModel(String cpf, String nome, String dataNascimento, int idade, String estado, String cidade, int posicao) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.idade = idade;
        this.estado = estado;
        this.cidade = cidade;
        this.posicao = posicao;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }
}