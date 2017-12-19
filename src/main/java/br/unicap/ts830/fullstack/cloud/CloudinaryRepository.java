/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicap.ts830.fullstack.cloud;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;

/**
 *
 * @author shido
 */
public class CloudinaryRepository implements IImageRepository {
    final static String API_NAME = "";
    final static String API_KEY = "";
    final static String API_SECRET = "";
    final static String UPLOAD_PRESET = "";
    
    final static String UPLOAD_ENDPOINT = "";
    
    public final static String RESPONSE_PUBLIC_ID = "public_id";
    public final static String RESPONSE_URL = "url";
    public final static String RESPONSE_FORMAT = "format";
    public final static String RESPONSE_FILENAME = "original_filename";
    public final static String RESPONSE_FILE_WIDTH = "width";
    public final static String RESPONSE_FILE_HEIGHT = "height";

    public CloudinaryRepository() {}
    
    @Override
    public Response upload(Map<String, Object> params) {
        byte[] fileData = (byte[]) params.get("file");
        String fileName = (String) params.get("fileName");
        
/*        
        params.entrySet().forEach(entity -> {
            String key = entity.getKey();
            List<Object> paramInfo = new ArrayList<>();
            
            if(key.equals("file")) {
                paramInfo.add(entity.getValue());
                paramInfo.add(MediaType.APPLICATION_OCTET_STREAM_TYPE);
            } else if (key.equals("fileName")) 
        });
*/        
        GenericEntity<MultipartFormDataOutput> entity = createCloudinaryPost(fileData, fileName);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(CloudinaryRepository.UPLOAD_ENDPOINT);


        return target.request()
                .acceptEncoding("gzip, deflate")
                .post(Entity.entity(entity, MediaType.MULTIPART_FORM_DATA_TYPE));       
    }
    
    private GenericEntity<MultipartFormDataOutput> createCloudinaryPost(byte[] fileData, String fileName) {
        MultipartFormDataOutput form = new MultipartFormDataOutput();
        form.addFormData("file", fileData, MediaType.APPLICATION_OCTET_STREAM_TYPE, fileName);
        form.addFormData("upload_preset", CloudinaryRepository.UPLOAD_PRESET, MediaType.TEXT_PLAIN_TYPE);
        /*params.entrySet().forEach((entry) -> {
            String key = entry.getKey();
            List data = entry.getValue();
            
            if (key.equals("file"))
                form.addFormData(key, data.get(0), (MediaType) data.get(1), (String) data.get(2));
            else if (key.equals("upload_preset"))
                form.addFormData(key, data.get(0), (MediaType) data.get(1));
            //query.setParameter(pair.getKey(), pair.getValue());
        });*/
        
        //mdo.addFormData("file", fileData, MediaType.APPLICATION_OCTET_STREAM_TYPE, fileName);
        //mdo.addFormData("upload_preset", CloudinaryRepository.UPLOAD_PRESET, MediaType.TEXT_PLAIN_TYPE);
        return new GenericEntity<MultipartFormDataOutput>(form) {};
    }
    
    @Override
    public Map<String, Object> getResponseField(Response response, String...fields) {
        Map<String, Object> map = parseResponse(response);
        
        if (fields.length == 0)
            return map;

        Map<String, Object> parsedResponse = new HashMap<>();
        for(String field : fields) {
            Object value = map.get(field);
            if(value != null)
                parsedResponse.put(field,value);
        }
        
        return parsedResponse;
    }
    
    @Override
    public Map<String, Object> createFileParams(byte[] fileData, String fileName) {
        Map<String, Object> params = new HashMap<>();
        params.put("file", fileData);
        params.put("fileName", fileName);
        return params;
    }

    public Map<String, Object> parseResponse(Response response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response.readEntity(String.class), new TypeReference<Map<String, Object>>(){});
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.EMPTY_MAP;
        }
    }
}