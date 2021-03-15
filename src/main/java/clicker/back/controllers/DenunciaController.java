package clicker.back.controllers;

import clicker.back.Setup;
import clicker.back.entities.AutoSemiNuevo;
import clicker.back.entities.Denuncia;
import clicker.back.entities.Usuario;
import clicker.back.services.AutoSemiNuevoService;
import clicker.back.services.DenunciaService;
import clicker.back.services.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/denuncia")
@CrossOrigin(origins = {Setup.local/*,Setup.route*/}, allowedHeaders = "*")
public class DenunciaController {
    @Autowired
    DenunciaService denunciaService;

    @Autowired
    AutoSemiNuevoService autoSemiNuevoService;

    @Autowired
    UsuariosService usuariosService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<Object> denunciar(@RequestBody Denuncia denuncia){
        if(denuncia.getDescripcion()==null ||  denuncia.getDescripcion().equals(""))
            return new ResponseEntity<>("no se envio detalle de la denuncia",HttpStatus.BAD_REQUEST);
        if(denuncia.getAutoSemiNuevo()==null || denuncia.getUsuario()==null)
            return new ResponseEntity<>("no se envio usuario o auto",HttpStatus.BAD_REQUEST);
        Usuario denunciante = usuariosService.getById(denuncia.getUsuario().getCorreo());
        AutoSemiNuevo autoSemiNuevo = autoSemiNuevoService.getById(denuncia.getAutoSemiNuevo().getId());
        if(denunciante == null || autoSemiNuevo == null ){
             return new ResponseEntity<>("no se encontro el usuario o el vehiculo", HttpStatus.BAD_REQUEST);
        }
        if(denunciaService.getByAutoAndUsuario(autoSemiNuevo,denunciante)!=null){
            return new ResponseEntity<>("Este usuario ya reporto al vehiculo",HttpStatus.BAD_REQUEST);
        }
        denunciante.getDenuncias().add(denuncia);
        autoSemiNuevo.getDenuncias().add(denuncia);
        denuncia.setUsuario(denunciante);
        denuncia.setAutoSemiNuevo(autoSemiNuevo);
        try{
            denunciaService.save(denuncia);
            autoSemiNuevoService.setRevisado(false,autoSemiNuevo.getId());
            return new ResponseEntity<>(null,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
