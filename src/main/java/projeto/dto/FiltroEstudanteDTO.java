package projeto.dto;

import java.io.Serializable;
import java.util.Date;

public class FiltroEstudanteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idEstudante;

    private String nome;

    private String email;

    private Date dataNascimento;

    private TurmaDTO turma;

    private EscolaDTO escola;

    public Long getIdEstudante() {
        return idEstudante;
    }

    public void setIdEstudante(Long idEstudante) {
        this.idEstudante = idEstudante;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public TurmaDTO getTurma() {
        return turma;
    }

    public void setTurma(TurmaDTO turma) {
        this.turma = turma;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EscolaDTO getEscola() {
        return escola;
    }

    public void setEscola(EscolaDTO escola) {
        this.escola = escola;
    }

}
