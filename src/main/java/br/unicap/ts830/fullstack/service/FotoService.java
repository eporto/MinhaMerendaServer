/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicap.ts830.fullstack.service;

import br.unicap.ts830.fullstack.model.Foto;
import br.unicap.ts830.fullstack.persistence.GenericDAO;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author shido
 */
@Stateless
public class FotoService extends GenericDAO<Foto>{
     public FotoService() {
        super(Foto.class);
    }
     
    public List<Foto> getUsuario() {
        return this.executeQuery(Foto.FIND_ALL);
    }
}
