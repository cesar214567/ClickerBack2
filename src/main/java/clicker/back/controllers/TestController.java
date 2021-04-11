package clicker.back.controllers;

import clicker.back.Setup;
import clicker.back.entities.VentaSemiNuevo;
import clicker.back.services.VentaSemiNuevoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/test")
@CrossOrigin(origins = {Setup.local/*,Setup.route*/}, allowedHeaders = "*")
public class TestController {

    @Autowired
    VentaSemiNuevoService ventaSemiNuevoService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<Object> getAllVentas(){
        try{
            return new ResponseEntity<>(ventaSemiNuevoService.getAll(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
