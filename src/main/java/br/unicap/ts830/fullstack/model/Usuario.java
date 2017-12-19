package br.unicap.ts830.fullstack.model;

import java.io.Serializable;
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
@NamedQuery(name = Usuario.FIND_ALL, query = "select u from Usuario u") //Test apenas (Provavelmente não precise de um Select all na Escola) -> Utilizar FindById
@Table(name="usuario")
public class Usuario implements Serializable {
    public static final String FIND_ALL = "Usuario.FindAll";
    private static final long serialVersionUID = 1L;
    
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
     
    private String tipo; //Aluno, Escola, Fabricante, Transportadora
    
    public Usuario(){}
    
    public Usuario(long id){
        this.id = id;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}
