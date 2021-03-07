package clicker.back.controllers;

import clicker.back.Setup;
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
        solicitudesRetiro.setAceptado(false);
        solicitudesRetiro.setDate(new Date());
        solicitudesRetiro.setAceptado(null);
        solicitudesRetiro.setTransferencia(null);
        solicitudesRetiro.getUsuario().getSolicitudesRetiros().add(solicitudesRetiro);
        try{
            return new ResponseEntity<>(solicitudesRetiroService.save(solicitudesRetiro),HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
