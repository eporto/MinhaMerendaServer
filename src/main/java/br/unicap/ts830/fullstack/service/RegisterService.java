/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicap.ts830.fullstack.service;

import br.unicap.ts830.fullstack.model.Register;
import br.unicap.ts830.fullstack.persistence.GenericDAO;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author shido
 */
@Stateless
public class RegisterService extends GenericDAO<Register>{
     public RegisterService() {
        super(Register.class);
    }
     
    public List<Register> getToken(String key) {
        HashMap params = new HashMap();
        params.put("appKey", key);
        return this.executeQuery(Register.FIND_BY_APPKEY, params);
    }
}
