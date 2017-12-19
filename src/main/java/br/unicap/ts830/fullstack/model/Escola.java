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

/*
 * @author shido
 */
@Entity
@NamedQuery(name = Escola.FIND_ALL, query = "select e from Escola e") //Test apenas (Provavelmente não precise de um Select all na Escola) -> Utilizar FindById
@Table(name="escolas")  
public class Escola implements Serializable {
    public static final String FIND_ALL = "Escola.FindAll";
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "escola_codigo")
    private int escolaCodigo;
    
    @Column(name = "escola_nome")
    private String escolaNome;
             
    private String endereco;
    
    @Column(name = "mec_codigo")
    private String mecCodigo;
    
    public Escola() {}
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getEscolaNome() {
        return escolaNome;
    }

    public void setEscolaNome(String escolaNome) {
        this.escolaNome = escolaNome;
    }
    
    public int getEscolaCodigo() {
        return escolaCodigo;
    }

    public void setEscolaCodigo(int escolaCodigo) {
        this.escolaCodigo = escolaCodigo;
    }
   
     public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
       public String getMecCodigo() {
        return mecCodigo;
    }

    public void setMecCodigo(String mecCodigo) {
        this.mecCodigo = mecCodigo;
    }
   
}