package clicker.back.controllers;

import clicker.back.Setup;
import clicker.back.antiguo.Solicitudes;
import clicker.back.entities.SolicitudesRetiro;
import clicker.back.entities.Usuario;
import clicker.back.services.SolicitudesRetiroService;
import clicker.back.services.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/solicitudRetiro")
@CrossOrigin(origins = {Setup.local/*,Setup.route*/}, allowedHeaders = "*")
public class RetirosController {

    @Autowired
    UsuariosService usuariosService;

    @Autowired
    SolicitudesRetiroService solicitudesRetiroService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<Object> retirarDinero(@RequestBody SolicitudesRetiro solicitudesRetiro){
        if(solicitudesRetiro.getMonto()<=0) return new ResponseEntity<>("se envio un monto negativo",HttpStatus.BAD_REQUEST);
        if(solicitudesRetiro.getUsuario()==null || solicitudesRetiro.getUsuario().getCorreo()==null)
            return new ResponseEntity<>("no se envio el usuario", HttpStatus.BAD_REQUEST);
        solicitudesRetiro.setUsuario(usuariosService.getById(solicitudesRetiro.getUsuario().getCorreo()));
        if(solicitudesRetiro.getUsuario()==null)return new ResponseEntity<>("no se encontro al usuario",HttpStatus.BAD_REQUEST);
        if(solicitudesRetiro.getUsuario().getBalance()<solicitudesRetiro.getMonto())return new ResponseEntity<>("el monto que se pide es mayor a lo que el usuario tiene",HttpStatus.NOT_ACCEPTABLE);
        if(solicitudesRetiroService.checkIfExist(solicitudesRetiro.getUsuario())){
            return new ResponseEntity<>("existe una solicitud actualmente",HttpStatus.LOCKED);
        }
        solicitudesRetiro.setAceptado(false);
        solicitudesRetiro.setDate(new Date());
        solicitudesRetiro.setAceptado(null);
        solicitudesRetiro.setTransferencia(null);
        solicitudesRetiro.getUsuario().getSolicitudesRetiros().add(solicitudesRetiro);
        try{
            solicitudesRetiroService.save(solicitudesRetiro);
            return new ResponseEntity<>(null,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    @ResponseBody
    public ResponseEntity<Object> eliminarSolicitud(@RequestParam("id")Long id){
        try{
            if(id==null)return new ResponseEntity<>("no se envio id ",HttpStatus.BAD_REQUEST);
            SolicitudesRetiro solicitudesRetiro = solicitudesRetiroService.getById(id);
            if(solicitudesRetiro==null) return new ResponseEntity<>("no se encontro la solicitud",HttpStatus.BAD_REQUEST);
            if(solicitudesRetiro.getAceptado()!=null)return new ResponseEntity<>("esta solicitud ya fue atendida",HttpStatus.LOCKED);
            solicitudesRetiro.getUsuario().getSolicitudesRetiros().removeIf(n->n.getId().equals(id));
            usuariosService.save(solicitudesRetiro.getUsuario());
            solicitudesRetiroService.delete(solicitudesRetiro);
            return new ResponseEntity<>("ok",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
