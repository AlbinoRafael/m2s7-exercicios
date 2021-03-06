package projeto.repository;

import projeto.dto.EscolaDTO;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class EscolaRepository extends GenericRepository {

    public List<EscolaDTO> consultarEscolas() {
        return entityManager.createQuery("SELECT new projeto.dto.EscolaDTO(e) "+
                        "FROM Escola e ", EscolaDTO.class).getResultList();
    }
    public List<EscolaDTO> consultarEscolaPorCodigoOuNome(String query) {
        query = "%" + query + "%";
        query = query.toLowerCase();
        try {
            return entityManager.createQuery("SELECT new projeto.dto.EscolaDTO(e.idEscola, e.nome) " +
                            "FROM Escola e " +
                            "WHERE CAST(e.idEscola AS string) = :query " +
                            "OR LOWER(e.nome) LIKE :query", EscolaDTO.class)
                    .setParameter("query", query)
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public List<EscolaDTO> consultarEscolasPorNome(String query) {
        query = "%" + query + "%";
        query = query.toLowerCase();
        try {
            return entityManager.createQuery("SELECT new projeto.dto.EscolaDTO(e.idEscola, e.nome) " +
                            "FROM Escola e " +
                            "WHERE LOWER(e.nome) LIKE :query", EscolaDTO.class)
                    .setParameter("query", query)
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }
}
