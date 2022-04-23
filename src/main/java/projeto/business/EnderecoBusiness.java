package projeto.business;

import org.apache.commons.lang3.StringUtils;
import projeto.dto.EnderecoDTO;
import projeto.entity.Endereco;
import projeto.exception.BusinessException;
import projeto.repository.EnderecoRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class EnderecoBusiness {

    @Inject
    private EnderecoRepository enderecoRepository;

    public void cadastrar(EnderecoDTO enderecoDTO) throws BusinessException {
        validarCadastrar(enderecoDTO);
        cadastrarEnderecoNoBanco(enderecoDTO);
    }

    private void cadastrarEnderecoNoBanco(EnderecoDTO enderecoDTO) throws BusinessException {
        Endereco endereco;
        if (enderecoDTO.getIdEndereco() != null) {
            endereco = enderecoRepository.find(Endereco.class, enderecoDTO.getIdEndereco());
            if (endereco == null) {
                throw new BusinessException("Endereço não encontrado.");
            }
        } else {
            endereco = new Endereco();
        }
        endereco.setRua(enderecoDTO.getRua());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setEstado(enderecoDTO.getEstado());
        endereco.setPais(enderecoDTO.getPais());

        if (endereco.getIdEndereco() != null) {
            enderecoRepository.merge(endereco);
        } else {
            enderecoRepository.persist(endereco);
            enderecoDTO.setIdEndereco(endereco.getIdEndereco());
        }
    }

    public void validarCadastrar(EnderecoDTO enderecoDTO) throws BusinessException {
        List<String> erros = new ArrayList<>();

        if (StringUtils.isBlank(enderecoDTO.getRua())) {
            erros.add("O nome da rua é inválido.");
        }

        if (StringUtils.isBlank(enderecoDTO.getNumero())) {
            erros.add("O número é inválido.");
        }

        if (StringUtils.isBlank(enderecoDTO.getBairro())) {
            erros.add("O bairro é inválido.");
        }

        if (StringUtils.isBlank(enderecoDTO.getCidade())) {
            erros.add("A cidade é inválida.");
        }
        if(StringUtils.isBlank(enderecoDTO.getEstado())){
            erros.add("O estado é inválido.");
        }
        if(StringUtils.isBlank(enderecoDTO.getPais())){
            erros.add("O país é inválido.");
        }
        if (!erros.isEmpty()) {
            throw new BusinessException(erros);
        }
    }

    public EnderecoDTO consultarDadosEndereco(Long idEndereco) throws BusinessException {
        Endereco endereco = enderecoRepository.find(Endereco.class, idEndereco);
        if (endereco == null) {
            throw new BusinessException("Endereco não encontrado através do ID " + idEndereco + ".");
        }

        return new EnderecoDTO(endereco);
    }
}
