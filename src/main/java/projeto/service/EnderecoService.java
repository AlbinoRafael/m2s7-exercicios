package projeto.service;

import projeto.business.EnderecoBusiness;
import projeto.dto.EnderecoDTO;
import projeto.exception.BusinessException;
import projeto.repository.EnderecoRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class EnderecoService {

    @Inject
    private EnderecoBusiness enderecoBusiness;

    @Inject
    private EnderecoRepository enderecoRepository;

    public void cadastrar(EnderecoDTO enderecoDTO) throws BusinessException {
        enderecoBusiness.cadastrar(enderecoDTO);
    }

    public EnderecoDTO consultarDadosEndereco(Long idEndereco) throws BusinessException {
        return enderecoBusiness.consultarDadosEndereco(idEndereco);
    }

    public List<EnderecoDTO> consultarEnderecoPorCodigoOuRuaOuNumero(String query) {
        return enderecoRepository.consultarEnderecoPorCodigoOuRuaOuNumero(query);
    }
}
