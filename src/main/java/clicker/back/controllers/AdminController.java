package clicker.back.controllers;

import clicker.back.Setup;
import clicker.back.entities.*;
import clicker.back.services.*;
import clicker.back.utils.errors.ResponseService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(value = "/admin")
@CrossOrigin(origins = {Setup.local/*,Setup.route*/}, allowedHeaders = "*")
public class    AdminController {

    @Autowired
    SolicitudesRetiroService solicitudesRetiroService;

    @Autowired
    UsersService usersService;

    @Autowired
    UsuariosService usuariosService;

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
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/Retiros")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> acceptSolicitud(@RequestBody SolicitudesRetiro solicitudesRetiro){
        if(solicitudesRetiro.getId()==null){
            return ResponseService.genError("no se envio el id",HttpStatus.BAD_REQUEST);
        }
        if(solicitudesRetiro.getAceptado()==null){
            return ResponseService.genError("no se envio la aceptacion",HttpStatus.BAD_REQUEST);
        }
        SolicitudesRetiro original = solicitudesRetiroService.getById(solicitudesRetiro.getId());
        if(original==null || original.getAceptado()!=null){
            return ResponseService.genError("no se encontro la solicitud o ya fue resuelta",HttpStatus.BAD_REQUEST);
        }
        original.setAceptado(solicitudesRetiro.getAceptado());
        original.setTransferencia(solicitudesRetiro.getTransferencia());
        if(solicitudesRetiro.getAdmin()==null || solicitudesRetiro.getAdmin().getEmail()==null){
            return ResponseService.genError("no se enviaron los datos del admin",HttpStatus.BAD_REQUEST);
        }
        original.setAdmin(usersService.getByEmail(solicitudesRetiro.getAdmin().getEmail()));
        if(solicitudesRetiro.getAdmin()==null){
            return ResponseService.genError("no se encontro al admin",HttpStatus.BAD_REQUEST);
        }

        if(original.getAceptado()){
            original.getUsuario().setBalance(original.getUsuario().getBalance()-original.getMonto());
        }
        try{
            solicitudesRetiroService.save(original);
            usuariosService.save(original.getUsuario());

            return new ResponseEntity<>(null,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/interesados")
    @ResponseBody
    public ResponseEntity<Object> getAllInteresados(){
        try{
            JSONArray jsonArray = new JSONArray();
            List<AutoSemiNuevo> list = autoSemiNuevoService.getAllEnabled(true,true,false);
            for (AutoSemiNuevo autoSemiNuevo : list) {
                List<InteresadoCompra> interesadoCompraList = interesadoCompraService.getAllByAuto(autoSemiNuevo);
                List<InteresadoReventa> interesadoReventasList = interesadoReventaService.getAllByAuto(autoSemiNuevo);
                interesadoCompraList.forEach(t->t.setAutoSemiNuevo(null));
                if (interesadoCompraList.size()!=0 || interesadoReventasList.size()!=0 ){
                    JSONObject object = new JSONObject();
                    object.put("auto",autoSemiNuevo);
                    object.put("interesadosReventa",interesadoReventasList);
                    object.put("interesadosCompra",interesadoCompraList);
                    jsonArray.appendElement(object);
                }

            }
            return new ResponseEntity<>(jsonArray,HttpStatus.OK);
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/reported")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getAllReported() {
        try{
            List<AutoSemiNuevo> autoSemiNuevos = autoSemiNuevoService.getReportados();

            for (AutoSemiNuevo autoSemiNuevo : autoSemiNuevos) {
                if (autoSemiNuevo.getDenuncias()!=null){
                    for (Denuncia denuncia : autoSemiNuevo.getDenuncias()) {
                        denuncia.getUsuario().setNumeroDenuncias((long) denuncia.getUsuario().getDenuncias().size());
                    }
                }
            }
            return new ResponseEntity<>(autoSemiNuevos,HttpStatus.OK);
        }catch (Exception e ){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(value = "/reported")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> validate(@RequestParam("id") Long idAuto){
        try{
            autoSemiNuevoService.setRevisado(true,idAuto);
            return ResponseService.genSuccess(null);
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/reported")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> remove(@RequestParam("id") Long idAuto){
        try{
            autoSemiNuevoService.borrarAuto(idAuto);
            return ResponseService.genSuccess(null);
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    SolicitudRemocionService solicitudRemocionService;

    @GetMapping("/solicitudRemocion")
    @ResponseBody
    public ResponseEntity<Object> getSolicitudesRemocion() {
        try{
            return ResponseService.genSuccess(solicitudRemocionService.getAll());
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/solicitudRemocion")
    @Transactional
    @ResponseBody
    public ResponseEntity<Object> validateSolicitudesRemocion(@RequestBody SolicitudRemocionAuto temp) {
        try{
            if(temp.getId()==null){
                return ResponseService.genError("no se mando el id",HttpStatus.BAD_REQUEST);
            }
            SolicitudRemocionAuto solicitudRemocionAuto = solicitudRemocionService.getById(temp.getId());
            if(solicitudRemocionAuto==null){
                return ResponseService.genError("no se encontro la solicitud",HttpStatus.NOT_FOUND);
            }
            if(temp.getAccepted()){
                solicitudRemocionAuto.getAutoSemiNuevo().setEnabled(false);
            }

            solicitudRemocionAuto.setAccepted(temp.getAccepted());
            solicitudRemocionService.save(solicitudRemocionAuto);
            return ResponseService.genSuccess(null);
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
