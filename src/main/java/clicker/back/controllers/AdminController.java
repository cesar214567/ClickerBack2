package clicker.back.controllers;

import clicker.back.Setup;
import clicker.back.entities.RegistroBalance;
import clicker.back.entities.SolicitudesRetiro;
import clicker.back.repositories.SolicitudesRetiroRepository;
import clicker.back.services.RegistroBalanceService;
import clicker.back.services.SolicitudesRetiroService;
import clicker.back.services.UsersService;
import clicker.back.services.UsuariosService;
import com.sendgrid.Response;
import org.apache.http.client.protocol.ResponseProcessCookies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Date;

@RestController
@RequestMapping(value = "/admin")
@CrossOrigin(origins = {Setup.local/*,Setup.route*/}, allowedHeaders = "*")
public class AdminController {

    @Autowired
    SolicitudesRetiroService solicitudesRetiroService;

    @Autowired
    UsersService usersService;

    @Autowired
    UsuariosService usuariosService;

    @Autowired
    RegistroBalanceService registroBalanceService;

    @GetMapping(value = "/Retiros")
    @ResponseBody
    public ResponseEntity<Object> getSolicitudesRegistro(){
        try{
            return  new ResponseEntity<>(solicitudesRetiroService.getPendientes(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/Retiros")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> acceptSolicitud(@RequestBody SolicitudesRetiro solicitudesRetiro){
        if(solicitudesRetiro.getId()==null){
            return new ResponseEntity<>("no se envio el id ",HttpStatus.BAD_REQUEST);
        }
        if(solicitudesRetiro.getAceptado()==null){
            return new ResponseEntity<>("no se envio la acpetacion",HttpStatus.BAD_REQUEST);
        }
        SolicitudesRetiro original = solicitudesRetiroService.getById(solicitudesRetiro.getId());
        if(original==null || original.getAceptado()!=null){
            return new ResponseEntity<>("no se encontro la solicitud o ya fue resuelta",HttpStatus.BAD_REQUEST);
        }
        original.setAceptado(solicitudesRetiro.getAceptado());
        original.setTransferencia(solicitudesRetiro.getTransferencia());
        if(solicitudesRetiro.getAdmin()==null || solicitudesRetiro.getAdmin().getEmail()==null){
            return new ResponseEntity<>("no se enviaron los datos del admin",HttpStatus.BAD_REQUEST);
        }
        original.setAdmin(usersService.getByEmail(solicitudesRetiro.getAdmin().getEmail()));
        if(solicitudesRetiro.getAdmin()==null){
            return new ResponseEntity<>("no se encontro al admin",HttpStatus.BAD_REQUEST);
        }
        RegistroBalance registroBalance = null;
        if(original.getAceptado()){
            original.getUsuario().setBalance(original.getUsuario().getBalance()-original.getMonto());
            registroBalance = new RegistroBalance(new Date(),-1*original.getMonto(),solicitudesRetiro.getUsuario(),"retiro de "+original.getMonto()+" soles");
            original.getUsuario().getHistorialBalance().add(registroBalance);
        }
        try{
            solicitudesRetiroService.save(original);
            usuariosService.save(original.getUsuario());

            return new ResponseEntity<>(null,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<Object> getAll(){
        return new ResponseEntity<>(registroBalanceService.getAll(),HttpStatus.OK);
    }



}
