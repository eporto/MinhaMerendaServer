/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicap.ts830.fullstack.cloud;

import java.util.Map;
import javax.ws.rs.core.Response;

/**
 *
 * @author shido
 */
public interface IImageRepository {
    public Map<String, Object> createFileParams(byte[] fileData, String fileName);
    public Response upload(Map<String, Object> params);
    public Map<String, Object> getResponseField(Response res,String...fields);
    //public Map<String, Object> parseResponse(Response response);
}
