package br.unicap.ts830.fullstack.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
//import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 *
 * @author shido
 */
@Entity
@NamedQueries({
     @NamedQuery(name = Avaliacao.FIND_ALL, query = "select a from Avaliacao a"),
     @NamedQuery(name = Avaliacao.FIND_BY_ESCOLA, query = "select a from Avaliacao a where a.escola.id = :codigo") //Id Tabela ou id Codigo_Escola ?
})
@Table(name="avaliacao") 
public class Avaliacao implements Serializable {
    public static final String FIND_ALL = "Avaliacao.FindAll";
    public static final String FIND_BY_ESCOLA = "Avaliacao.FindByEscola";
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id; 

    /* Removido
    @Column(name = "nome_escola")
    private String escolaNome; 
    */

    @Column(length = 1)
    private int pontuacao;

    @OneToOne
    @JoinColumn(name = "id_escola")
    private Escola escola;

    @OneToOne
    @JoinColumn(name = "id_foto")
    private Foto foto;
    
    /* Precisa armazenar a imagem no próprio DB (Em contraste a armazenar no HD), já que estamos com um DB na memória
    @Lob
    @Column(name = "url_photo")
    private byte[] foto;
    */
  
    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
    //Empty Constructor
    public Avaliacao() {}
    
    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public Escola getEscola() {
        return escola;
    }

    public void setEscola(Escola escola) {
        this.escola = escola;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }
    
    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }
    
    /*
    @Override
    public int hashCode(){
        return this.id;
    }
    
    @Override
    public boolean equals(Object d){
        return d.hashCode() == this.hashCode();
    }
    
    
    @Id
   //@GeneratedValue(strategy = GenerationType.IDENTITY)
   //@GeneratedValue
   private int id; //private Long id;

   //@NotNull
   //@Size(min = 1, max = 25)
   //@Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
   //private String nome;

   //private int valor;
   
   /*
   @NotNull
   //@NotEmpty = @NotNull + @Size(min=1)
   private String email;

   @NotNull
   @Size(min = 10, max = 12)
   @Digits(fraction = 0, integer = 12)
   @Column(name = "phone_number")
   private String phoneNumber;
   */
    
  
}
