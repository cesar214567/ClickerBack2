package clicker.back.controllers;

import clicker.back.Setup;
import clicker.back.entities.AutoPatrocinado;
import clicker.back.entities.AutoSemiNuevo;
import clicker.back.services.AutoPatrocinadoService;
import clicker.back.services.AutoSemiNuevoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/sponsor")
@CrossOrigin(origins = {Setup.local/*,Setup.route*/}, allowedHeaders = "*")
public class SponsorsController {
    @Autowired
    AutoSemiNuevoService autoSemiNuevoService;

    @Autowired
    AutoPatrocinadoService autoPatrocinadoService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<Object> sponsor(@RequestBody AutoPatrocinado autoPatrocinado){
        if(autoPatrocinado.getAutoSemiNuevo()==null || autoPatrocinado.getAutoSemiNuevo().getId()==null)
            return new ResponseEntity<>("no se envio el auto a patrocinar", HttpStatus.BAD_REQUEST);
        if(autoPatrocinado.getLevel()==null)return new ResponseEntity<>("no se envio el nivel",HttpStatus.BAD_REQUEST);
        autoPatrocinado.setAutoSemiNuevo(autoSemiNuevoService.getById(autoPatrocinado.getAutoSemiNuevo().getId()));
        if(autoPatrocinado.getAutoSemiNuevo()==null)return new ResponseEntity<>("no se encontro un auto con ese id",HttpStatus.BAD_REQUEST);
        if(autoPatrocinadoService.findByAutoSemiNuevo(autoPatrocinado.getAutoSemiNuevo())!=null)
            return new ResponseEntity<>("ya se patrocino este auto",HttpStatus.BAD_REQUEST);
        try{
            return new ResponseEntity<>(autoPatrocinadoService.save(autoPatrocinado),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<Object> getAll(){
        try{
            return new ResponseEntity<>(autoPatrocinadoService.findAll(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
