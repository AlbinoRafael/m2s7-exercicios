package projeto.bean;

import org.omnifaces.cdi.ViewScoped;
import projeto.dto.*;
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
@Named("buscaEstudanteWebBean")
public class BuscaEstudanteWebBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private TurmaService turmaService;

    @Inject
    private EstudanteService estudanteService;

    @Inject
    private EscolaService escolaService;

    private FiltroEstudanteDTO filtro = new FiltroEstudanteDTO();

    private List<TurmaDTO> turmasEncontradas = new ArrayList<>();

    private List<EscolaDTO> escolasEncontradas = new ArrayList<>();

    private List<EstudanteDTO> estudantesEncontrados = new ArrayList<>();
    private boolean buscaFeita;

    public List<EscolaDTO> consultarEscolasPorNome(Object query){
        escolasEncontradas = escolaService.consultarEscolasPorNome(query.toString());
        return escolasEncontradas;
    }

    public List<TurmaDTO> consultarTurmasPorNome(Object query){
        turmasEncontradas = turmaService.consultarTurmasPorNome(query.toString());
        return turmasEncontradas;
    }
    public void buscar() {
        try {
            estudantesEncontrados = estudanteService.buscar(filtro);
            buscaFeita = true;
        } catch (BusinessException e) {
            MessageUtils.returnMessageOnFail(e.getErros());
        }
    }

    public FiltroEstudanteDTO getFiltro() {
        return filtro;
    }

    public void setFiltro(FiltroEstudanteDTO filtro) {
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
