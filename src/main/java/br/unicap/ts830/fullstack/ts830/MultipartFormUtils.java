/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicap.ts830.fullstack.ts830;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.MultivaluedMap;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import sun.misc.IOUtils;

/**
 *
 * @author shido
 */
public class MultipartFormUtils {
    Map<String, List<InputPart>> uploadForm;
    
    public MultipartFormUtils(Map<String, List<InputPart>> uploadForm) {
        this.uploadForm = uploadForm;
    }
    
    public String getFileName(String key) {
        List<InputPart> inputParts = this.uploadForm.get(key);
        
        if(inputParts.isEmpty())
            return "";
        
        InputPart inputPart = inputParts.get(0);
        MultivaluedMap<String, String> header = inputPart.getHeaders();
        
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }
    
    /*
    public byte[] readFormFile(String key) {
        List<InputPart> inputParts = this.uploadForm.get(key);
        byte[] bytes = new byte[0];
        
        if(inputParts.isEmpty())
            return bytes;
        
        InputStream inputStream;
        for (InputPart inputPart : inputParts) {
            try {
        //        MultivaluedMap<String, String> header = inputPart.getHeaders();
        //        String fileName = getFileName(header);

                //convert the uploaded file to inputstream
                inputStream = inputPart.getBody(InputStream.class,null);


                //byte [] bytes = IOUtils.toByteArray(inputStream);
                bytes = IOUtils.readFully(inputStream, -1, false);
                //Guava: byte[] bytes = ByteStreams.toByteArray(inputStream);
                //Map<String, Object> imageParams = imageService.createFileParams(bytes, fileName);

                //imageServiceResponse = imageService.upload(imageParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
	}
        
        return bytes;
    }*/
    
    public byte[] readFormFile(String key) {
        List<InputPart> inputParts = this.uploadForm.get(key);
        byte[] bytes = new byte[0];
        
        if(inputParts.isEmpty())
            return bytes;
        
        InputPart part = inputParts.get(0);
        InputStream inputStream;
        
        try {
            inputStream = part.getBody(InputStream.class,null);


            //byte [] bytes = IOUtils.toByteArray(inputStream);
            bytes = IOUtils.readFully(inputStream, -1, false);
            //Guava: byte[] bytes = ByteStreams.toByteArray(inputStream);
            //Map<String, Object> imageParams = imageService.createFileParams(bytes, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return bytes;
    }
    
    public String readFormString(String key) throws IOException {
        List<InputPart> inputParts = this.uploadForm.get(key); 
        
        if(inputParts.isEmpty())
            return "";
        
        return inputParts.get(0).getBodyAsString();
    }
}
