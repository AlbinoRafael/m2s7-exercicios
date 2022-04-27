package projeto.business;

import org.apache.commons.lang3.StringUtils;
import projeto.dto.EstudanteDTO;
import projeto.dto.FiltroEstudanteDTO;
import projeto.entity.Endereco;
import projeto.entity.Estudante;
import projeto.entity.Turma;
import projeto.exception.BusinessException;
import projeto.repository.EnderecoRepository;
import projeto.repository.EstudanteRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class EstudanteBusiness {

    @Inject
    private EstudanteRepository estudanteRepository;

    @Inject
    private EnderecoRepository enderecoRepository;

    private EnderecoBusiness enderecoBusiness = new EnderecoBusiness();

    public List<EstudanteDTO> buscar(FiltroEstudanteDTO filtro) throws BusinessException {
        validarCamposNulos(filtro);
        return estudanteRepository.buscar(filtro);
    }

    public void cadastrar(EstudanteDTO estudanteDTO) throws BusinessException {
        validarCadastrar(estudanteDTO);
        cadastrarEstudanteNoBanco(estudanteDTO);
    }

    private void cadastrarEstudanteNoBanco(EstudanteDTO estudanteDTO) throws BusinessException {
        Estudante estudante;
        if (estudanteDTO.getIdEstudante() != null) {
            estudante = estudanteRepository.find(Estudante.class, estudanteDTO.getIdEstudante());
            if (estudante == null) {
                throw new BusinessException("Estudante não encontrado.");
            }
        } else {
            estudante = new Estudante();
        }

        estudante.setNome(estudanteDTO.getNome());
        estudante.setEmail(estudanteDTO.getEmail());
        estudante.setDataNascimento(estudanteDTO.getDataNascimento());

        Turma turma = estudanteRepository.find(Turma.class, estudanteDTO.getIdEstudante());

        if (turma == null) {
            throw new BusinessException("Turma não encontrada");
        }

        estudante.setTurma(turma);

        enderecoBusiness.validarCadastrar(estudanteDTO.getEndereco());
        Endereco endereco = new Endereco();
        endereco.setRua(estudanteDTO.getEndereco().getRua());
        endereco.setNumero(estudanteDTO.getEndereco().getNumero());
        endereco.setBairro(estudanteDTO.getEndereco().getBairro());
        endereco.setCidade(estudanteDTO.getEndereco().getCidade());
        endereco.setEstado(estudanteDTO.getEndereco().getEstado());
        endereco.setPais(estudanteDTO.getEndereco().getPais());
        estudante.setEndereco(endereco);

        if (endereco.getIdEndereco() != null) {
            enderecoRepository.merge(endereco);
        } else {
            enderecoRepository.persist(endereco);
            estudanteDTO.getEndereco().setIdEndereco(endereco.getIdEndereco());
        }

        if (estudante.getIdEstudante() != null) {
            estudanteRepository.merge(estudante);
        } else {
            estudanteRepository.persist(estudante);
            estudanteDTO.setIdEstudante(estudante.getIdEstudante());
        }
    }

    private void validarCadastrar(EstudanteDTO estudanteDTO) throws BusinessException {
        List<String> erros = new ArrayList<>();

        if (StringUtils.isBlank(estudanteDTO.getNome())) {
            erros.add("O nome do estudante é inválido.");
        }

        if (estudanteDTO.getDataNascimento() == null) {
            erros.add("A data de nascimento é inválida.");
        }

        if (StringUtils.isBlank(estudanteDTO.getEmail())) {
            erros.add("O e-mail do estudante é inválido.");
        }

        if(estudanteDTO.getTurma() == null){
            erros.add("Turma inválida");
        }

        if (!erros.isEmpty()) {
            throw new BusinessException(erros);
        }
    }

    public EstudanteDTO consultarDadosEstudante(Long idEstudante) throws BusinessException {
        Estudante estudante = estudanteRepository.find(Estudante.class, idEstudante);
        if (estudante == null) {
            throw new BusinessException("Estudante não encontrado através do ID " + idEstudante + ".");
        }
        return new EstudanteDTO(estudante);
    }

    private void validarCamposNulos(FiltroEstudanteDTO filtro) throws BusinessException {
        if (filtro.getIdEstudante() == null
                && StringUtils.isBlank(filtro.getNome())
                && StringUtils.isBlank(filtro.getEmail())
                && filtro.getDataNascimento() == null
                && filtro.getTurma() == null
                && filtro.getEscola() == null) {
            throw new BusinessException("Insira ao menos um filtro para realizar a busca.");
        }
    }
}
