package projeto.repository;

import org.apache.commons.lang3.StringUtils;
import projeto.dto.EnderecoDTO;
import projeto.dto.EnderecoFiltroDTO;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class EnderecoRepository extends GenericRepository {

    public List<EnderecoDTO> consultarEnderecoPorCodigoOuRuaOuNumero(String query) {
        query = "%" + query + "%";
        query = query.toLowerCase();
        try {
            return entityManager.createQuery("SELECT new projeto.dto.EnderecoDTO(e.idEndereco, e.rua, e.numero) " +
                            "FROM Endereco e " +
                            "WHERE CAST(e.idEndereco AS string) = :query " +
                            "OR LOWER(e.nome) LIKE :query " +
                            "OR LOWER(e.numero) LIKE :query", EnderecoDTO.class)
                    .setParameter("query", query)
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }
    public List<EnderecoDTO> consultarEnderecosPorFiltro(EnderecoFiltroDTO filtro) {
        try {
            String hql = obterSqlConsultarEnderecoPorFiltro(filtro);
            Query query = entityManager.createQuery(hql,EnderecoDTO.class);
            popularParametrosConsultarEnderecosPorFiltro(filtro, query);

            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }
    private void popularParametrosConsultarEnderecosPorFiltro(EnderecoFiltroDTO filtro, Query query) {
        if (!StringUtils.isBlank(filtro.getRua())) {
            String rua = "%" + filtro.getRua() + "%";
            rua = rua.toLowerCase();
            query.setParameter("rua", rua);
        }
        if (!StringUtils.isBlank(filtro.getNumero())) {
            String numero = "%" + filtro.getNumero() + "%";
            numero = numero.toLowerCase();
            query.setParameter("numero", numero);
        }
        if (!StringUtils.isBlank(filtro.getBairro())) {
            String bairro = "%" + filtro.getBairro() + "%";
            bairro = bairro.toLowerCase();
            query.setParameter("bairro", bairro);
        }
        if (!StringUtils.isBlank(filtro.getCidade())) {
            String cidade = "%" + filtro.getCidade() + "%";
            cidade = cidade.toLowerCase();
            query.setParameter("cidade", cidade);
        }
        if (!StringUtils.isBlank(filtro.getEstado())) {
            String estado = "%" + filtro.getEstado() + "%";
            estado = estado.toLowerCase();
            query.setParameter("estado", estado);
        }

        if (!StringUtils.isBlank(filtro.getPais())) {
            String pais = "%" + filtro.getPais() + "%";
            pais = pais.toLowerCase();
            query.setParameter("pais", pais);
        }
    }

    private String obterSqlConsultarEnderecoPorFiltro(EnderecoFiltroDTO filtro) {
        String hql = "SELECT new projeto.dto.EnderecoDTO(e) "
                + "FROM Endereco e ";
        String andOrWhere = "WHERE ";

        if (!StringUtils.isBlank(filtro.getRua())) {
            hql = hql.concat(andOrWhere).concat("LOWER(e.rua) = :rua ");
            andOrWhere = "AND ";
        }
        if (!StringUtils.isBlank(filtro.getNumero())) {
            hql = hql.concat(andOrWhere).concat("LOWER(e.numero) = :numero ");
            andOrWhere = "AND ";
        }
        if (!StringUtils.isBlank(filtro.getBairro())) {
            hql = hql.concat(andOrWhere).concat("LOWER(e.bairro) LIKE :bairro ");
            andOrWhere = "AND ";
        }
        if (!StringUtils.isBlank(filtro.getCidade())) {
            hql = hql.concat(andOrWhere).concat("e.cidade = :cidade ");
            andOrWhere = "AND ";
        }
        if (!StringUtils.isBlank(filtro.getEstado())) {
            hql = hql.concat(andOrWhere).concat("e.estado = :estado ");
            andOrWhere = "AND ";
        }
        if (!StringUtils.isBlank(filtro.getPais())) {
            hql = hql.concat(andOrWhere).concat("e.pais = :pais ");
        }
        return hql.concat("ORDER BY e.idEndereco");
    }
}
