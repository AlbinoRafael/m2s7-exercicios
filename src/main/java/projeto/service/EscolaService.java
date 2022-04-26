package projeto.service;

import projeto.business.EscolaBusiness;
import projeto.dto.EscolaDTO;
import projeto.exception.BusinessException;
import projeto.repository.EscolaRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class EscolaService {

    @Inject
    private EscolaBusiness escolaBusiness;

    @Inject
    private EscolaRepository escolaRepository;

    public void cadastrar(EscolaDTO escolaDTO) throws BusinessException {
        escolaBusiness.cadastrar(escolaDTO);
    }

    public List<EscolaDTO> consultarEscolas(){
        return escolaRepository.consultarEscolas();
    }

    public EscolaDTO consultarDadosEscola(Long idEscola) throws BusinessException {
        return escolaBusiness.consultarDadosEscola(idEscola);
    }

    public List<EscolaDTO> consultarEscolaPorCodigoOuNome(String query) {
        return escolaRepository.consultarEscolaPorCodigoOuNome(query);
    }

    public List<EscolaDTO> consultarEscolasPorNome(String query) {
        return escolaRepository.consultarEscolasPorNome(query);
    }
}
