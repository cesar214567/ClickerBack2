package clicker.back.controllers.utils;

import clicker.back.Setup;
import clicker.back.entities.VentaSemiNuevo;
import clicker.back.services.AmazonService;
import clicker.back.services.UsuariosService;
import clicker.back.utils.entities.Incidencias;
import clicker.back.utils.errors.ResponseService;
import clicker.back.utils.services.IncidenciasService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@RestController
@RequestMapping(value = "/incidencias")
@CrossOrigin(origins = {Setup.local/*,Setup.route*/}, allowedHeaders = "*")
public class IncidenciasController {
    @Autowired
    IncidenciasService incidenciasService;

    @Autowired
    UsuariosService usuariosService;

    @Autowired
    AmazonService amazonService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<Object> getAll(){
        try{
            return ResponseService.genSuccess(incidenciasService.getAll());
        }catch (Exception e){
            return ResponseService.genError("fallo", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Object> save(HttpServletRequest request){
        try{
            MultipartFile file = null;
            String model="{}";
            for (Part part:request.getParts()){
                if(part.getContentType()!=null ){
                    file = new MockMultipartFile(part.getSubmittedFileName(),part.getSubmittedFileName(),part.getContentType(),part.getInputStream());
                }else{
                    String theString = IOUtils.toString(part.getInputStream(), StandardCharsets.UTF_8);
                    model = String.valueOf(theString);
                }
            }
            ObjectMapper objectMapper = new ObjectMapper();
            Incidencias incidencias = objectMapper.readValue(model,Incidencias.class);

            if(incidencias.getUsuario()==null || incidencias.getUsuario().getId()==null){
                return ResponseService.genError("no se mando el usuario",HttpStatus.BAD_REQUEST);
            }
            if(!usuariosService.existById(incidencias.getUsuario().getId())){
                return ResponseService.genError("no se encontro al usuario",HttpStatus.NOT_FOUND);
            }
            incidencias.setUsuario(usuariosService.getById(incidencias.getUsuario().getId()));
            incidencias.setId(null);
            incidencias.setDate(new Date());
            if (file!=null){
                incidencias.setFoto(amazonService.uploadFile(file,incidencias.getUsuario().getId().toString(),"fotosIncidencias/"+incidencias.getId()));
            }else{
                incidencias.setFoto(null);
            }
            incidencias = incidenciasService.save(incidencias);
            return ResponseService.genSuccess(incidencias);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
