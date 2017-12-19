package br.unicap.ts830.fullstack.endpoint;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import br.unicap.ts830.fullstack.model.Avaliacao;
import br.unicap.ts830.fullstack.model.Escola;
import br.unicap.ts830.fullstack.model.Foto;
import br.unicap.ts830.fullstack.model.Geometry;
import br.unicap.ts830.fullstack.model.Register;
import br.unicap.ts830.fullstack.model.Usuario;
import br.unicap.ts830.fullstack.service.AvaliacaoService;
import br.unicap.ts830.fullstack.service.EscolaService;
import br.unicap.ts830.fullstack.service.FotoService;
import br.unicap.ts830.fullstack.service.GeometryService;
import br.unicap.ts830.fullstack.cloud.ImageService;
import br.unicap.ts830.fullstack.service.RegisterService;
import br.unicap.ts830.fullstack.service.UsuarioService;
import br.unicap.ts830.fullstack.ts830.MultipartFormUtils;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
//import javax.json.Json;
//import javax.json.JsonArrayBuilder;

/**
 *
 * @author ts830
 */
@Path("ts830")
public class TACFullStackEndPoint { 
    @Inject
    AvaliacaoService avaliacaoService;
    
    @Inject 
    EscolaService escolaService;
    
    @Inject
    UsuarioService usuarioService;
    
    @Inject
    RegisterService registerService;
    
    @Inject
    GeometryService geometryService;
    
    @Inject
    FotoService fotoService;
    
    @GET
    @Produces("text/plain")
    public String doHello() throws Exception {
        return "It's Running !";
    }
    
    /*****************************************
     *             ENDPOINTS                 *
     *****************************************/
    
    /**
     * Verifica se já existe uma AppKey no banco, isso é necessário pelo fato que alguns aplicativos podem ter sido removido do disposítivo e re-instalado novamente.
     * Nesse caso a AppKey não muda.
     * 
     * @param r Register Class Json
     * @return Token || Null
     * @throws Exception 
     */
    @POST
    @Path("register")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response registerApplication(Register r) throws Exception {
        try {
            //Verifica no banco se existe um Registro com appKey enviada
            List<Register> selectedRegister = this.registerService.getToken(r.getAppKey());

            //Se não existe nenhum registro da AppKey no banco, então iremos criar um token e associar a appkey. Retornaremos o token
            if (selectedRegister.isEmpty()) {
                final String secret = "Hello World!";
                String toToken = r.getAppKey() + secret;

                MessageDigest mDigest = MessageDigest.getInstance("MD5");
                mDigest.update(toToken.getBytes());

                byte[] token = Base64.getEncoder().encode(mDigest.digest());
                String tokenBase64 = new String(token);

                r.setAppToken(tokenBase64); 
                registerService.insert(r);

                return Response.status(Response.Status.OK).entity(tokenBase64).build();
            } else
                //Caso já exista um registro com aquela AppKey, retornamos o token encontrado
                return Response.status(Response.Status.OK).entity(selectedRegister.get(0).getAppToken()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error in Register").build();
        }
    }
    
    @POST
    @Path("create/avaliacao/foto")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addFoto(MultipartFormDataInput input, @Context HttpHeaders headers) {
        if(!this.hasAuthorization(headers))
            return Response.status(Response.Status.UNAUTHORIZED).entity("Credencial Inválida").build();
        
        MultipartFormUtils formUtils = new MultipartFormUtils(input.getFormDataMap());
        ImageService imageService = new ImageService();
        Response imageServiceResponse = null;
        
        //Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        //List<InputPart> inputParts = uploadForm.get("uploadedFile");
        //List<InputPart> avaliacao = uploadForm.get("avaliacaoid");
        
        try {
            String fileName = formUtils.getFileName("uploadedFile");
            byte[] fileData = formUtils.readFormFile("uploadedFile");
            
            Map<String, Object> imageParams = imageService.createFileParams(fileData, fileName);
            imageServiceResponse = imageService.upload(imageParams);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*for (InputPart inputPart : inputParts) {
            try {
                MultivaluedMap<String, String> header = inputPart.getHeaders();
                String fileName = getFileName(header);

                //convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class,null);
                

                //byte [] bytes = IOUtils.toByteArray(inputStream);
                byte [] bytes = IOUtils.readFully(inputStream, -1, false);
                //Guava: byte[] bytes = ByteStreams.toByteArray(inputStream);
                Map<String, Object> imageParams = imageService.createFileParams(bytes, fileName);

                imageServiceResponse = imageService.upload(imageParams);

            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao salvar a Imagem").build();
            }
	}*/

        try {
            if(imageServiceResponse == null)
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao salvar a Imagem").build();
            
            Long id = Long.parseLong(formUtils.readFormString("avaliacaoid"));
            Foto f = imageService.createImageForPersistence(imageServiceResponse);
            this.fotoService.insert(f);
            
            Avaliacao updateAvaliacao = this.avaliacaoService.findById(id);
            updateAvaliacao.setFoto(f);
            this.avaliacaoService.update(updateAvaliacao);
            
            return Response.status(Response.Status.OK).entity("Avaliação Adicionada !").build();
            
        } catch(Exception e) {
             e.printStackTrace();
             return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro").build();
        }
    }
    
    
    @POST
    @Path("create/avaliacao")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response addAvaliacao(Avaliacao a, @Context HttpHeaders headers) throws Exception {
        if(!this.hasAuthorization(headers))
            return Response.status(Response.Status.UNAUTHORIZED).entity("Credencial Inválida").build();
        
        try {
            Avaliacao inserted = this.avaliacaoService.insert(a);
            return Response.status(Response.Status.OK).entity(inserted.getId()).build();    
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro Inserindo Avaliação").build();
        }
    }
    
    @GET
    @Path("list/avaliacao")
    @Produces("application/json")
    public Response listAvaliacao(@Context HttpHeaders headers) throws Exception {
        if(!this.hasAuthorization(headers)) 
            return Response.status(Response.Status.UNAUTHORIZED).entity(Collections.EMPTY_LIST).build(); //401

        try {
            List<Avaliacao> avaliacaoResult = avaliacaoService.getAvaliacao();
            return Response.status(Response.Status.OK).entity(avaliacaoResult).build(); //200
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Collections.EMPTY_LIST).build(); //500
        }
    }
    
    @GET
    @Path("list/avaliacao/{id}")
    @Produces("application/json")
    public Response listAvaliacaoByEscola(@PathParam("id") String id, @Context HttpHeaders headers) throws Exception {
        if(!this.hasAuthorization(headers))
            return Response.status(Response.Status.UNAUTHORIZED).entity(Collections.EMPTY_LIST).build(); //401

        try {
            List<Avaliacao> avaliacaoResult = avaliacaoService.findAvaliacao(Long.parseLong(id));
            return Response.status(Response.Status.OK).entity(avaliacaoResult).build(); //200
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Collections.EMPTY_LIST).build(); //500
        }
    }
    
