/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicap.ts830.fullstack.service;

import br.unicap.ts830.fullstack.model.Usuario;
import br.unicap.ts830.fullstack.persistence.GenericDAO;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author shido
 */
@Stateless
public class UsuarioService extends GenericDAO<Usuario>{
     public UsuarioService() {
        super(Usuario.class);
    }
     
    public List<Usuario> getUsuario() {
        return this.executeQuery(Usuario.FIND_ALL);
    }
}
