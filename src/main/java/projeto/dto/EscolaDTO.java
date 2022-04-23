package projeto.dto;

import projeto.entity.Endereco;
import projeto.entity.Escola;
import projeto.entity.Turma;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EscolaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idEscola;

    private String nome;

    private Endereco endereco;

    private Date dataCriacao;

    List<Turma> turmas = new ArrayList<>();

    public EscolaDTO(){}

    public EscolaDTO(Long idEscola, String nome, Endereco endereco, Date dataCriacao) {
        this.idEscola = idEscola;
        this.nome = nome;
        this.endereco = endereco;
        this.dataCriacao = dataCriacao;
    }

    public EscolaDTO(Escola escola){
        this.idEscola = escola.getIdEscola();
        this.nome = escola.getNome();
        this.endereco = escola.getEndereco();
        this.dataCriacao = escola.getDataCriacao();
    }

    public Long getIdEscola() {
        return idEscola;
    }

    public void setIdEscola(Long idEscola) {
        this.idEscola = idEscola;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }
}
