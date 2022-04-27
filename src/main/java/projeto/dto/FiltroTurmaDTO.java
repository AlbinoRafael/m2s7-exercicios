package projeto.dto;

import java.io.Serializable;
import java.util.Date;

public class FiltroTurmaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idTurma;

    private String nome;

    private EstudanteDTO estudante;

    private EscolaDTO escola;

    private Date dataInicio;

    private Date dataTermino;

    public Long getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(Long idTurma) {
        this.idTurma = idTurma;
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

    public EstudanteDTO getEstudante() {
        return estudante;
    }

    public void setEstudante(EstudanteDTO estudante) {
        this.estudante = estudante;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }
}
