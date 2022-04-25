package projeto.business;

import org.apache.commons.lang3.StringUtils;
import projeto.dto.EscolaDTO;
import projeto.dto.EstudanteDTO;
import projeto.dto.TurmaDTO;
import projeto.entity.Endereco;
import projeto.entity.Estudante;
import projeto.entity.Turma;
import projeto.entity.Escola;
import projeto.exception.BusinessException;
import projeto.repository.EnderecoRepository;
import projeto.repository.EscolaRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class EscolaBusiness {

    @Inject
    private EscolaRepository escolaRepository;

    @Inject
    private EnderecoRepository enderecoRepository;
    @Inject
    private EnderecoBusiness enderecoBusiness;

    public void cadastrar(EscolaDTO escolaDTO) throws BusinessException {
        validarCadastrar(escolaDTO);
        cadastrarEscolaNoBanco(escolaDTO);
    }

    private void cadastrarEscolaNoBanco(EscolaDTO escolaDTO) throws BusinessException {
        Escola escola;
        if (escolaDTO.getIdEscola() != null) {
            escola = escolaRepository.find(Escola.class, escolaDTO.getIdEscola());
            if (escola == null) {
                throw new BusinessException("Escola não encontrada.");
            }
        } else {
            escola = new Escola();
        }
        escola.setNome(escolaDTO.getNome());
        escola.setDataCriacao(escolaDTO.getDataCriacao());

        enderecoBusiness.validarCadastrar(escolaDTO.getEnderecoDTO());
        Endereco endereco = new Endereco();
        endereco.setIdEndereco(escolaDTO.getEnderecoDTO().getIdEndereco());
        endereco.setRua(escolaDTO.getEnderecoDTO().getRua());
        endereco.setNumero(escolaDTO.getEnderecoDTO().getNumero());
        endereco.setBairro(escolaDTO.getEnderecoDTO().getBairro());
        endereco.setCidade(escolaDTO.getEnderecoDTO().getCidade());
        endereco.setEstado(escolaDTO.getEnderecoDTO().getEstado());
        endereco.setPais(escolaDTO.getEnderecoDTO().getPais());
        escola.setEndereco(endereco);

        for (Turma turma: escola.getTurmas()) {
            if (escolaDTO.getTurmas()
                    .stream()
                    .noneMatch(idTurma -> idTurma.equals(turma.getIdTurma()))) {
                turma.setEscola(null);
            }
        }

        for (Long idTurma : escolaDTO.getTurmas()) {
            if (escola.getTurmas()
                    .stream()
                    .noneMatch(turma -> turma.getIdTurma().equals(idTurma))) {
                Turma turma = escolaRepository.find(Turma.class, idTurma);
                if (turma == null) {
                    throw new BusinessException("Turma não encontrada.");
                }
                turma.setEscola(escola);
            }
        }
        if (endereco.getIdEndereco() != null) {
            enderecoRepository.merge(endereco);
        } else {
            enderecoRepository.persist(endereco);
            escolaDTO.getEnderecoDTO().setIdEndereco(endereco.getIdEndereco());
        }

        if (escola.getIdEscola() != null) {
            escolaRepository.merge(escola);
        } else {
            escolaRepository.persist(escola);
            escolaDTO.setIdEscola(escola.getIdEscola());
        }
    }

    public void validarCadastrar(EscolaDTO escolaDTO) throws BusinessException {
        List<String> erros = new ArrayList<>();

        if (StringUtils.isBlank(escolaDTO.getNome())) {
            erros.add("O nome da escola é inválido.");
        }

        if (escolaDTO.getDataCriacao()==null) {
            erros.add("A data é inválida.");
        }
        if(escolaDTO.getTurmas().isEmpty() && escolaDTO.getIdEscola() == null){
            erros.add("Selecione pelo menos 1 turma");
        }
        if (!erros.isEmpty()) {
            throw new BusinessException(erros);
        }
    }

    public EscolaDTO consultarDadosEscola(Long idEscola) throws BusinessException {
        Escola Escola = escolaRepository.find(projeto.entity.Escola.class, idEscola);
        if (Escola == null) {
            throw new BusinessException("Escola não encontrada através do ID " + idEscola + ".");
        }

        return new EscolaDTO(Escola);
    }
}
