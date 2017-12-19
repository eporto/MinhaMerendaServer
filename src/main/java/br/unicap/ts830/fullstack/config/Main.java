package br.unicap.ts830.fullstack.config;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.jpa.postgresql.PostgreSQLJPAFraction;

public class Main {
  public static void main(String[] args) throws Exception {
    Container container = new Container();

    container.fraction(new DatasourcesFraction()
        .jdbcDriver("postgresql", (d) -> {
          d.driverClassName("org.postgresql.Driver");
          d.driverModuleName("org.postgresql");
        })
        .dataSource("MyPU", (ds) -> {
          ds.driverName("postgresql");
          ds.connectionUrl(System.getenv("JDBC_DATABASE_URL"));
          ds.userName(System.getenv("JDBC_DATABASE_USERNAME"));
          ds.password(System.getenv("JDBC_DATABASE_PASSWORD"));
          
          //ds.connectionUrl("jdbc:postgresql://localhost/postgres");
          //ds.userName("postgres");
          
            //ds.password(""); //Não utilizado no postgresql localhost
        })
    );

    // Prevent JPA Fraction from installing it's default datasource fraction
    container.fraction(new PostgreSQLJPAFraction()
        .inhibitDefaultDatasource()
        .defaultDatasource("jboss/datasources/MyPU")
    );

    container.start();

    JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
    deployment.addClasses(br.unicap.ts830.fullstack.ts830.MultipartFormUtils.class);
    deployment.addClasses(br.unicap.ts830.fullstack.model.Avaliacao.class);
    deployment.addClasses(br.unicap.ts830.fullstack.model.Escola.class);
    deployment.addClasses(br.unicap.ts830.fullstack.model.Usuario.class);
    deployment.addClasses(br.unicap.ts830.fullstack.model.Register.class);
    deployment.addClasses(br.unicap.ts830.fullstack.model.Geometry.class);
    deployment.addClasses(br.unicap.ts830.fullstack.model.Foto.class);
    deployment.addClasses(br.unicap.ts830.fullstack.persistence.GenericDAO.class);
    deployment.addClasses(br.unicap.ts830.fullstack.service.AvaliacaoService.class);
    deployment.addClasses(br.unicap.ts830.fullstack.service.EscolaService.class);
    deployment.addClasses(br.unicap.ts830.fullstack.service.UsuarioService.class);  
    deployment.addClasses(br.unicap.ts830.fullstack.service.RegisterService.class);  
    deployment.addClasses(br.unicap.ts830.fullstack.service.GeometryService.class);
    deployment.addClasses(br.unicap.ts830.fullstack.service.FotoService.class);
    deployment.addClasses(br.unicap.ts830.fullstack.cloud.ImageService.class);
    deployment.addClasses(br.unicap.ts830.fullstack.cloud.IImageRepository.class);
    deployment.addClasses(br.unicap.ts830.fullstack.cloud.CloudinaryRepository.class);
    deployment.addAsWebInfResource(new ClassLoaderAsset("META-INF/persistence.xml", Main.class.getClassLoader()), "classes/META-INF/persistence.xml");
    //deployment.addAsWebInfResource(new ClassLoaderAsset("META-INF/load.sql", Main.class.getClassLoader()), "classes/META-INF/load.sql");
    deployment.addResource(br.unicap.ts830.fullstack.endpoint.TACFullStackEndPoint.class);
    deployment.addAllDependencies();

    container.deploy(deployment);
  }
}