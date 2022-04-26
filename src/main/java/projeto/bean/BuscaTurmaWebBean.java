package projeto.bean;

import org.omnifaces.cdi.ViewScoped;
import projeto.dto.EscolaDTO;
import projeto.dto.EstudanteDTO;
import projeto.dto.FiltroTurmaDTO;
import projeto.dto.TurmaDTO;
import projeto.exception.BusinessException;
import projeto.service.EscolaService;
import projeto.service.EstudanteService;
import projeto.service.TurmaService;
import projeto.utils.MessageUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named("buscaTurmaWebBean")
public class BuscaTurmaWebBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private TurmaService turmaService;

    @Inject
    private EstudanteService estudanteService;

    @Inject
    private EscolaService escolaService;

    private FiltroTurmaDTO filtro = new FiltroTurmaDTO();

    private List<TurmaDTO> turmasEncontradas = new ArrayList<>();

    private List<EscolaDTO> escolasEncontradas = new ArrayList<>();

    private List<EstudanteDTO> estudantesEncontrados = new ArrayList<>();
    private boolean buscaFeita;

    public List<EstudanteDTO> consultarEstudantePorNomeOuMatricula(Object query) {
        estudantesEncontrados = estudanteService.consultarEstudantePorNomeOuMatricula(query.toString());
        return estudantesEncontrados;
    }

    public List<EscolaDTO> consultarEscolasPorNome(Object query){
        escolasEncontradas = escolaService.consultarEscolasPorNome(query.toString());
        return escolasEncontradas;
    }

    public void buscar() {
        try {
            turmasEncontradas = turmaService.buscar(filtro);
            buscaFeita = true;
        } catch (BusinessException e) {
            MessageUtils.returnMessageOnFail(e.getErros());
        }
    }

    public FiltroTurmaDTO getFiltro() {
        return filtro;
    }

    public void setFiltro(FiltroTurmaDTO filtro) {
        this.filtro = filtro;
    }

    public List<TurmaDTO> getTurmasEncontradas() {
        return turmasEncontradas;
    }

    public void setTurmasEncontradas(List<TurmaDTO> turmasEncontradas) {

        this.turmasEncontradas = turmasEncontradas;
    }

    public boolean isBuscaFeita() {
        return buscaFeita;
    }

    public void setBuscaFeita(boolean buscaFeita) {
        this.buscaFeita = buscaFeita;
    }

    public List<EscolaDTO> getEscolasEncontradas() {
        return escolasEncontradas;
    }

    public void setEscolasEncontradas(List<EscolaDTO> escolasEncontradas) {
        this.escolasEncontradas = escolasEncontradas;
    }

    public List<EstudanteDTO> getEstudantesEncontrados() {
        return estudantesEncontrados;
    }

    public void setEstudantesEncontrados(List<EstudanteDTO> estudantesEncontrados) {
        this.estudantesEncontrados = estudantesEncontrados;
    }
}
