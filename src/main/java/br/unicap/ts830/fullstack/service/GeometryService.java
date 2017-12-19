/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicap.ts830.fullstack.service;

import br.unicap.ts830.fullstack.model.Geometry;
import br.unicap.ts830.fullstack.persistence.GenericDAO;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author shido
 */
@Stateless
public class GeometryService extends GenericDAO<Geometry>{
     public GeometryService() {
        super(Geometry.class);
    }
     
    public List<Geometry> getGeometries() {
        return this.executeQuery(Geometry.FIND_ALL);
    }
    
    public List<Geometry> findGeometry(int id) {
        HashMap params = new HashMap();
        params.put("id", id);
        return this.executeQuery(Geometry.FIND_BY_ID, params);
    }
}
