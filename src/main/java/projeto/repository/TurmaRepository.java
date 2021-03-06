package projeto.repository;

import org.apache.commons.lang3.StringUtils;
import projeto.dto.EstudanteDTO;
import projeto.dto.FiltroTurmaDTO;
import projeto.dto.TurmaDTO;
import projeto.entity.Turma;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class TurmaRepository extends GenericRepository {

    public List<TurmaDTO> consultarTurmas() {
        return entityManager.createNamedQuery(Turma.GET_TURMASDTO, TurmaDTO.class)
                .getResultList();
    }

    public List<TurmaDTO> consultarTurmasPorNome(String query) {
        query = "%"+query+"%";
        query = query.toLowerCase();
        return entityManager.createQuery("SELECT new projeto.dto.TurmaDTO(t.idTurma, t.nome) "
                                +"FROM Turma t "
                                +"WHERE LOWER(t.nome) LIKE :query", TurmaDTO.class)
                                .setParameter("query", query)
                                .getResultList();
    }

    public List<TurmaDTO> consultarTurmasSemEscola() {
        return entityManager.createQuery("SELECT new projeto.dto.TurmaDTO(t, false) "
                        + "FROM Turma t "
                        + "WHERE t.escola is null ", TurmaDTO.class)
                .getResultList();
    }

    public List<EstudanteDTO> consultarEstudantesSemTurmas() {
        return entityManager.createQuery("SELECT new projeto.dto.EstudanteDTO(e) " +
                        "FROM Estudante e " +
                        "WHERE e.turma is null", EstudanteDTO.class)
                .getResultList();
    }

    public List<TurmaDTO> buscar(FiltroTurmaDTO filtro) {
        String hql = montarSqlBuscaTurma(filtro);
        Query query = entityManager.createQuery(hql, TurmaDTO.class);
        popularParametrosBuscaTurma(filtro, query);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    private void popularParametrosBuscaTurma(FiltroTurmaDTO filtro, Query query) {
        if (filtro.getIdTurma() != null) {
            query.setParameter("idTurma", filtro.getIdTurma());
        }
        if (!StringUtils.isBlank(filtro.getNome())) {
            String nome = "%" + filtro.getNome() + "%";
            nome = nome.toLowerCase();
            query.setParameter("nome", nome);
        }

        if (filtro.getDataInicio() != null) {
            query.setParameter("dataInicio", filtro.getDataInicio());
        }

        if (filtro.getDataTermino() != null) {
            query.setParameter("dataTermino", filtro.getDataTermino());
        }
        if (filtro.getEscola() != null) {
            query.setParameter("idEscola", filtro.getEscola().getIdEscola());
        }
        if(filtro.getEstudante() != null){
            query.setParameter("idEstudante", filtro.getEstudante().getIdEstudante());
        }
    }

    private String montarSqlBuscaTurma(FiltroTurmaDTO filtro) {
        String hql = "SELECT new projeto.dto.TurmaDTO(t, false) " +
                "FROM Turma t ";
        String andOrWhere = "WHERE ";

        if (filtro.getEstudante() != null) {
            hql = hql.concat("JOIN t.estudantes e ");

            hql = hql.concat(andOrWhere).concat("e.idEstudante = :idEstudante ");
            andOrWhere = "AND ";
        }

        if (filtro.getIdTurma() != null) {
            hql = hql.concat(andOrWhere).concat("t.idTurma = :idTurma ");
            andOrWhere = "AND ";
        }

        if (!StringUtils.isBlank(filtro.getNome())) {
            hql = hql.concat(andOrWhere).concat("LOWER(t.nome) LIKE :nome ");
            andOrWhere = "AND ";
        }

        if (filtro.getDataInicio() != null) {
            hql = hql.concat(andOrWhere).concat("t.dataInicio >= :dataInicio ");
            andOrWhere = "AND ";
        }

        if (filtro.getDataTermino() != null) {
            hql = hql.concat(andOrWhere).concat("t.dataTermino <= :dataTermino ");
            andOrWhere = "AND ";
        }

        if (filtro.getEscola() != null) {
            hql = hql.concat(andOrWhere).concat("t.escola.idEscola = :idEscola ");
        }

        return hql.concat("ORDER BY t.idTurma");
    }


}
