package projeto.dto;

import projeto.entity.Turma;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TurmaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idTurma;

    private Long idEscola;

    private String nome;

    private Date dataInicio;

    private Date dataTermino;

    private List<EstudanteDTO> estudantes = new ArrayList<>();

    private int quantidadeEstudantes;

    private String status;

    private EscolaDTO escolaDTO = new EscolaDTO();

    public TurmaDTO() {
    }

    public TurmaDTO(Long idTurma, String nome) {
        this.idTurma = idTurma;
        this.nome = nome;
    }

    public TurmaDTO(Turma turma, boolean comEstudantes) {
        this.idTurma = turma.getIdTurma();
        this.idEscola = turma.getEscola() != null ? turma.getEscola().getIdEscola() : null;
        this.nome = turma.getNome();
        this.dataInicio = turma.getDataInicio();
        this.dataTermino = turma.getDataTermino();
        if(comEstudantes) {
            this.estudantes = turma.getEstudantes()
                    .stream()
                    .map(EstudanteDTO::new)
                    .sorted(Comparator.comparing(EstudanteDTO::getNome))
                    .collect(Collectors.toList());
        }
        this.quantidadeEstudantes = turma.getEstudantes().size();
        this.escolaDTO = turma.getEscola() != null ? new EscolaDTO(turma.getEscola()) : null;
        Date dataAtual = new Date();
        if (dataAtual.after(dataInicio) && dataAtual.before(dataTermino)) {
            this.status = "Em andamento";
        } else if (dataAtual.before(dataInicio)) {
            this.status = "Aguardando início";
        } else if (dataAtual.after(dataTermino)) {
            this.status = "Encerrada";
        }
    }

    public Long getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(Long idTurma) {
        this.idTurma = idTurma;
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

    public List<EstudanteDTO> getEstudantes() {
        return estudantes;
    }

    public void setEstudantes(List<EstudanteDTO> estudantes) {
        this.estudantes = estudantes;
    }

    public int getQuantidadeEstudantes() {
        return quantidadeEstudantes;
    }

    public void setQuantidadeEstudantes(int quantidadeEstudantes) {
        this.quantidadeEstudantes = quantidadeEstudantes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public EscolaDTO getEscolaDTO() {
        return escolaDTO;
    }

    public void setEscolaDTO(EscolaDTO escolaDTO) {
        this.escolaDTO = escolaDTO;
    }
}
