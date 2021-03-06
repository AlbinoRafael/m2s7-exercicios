package projeto.service;

import projeto.business.TurmaBusiness;
import projeto.dto.EscolaDTO;
import projeto.dto.EstudanteDTO;
import projeto.dto.FiltroTurmaDTO;
import projeto.dto.TurmaDTO;
import projeto.exception.BusinessException;
import projeto.repository.EscolaRepository;
import projeto.repository.TurmaRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TurmaService {

    @Inject
    private TurmaBusiness turmaBusiness;

    @Inject
    private TurmaRepository turmaRepository;

    @Inject
    private EscolaRepository escolaRepository;

    public void cadastrar(TurmaDTO turmaDTO) throws BusinessException {
        turmaBusiness.cadastrar(turmaDTO);
    }

    public TurmaDTO consultarDadosTurma(Long idTurma) throws BusinessException {
        return turmaBusiness.consultarDadosTurma(idTurma);
    }

    public List<TurmaDTO> consultarTurmas() {
        return turmaRepository.consultarTurmas();
    }

    public List<TurmaDTO> consultarTurmasSemEscola(){
        return turmaRepository.consultarTurmasSemEscola();
    }

    public List<EstudanteDTO> consultarEstudantesSemTurmas() {
        return turmaRepository.consultarEstudantesSemTurmas();
    }

    public List<TurmaDTO> buscar(FiltroTurmaDTO filtro) throws BusinessException {
        return turmaBusiness.buscar(filtro);
    }

    public List<EscolaDTO> consultarEscolasPorNome(String query) {
        return escolaRepository.consultarEscolasPorNome(query);
    }

    public List<TurmaDTO> consultarTurmasPorNome(String query) {
        return turmaRepository.consultarTurmasPorNome(query);
    }
}
