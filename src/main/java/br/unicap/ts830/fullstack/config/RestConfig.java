package br.unicap.ts830.fullstack.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author shido
 */
@javax.ws.rs.ApplicationPath("rest")
public class RestConfig extends Application {
    
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(br.unicap.ts830.fullstack.endpoint.TACFullStackEndPoint.class);
    }
    
}
