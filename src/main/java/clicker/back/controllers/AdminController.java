package clicker.back.controllers;

import clicker.back.Setup;
import clicker.back.entities.*;
import clicker.back.repositories.SolicitudesRetiroRepository;
import clicker.back.services.*;
import com.google.gson.JsonArray;
import com.sendgrid.Response;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.http.client.protocol.ResponseProcessCookies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

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

    @Autowired
    AutoSemiNuevoService autoSemiNuevoService;

    @Autowired
    InteresadoCompraService interesadoCompraService;
    @Autowired
    InteresadoReventaService interesadoReventaService;

    @GetMapping(value = "/Retiros")
    @ResponseBody
    public ResponseEntity<Object> getSolicitudesRegistro(){
        try{
            return  new ResponseEntity<>(solicitudesRetiroService.getPendientes(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/Retiros")
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
            registroBalance = new RegistroBalance(new Date(), (float) (-1*original.getMonto()),solicitudesRetiro.getUsuario(),"retiro de "+original.getMonto()+" soles");
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

    @GetMapping(value = "/interesados")
    @ResponseBody
    public ResponseEntity<Object> getAllInteresados(){
        JSONArray jsonArray = new JSONArray();
        List<AutoSemiNuevo> list = autoSemiNuevoService.getAllEnabled(true,true,false);
        for (AutoSemiNuevo autoSemiNuevo : list) {
            List<InteresadoCompra> interesadoCompraList = interesadoCompraService.getAllByAuto(autoSemiNuevo);
            List<InteresadoReventa> interesadoReventasList = interesadoReventaService.getAllByAuto(autoSemiNuevo);
            interesadoCompraList.forEach(t->t.setAutoSemiNuevo(null));
            interesadoReventasList.forEach(t->t.setAutoSemiNuevo(null));
            if (interesadoCompraList.size()!=0 || interesadoReventasList.size()!=0 ){
                JSONObject object = new JSONObject();
                object.put("auto",autoSemiNuevo);
                object.put("interesadosReventa",interesadoReventasList);
                object.put("interesadosCompra",interesadoCompraList);
                jsonArray.appendElement(object);
            }

        }
        try{
            return new ResponseEntity<>(jsonArray,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/reported")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getAllReported() {
        List<AutoSemiNuevo> autoSemiNuevos = autoSemiNuevoService.getReportados();
        if(autoSemiNuevos==null){
            return new ResponseEntity<>("BAD",HttpStatus.BAD_REQUEST);
        }
        for (AutoSemiNuevo autoSemiNuevo : autoSemiNuevos) {
            if (autoSemiNuevo.getDenuncias()!=null){
                for (Denuncia denuncia : autoSemiNuevo.getDenuncias()) {
                    denuncia.getUsuario().setNumeroDenuncias((long) denuncia.getUsuario().getDenuncias().size());
                }
            }
        }
        return new ResponseEntity<>(autoSemiNuevos,HttpStatus.OK);
    }

    @PutMapping(value = "/reported")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> validate(@RequestParam("id") Long idAuto){
        try{
            autoSemiNuevoService.setRevisado(true,idAuto);
            return new ResponseEntity<>(null,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> remove(@RequestParam("id") Long idAuto){
        try{
            autoSemiNuevoService.borrarAuto(idAuto);
            return new ResponseEntity<>(null,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
