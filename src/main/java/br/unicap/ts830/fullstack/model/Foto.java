package br.unicap.ts830.fullstack.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author shido
 */
@Entity
@NamedQuery(name = Foto.FIND_ALL, query = "select f from Foto f") //Test apenas (Provavelmente não precise de um Select all na Escola) -> Utilizar FindById
@Table(name="foto")
public class Foto implements Serializable {
    public static final String FIND_ALL = "Foto.FindAll";
    private static final long serialVersionUID = 1L;
    
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
     
    @Column(name = "imgpath")
    private String imgPath; 
    
    public Foto(){}
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return imgPath;
    }

  
    public void setPath(String path) {
        this.imgPath = path;
    }
    
}
