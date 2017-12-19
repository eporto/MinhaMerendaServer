package br.unicap.ts830.fullstack.cloud;

import br.unicap.ts830.fullstack.model.Foto;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import org.apache.http.client.*;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import javax.ws.rs.core.Response;
/**
 *
 * @author shido
 */
public class ImageService {
    IImageRepository imageRepository = new CloudinaryRepository();
    
    public ImageService() {}
    
    public Response upload(Map<String, Object> params) throws Exception {
        
        return imageRepository.upload(params);
        
        /*Client client = ClientBuilder.newClient();

        WebTarget target = client.target(CloudinaryRepository.UPLOAD_ENDPOINT);

        MultipartFormDataOutput mdo = new MultipartFormDataOutput();
        mdo.addFormData("file", fileData, MediaType.APPLICATION_OCTET_STREAM_TYPE, fileName);
        mdo.addFormData("upload_preset", CloudinaryRepository.UPLOAD_PRESET, MediaType.TEXT_PLAIN_TYPE);
        GenericEntity<MultipartFormDataOutput> entity = new GenericEntity<MultipartFormDataOutput>(mdo) {};

        Response r = target.request()
                .acceptEncoding("gzip, deflate")
                .post(Entity.entity(entity, MediaType.MULTIPART_FORM_DATA_TYPE));
        
        System.out.println();
        String output = r.readEntity(String.class);
        System.out.println(output);*/
    }
    
    public Map<String, Object> createFileParams(byte[] fileData, String fileName) {
        return imageRepository.createFileParams(fileData, fileName);
    }
    
    /*public Map<String, Object> parseResponse(Response res) {
        return this.imageRepository.parseResponse(res);
    }*/
    
    public Map<String, Object> getResponseField(Response res, String...fields) {
        return this.imageRepository.getResponseField(res,fields);
    }
    
    public Foto createImageForPersistence(Response res) {
        Foto f = new Foto();
        Map<String, Object> imageOptions = this.getResponseField(res, CloudinaryRepository.RESPONSE_URL, CloudinaryRepository.RESPONSE_FORMAT);
        
        f.setPath((String)imageOptions.get(CloudinaryRepository.RESPONSE_URL));
        return f;
    }
       
    private void writeFile(byte[] content, String filePath, String fileName) throws Exception {
        File file = new File(filePath + fileName);

        if (!file.exists())
            file.createNewFile();

        try (FileOutputStream fop = new FileOutputStream(file)) {
            fop.write(content);
            fop.flush();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
