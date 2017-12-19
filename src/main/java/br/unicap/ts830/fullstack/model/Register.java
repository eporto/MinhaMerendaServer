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
@NamedQuery(name = Register.FIND_BY_APPKEY, query = "select r from Register r where r.appkey = :appKey") //Test apenas (Provavelmente não precise de um Select all na Escola) -> Utilizar FindById
@Table(name="appkey")
public class Register implements Serializable {
    public static final String FIND_BY_APPKEY = "Register.FindAll";
    private static final long serialVersionUID = 1L;
    
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
     
    private String appkey; 
    private String token;
    
    public Register(){}
    
    public Register(String appKey) {
        this.appkey = appKey;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public String getAppKey() {
        return appkey;
    }

    public void setAppKey(String appKey) {
        this.appkey = appKey;
    }

 
    public String getAppToken() {
        return token;
    }

  
    public void setAppToken(String appToken) {
        this.token = appToken;
    }
    
}
