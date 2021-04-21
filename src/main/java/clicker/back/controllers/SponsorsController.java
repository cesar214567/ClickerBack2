package clicker.back.controllers;

import clicker.back.Setup;
import clicker.back.entities.AutoPatrocinado;
import clicker.back.services.AutoPatrocinadoService;
import clicker.back.services.AutoSemiNuevoService;
import clicker.back.utils.errors.ResponseService;
import com.sendgrid.Response;
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
        if(autoPatrocinado.getAutoSemiNuevo()==null || autoPatrocinado.getAutoSemiNuevo().getId()==null){
            return ResponseService.genError("no se envio el auto a patrocinar",HttpStatus.BAD_REQUEST);
        }
        autoPatrocinado.setAutoSemiNuevo(autoSemiNuevoService.getById(autoPatrocinado.getAutoSemiNuevo().getId()));
        if(autoPatrocinado.getAutoSemiNuevo()==null){
            return ResponseService.genError("no se encontro un auto con ese id",HttpStatus.BAD_REQUEST);
        }
        if(autoPatrocinadoService.findByAutoSemiNuevo(autoPatrocinado.getAutoSemiNuevo())!=null)
            return ResponseService.genError("ya se patrocino este auto",HttpStatus.BAD_REQUEST);
        try{
            return new ResponseEntity<>(autoPatrocinadoService.save(autoPatrocinado),HttpStatus.OK);
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<Object> getAll(){
        try{
            return new ResponseEntity<>(autoPatrocinadoService.findAll(),HttpStatus.OK);
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<Object> modify(@RequestBody AutoPatrocinado autoPatrocinado){
        if(autoPatrocinado.getId()==null){
            return ResponseService.genError("no se envio el id",HttpStatus.BAD_REQUEST);
        }
        AutoPatrocinado temp = autoPatrocinadoService.findById(autoPatrocinado.getId());
        if(temp==null)return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        temp.setLevel(autoPatrocinado.getLevel());
        try{
            autoPatrocinadoService.save(temp);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    @ResponseBody
    public ResponseEntity<Object> delete(@RequestParam("id")Long id){
        if(id==null){
            return  ResponseService.genError("no se envio el id",HttpStatus.BAD_REQUEST);
        }
        AutoPatrocinado autoPatrocinado = autoPatrocinadoService.findById(id);
        if(autoPatrocinado==null){
            return ResponseService.genError("no se encontro el patrocinio",HttpStatus.NOT_FOUND);
        }
        try{
            autoPatrocinadoService.delete(autoPatrocinado);
            return ResponseService.genSuccess(null);
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
