package br.unicap.ts830.fullstack.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author shido
 */
@Entity
@NamedQueries({
     @NamedQuery(name = Geometry.FIND_ALL, query = "select g from Geometry g"),
     @NamedQuery(name = Geometry.FIND_BY_ID, query = "select g from Geometry g where g.escolaId = :id")
})
@Table(name="escolasgeometry")
public class Geometry implements Serializable {
    public static final String FIND_ALL = "Geometry.FindAll";
    public static final String FIND_BY_ID = "Geometry.FindById";
    private static final long serialVersionUID = 1L;
    
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
     
    @Column(name = "id_escola")
    private int escolaId;
    
    private String x;
    
    private String y;
    
    public Geometry(){}
    
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the x
     */
    public String getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(String x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public String getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(String y) {
        this.y = y;
    }

    /**
     * @return the escolaId
     */
    public int getEscolaId() {
        return escolaId;
    }

    /**
     * @param escolaId the escolaId to set
     */
    public void setEscolaId(int escolaId) {
        this.escolaId = escolaId;
    }

    
   
    
}
