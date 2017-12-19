/*
 * Endpoit -> Service(DAO) -> DB
 */
package br.unicap.ts830.fullstack.service;

import java.util.List;
import javax.ejb.Stateless;
import br.unicap.ts830.fullstack.model.Avaliacao;
import br.unicap.ts830.fullstack.persistence.GenericDAO;
import java.util.HashMap;

/**
 *
 * @author shido
 */
@Stateless
public class AvaliacaoService extends GenericDAO<Avaliacao>{
    
    public AvaliacaoService() {
        super(Avaliacao.class);
    }
    
    public List<Avaliacao> getAvaliacao() {
        return this.executeQuery(Avaliacao.FIND_ALL);
    }
    
    public List<Avaliacao> findAvaliacao(Long escolaCodigo) {
        HashMap params = new HashMap();
        params.put("codigo", escolaCodigo);
        return this.executeQuery(Avaliacao.FIND_BY_ESCOLA, params);
    }
}
