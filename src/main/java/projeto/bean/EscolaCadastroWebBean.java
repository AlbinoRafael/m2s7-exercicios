package projeto.bean;

import org.omnifaces.cdi.Param;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import projeto.dto.EscolaDTO;
import projeto.dto.EscolaDTO;
import projeto.dto.EstudanteDTO;
import projeto.dto.TurmaDTO;
import projeto.exception.BusinessException;
import projeto.service.EscolaService;
import projeto.service.TurmaService;
import projeto.utils.MessageUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named("escolaCadastroWebBean")
public class EscolaCadastroWebBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private EscolaDTO escolaDTO = new EscolaDTO();

    @Param(name = "idEscola")
    private Long idEscola;

    @Inject
    private EscolaService escolaService;

    @Inject
    private TurmaService turmaService;

    private List<TurmaDTO> turmas = new ArrayList<>();

    private List<Long> turmasSelecionadas = new ArrayList<>();

    public void inicializar() {
        if (idEscola != null) {
            try {
                escolaDTO = escolaService.consultarDadosEscola(idEscola);
                MessageUtils.limparMensagens();
            } catch (BusinessException e) {
                MessageUtils.returnGlobalMessageOnFail(e.getErros());
                Faces.redirect("/escola.xhtml");
            }
        }
        turmas = turmaService.consultarTurmasSemEscola();
        System.out.println("caiu aqui");
    }

    public void cadastrar() {
        try {
            escolaDTO.setTurmas(turmasSelecionadas);
            escolaService.cadastrar(escolaDTO);
            if (idEscola == null) {
                MessageUtils.returnGlobalMessageOnSuccess("Salvo com sucesso!");
                Faces.redirect("/escola.xhtml?idEscola=" + escolaDTO.getIdEscola());
            } else {
                MessageUtils.returnMessageOnSuccess("Salvo com sucesso!");
            }
        } catch (BusinessException e) {
            MessageUtils.returnMessageOnFail(e.getErros());
        } catch (Exception e) {
            MessageUtils.returnMessageOnFail("Ocorreu um erro ao salvar a escola. Por favor, entre em contato com o suporte.");
        }
    }

    public EscolaDTO getEscolaDTO() {
        return escolaDTO;
    }

    public void setEscolaDTO(EscolaDTO escolaDTO) {
        this.escolaDTO = escolaDTO;
    }

    public Long getIdEscola() {
        return idEscola;
    }

    public void setIdEscola(Long idEscola) {
        this.idEscola = idEscola;
    }

    public List<TurmaDTO> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<TurmaDTO> turmas) {
        this.turmas = turmas;
    }

    public List<Long> getTurmasSelecionadas() {
        return turmasSelecionadas;
    }

    public void setTurmasSelecionadas(List<Long> turmasSelecionadas) {
        this.turmasSelecionadas = turmasSelecionadas;
    }
}
