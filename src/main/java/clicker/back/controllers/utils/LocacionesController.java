package clicker.back.controllers.utils;

import clicker.back.Setup;
import clicker.back.utils.services.LocacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/locaciones")
@CrossOrigin(origins = {Setup.local/*,Setup.route*/}, allowedHeaders = "*")
public class LocacionesController {
    @Autowired
    LocacionesService locacionesService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<Object> getAll(){
        try{
            return new ResponseEntity<>(locacionesService.findAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
