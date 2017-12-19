package br.unicap.ts830.fullstack.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

/**
 *
 * @author shido
 * @param <T>
 */
public class GenericDAO<T> {
    @PersistenceContext
    EntityManager manager;
    
    private final Class<T> persistClass;
    
    public GenericDAO(Class<T> persistClass) {
        this.persistClass = persistClass;
    }
    
    @Transactional
    public T insert(T t) {
        manager.persist(t);
        return t;
    }
    
    @Transactional
    public T update(T t) {
        manager.merge(t);
        return t;
    }
    
    @Transactional
    public T delete(Long id) {
        T toRemove = manager.find(this.persistClass, id);
        if (toRemove != null)
            manager.remove(toRemove);
        return toRemove;
    }
    
    @Transactional
    public T delete(Object o) {
        T toRemove = manager.find(this.persistClass, o);
        if (toRemove != null)
            manager.remove(toRemove);
        return toRemove;
    }
    
    @Transactional
    public T findById(Long id) {
        return manager.find(this.persistClass, id);
    }
    
    @Transactional
    public T findByObject(Object o) {
        return manager.find(this.persistClass, o);
    }
    
    public List<T> executeQuery(String queryName) {
        TypedQuery<T> query = this.manager.createNamedQuery(queryName, this.persistClass);
        return query.getResultList();
    };
    

    public List<T> executeQuery(String queryName, HashMap<String, Object> parameters) {
        TypedQuery<T> query = this.manager.createNamedQuery(queryName, persistClass);       
        if (parameters != null) {
            for(Map.Entry<String, Object> pair : parameters.entrySet()) {
                query.setParameter(pair.getKey(), pair.getValue());
            }
        }
        return query.getResultList();
    }
    
    
}
