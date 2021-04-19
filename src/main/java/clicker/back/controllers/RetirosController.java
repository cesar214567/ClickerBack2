package clicker.back.controllers;

import clicker.back.Setup;
import clicker.back.entities.SolicitudesRetiro;
import clicker.back.services.SolicitudesRetiroService;
import clicker.back.services.UsuariosService;
import clicker.back.utils.errors.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
        if(solicitudesRetiro.getMonto()<=0){
            return ResponseService.genError("se envio un monto negativo",HttpStatus.BAD_REQUEST);
        }
        if(solicitudesRetiro.getUsuario()==null || solicitudesRetiro.getUsuario().getCorreo()==null)
            return ResponseService.genError("no se envio el usuario",HttpStatus.BAD_REQUEST);
        solicitudesRetiro.setUsuario(usuariosService.getById(solicitudesRetiro.getUsuario().getCorreo()));
        if(solicitudesRetiro.getUsuario()==null){
            return ResponseService.genError("no se encontro al usuario",HttpStatus.BAD_REQUEST);
        }
        if(solicitudesRetiro.getUsuario().getBalance()<solicitudesRetiro.getMonto()){
            return ResponseService.genError("el monto que se pide es mayor a lo que el usuario tiene",HttpStatus.NOT_ACCEPTABLE);
        }
        if(solicitudesRetiroService.checkIfExist(solicitudesRetiro.getUsuario())){
            return ResponseService.genError("existe una solicitud actualmente",HttpStatus.LOCKED);
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
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    @ResponseBody
    public ResponseEntity<Object> eliminarSolicitud(@RequestParam("id")Long id){
        try{
            if(id==null){
                return ResponseService.genError("no se envio id",HttpStatus.BAD_REQUEST);
            }
            SolicitudesRetiro solicitudesRetiro = solicitudesRetiroService.getById(id);
            if(solicitudesRetiro==null){
                return ResponseService.genError("no se encontro la solicitud",HttpStatus.BAD_REQUEST);
            }
            if(solicitudesRetiro.getAceptado()!=null){
                return ResponseService.genError("esta solicitud ya fue atendida",HttpStatus.LOCKED);
            }
            solicitudesRetiro.getUsuario().getSolicitudesRetiros().removeIf(n->n.getId().equals(id));
            usuariosService.save(solicitudesRetiro.getUsuario());
            solicitudesRetiroService.delete(solicitudesRetiro);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<Object> getSolicitudVigente(@RequestParam("id")String correo){
        try{
            if (correo==null){
                return ResponseService.genError("no se envió correo",HttpStatus.BAD_REQUEST);
            }
            List<SolicitudesRetiro> solicitudes = solicitudesRetiroService.findSolicitudVigente(correo);
            if (solicitudes.size() == 0){
                return ResponseService.genError("no se encontró solicitudes para el usuario enviado",HttpStatus.NOT_FOUND);
            }
            for (SolicitudesRetiro solicitud: solicitudes){
                if (solicitud.getAceptado() == null) {
                    return new ResponseEntity<>(solicitud, HttpStatus.OK);
                }
            }
            return ResponseService.genError("solicitud ya fue atendida",HttpStatus.ALREADY_REPORTED);
        } catch (Exception e) {
            return ResponseService.genError("fallo servidor",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
