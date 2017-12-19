/*
 * Endpoit -> Service(DAO) -> DB
 */
package br.unicap.ts830.fullstack.service;

import java.util.List;

import javax.ejb.Stateless;
import br.unicap.ts830.fullstack.model.Escola;
import br.unicap.ts830.fullstack.persistence.GenericDAO;

/**
 *
 * @author shido
 */
@Stateless
public class EscolaService extends GenericDAO<Escola>{
    
    public EscolaService() {
        super(Escola.class);
    }
    
    public List<Escola> getEscola() {
        return this.executeQuery(Escola.FIND_ALL);
    }
}