    @POST
    @Path("create/escola")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response addEscola(Escola e, @Context HttpHeaders headers) throws Exception {
        if(!this.hasAuthorization(headers))
            return Response.status(Response.Status.UNAUTHORIZED).entity("Credencial Inválida").build();

        try {
            this.escolaService.insert(e);
            return Response.status(Response.Status.OK).entity("Escola Adicionada!").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao Criar Escola").build();
        }
    }
    
    
    @GET
    @Path("list/escola")
    @Produces("application/json")
    public Response listEscola() throws Exception {
        try {
            List<Escola> escolas = this.escolaService.getEscola();
            return Response.status(Response.Status.OK).entity(escolas).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Collections.EMPTY_LIST).build();
        }
    }
    
    @POST
    @Path("create/usuario")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response addUsuario(Usuario u, @Context HttpHeaders headers) throws Exception {
         if(!this.hasAuthorization(headers)) 
            return Response.status(Response.Status.UNAUTHORIZED).entity("Credencial Inválida").build();

        try {
            this.usuarioService.insert(u);
            return Response.status(Response.Status.OK).entity("Usuário Adicionado !").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao Adicionar Usuário").build();
        }
    }
    
    @GET
    @Path("list/usuario")
    @Produces("application/json")
    public Response listEscola(@Context HttpHeaders headers) throws Exception {
        if(!this.hasAuthorization(headers))
            return Response.status(Response.Status.UNAUTHORIZED).entity(Collections.EMPTY_LIST).build(); //401

        try {
            List<Usuario> usuarioResult = this.usuarioService.getUsuario();
            return Response.status(Response.Status.OK).entity(usuarioResult).build(); //200
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Collections.EMPTY_LIST).build(); //200
        }
    }
    
    @GET
    @Path("list/geometry")
    @Produces("application/json")
    public Response listGeometry() throws Exception {
        try {
            List<Geometry> geometries = this.geometryService.getGeometries();
            return Response.status(Response.Status.OK).entity(geometries).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Collections.EMPTY_LIST).build();
        }
    }
    
    @GET
    @Path("list/geometry/{id}")
    @Produces("application/json")
    public Response listGeometry(@PathParam("id") String id) {
        try {
            List<Geometry> geometry = this.geometryService.findGeometry(Integer.parseInt(id));
            if(geometry.isEmpty())
                return Response.status(Response.Status.NOT_FOUND).entity(Collections.EMPTY_LIST).build();
            
            return Response.status(Response.Status.OK).entity(geometry).build();
        } catch (Exception e) {
            System.out.println("[Error: list/geometry/{id}]: "+e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Collections.EMPTY_LIST).build();
        }
    }
    
    /*****************************************
     *                UTILS                  *
     *****************************************/
    
    public boolean hasAuthorization(HttpHeaders headers) {
        //Obtém as credenciais enviadas pelo Header
        List<String> headerToken = headers.getRequestHeader("Authorization");
        List<String> headerKey = headers.getRequestHeader("Appkey");
        
        //Se alguma credencial estiver faltando, não precisa continuar
        if(!headerToken.isEmpty() && !headerKey.isEmpty()) {
            String token = headerToken.get(0);
            String appKey = headerKey.get(0);
            
            //Uma vez que algum tipo de credencial tenha sido enviada nos Headers, precisa verificar agora se ela existe no Banco e se bate com a AppKey enviada
            List<Register> tokenResult = registerService.getToken(appKey);
            
            if(!tokenResult.isEmpty()) {    
                if(token.equals(tokenResult.get(0).getAppToken())) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * header sample
     * {
     * 	Content-Type: image/gif
     * 	Content-Disposition: form-data; name="datafile1"; filename="somefile.gif"
     * }
     **/
    private String getFileName(MultivaluedMap<String, String> header) {
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
   
}
     /* JsonArrayBuilder ja = Json.createArrayBuilder();
        l.forEach((a) -> {
            ja.add(Json.createObjectBuilder()
                    .add("ID", a.getId())
                    //.add("Usuario", a.getUsuarioID().getTipo())
                    .add("Nome", a.getEscolaID().getEscolaNome())
                    .add("Pontuação", a.getPontuacao())
                    .add("Foto", a.getFoto())
                    
            );
        });
        return ja.build().toString();*/ 