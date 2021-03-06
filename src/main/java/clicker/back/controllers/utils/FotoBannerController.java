package clicker.back.controllers.utils;

import clicker.back.Setup;
import clicker.back.utils.entities.FotoBanner;
import clicker.back.utils.services.FotoBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/fotoBanner")
@CrossOrigin(origins = {Setup.local/*,Setup.route*/}, allowedHeaders = "*")
public class FotoBannerController {
    @Autowired
    FotoBannerService fotoBannerService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<Object> getFotoBanner(){
        try{
            return new ResponseEntity<>(fotoBannerService.findAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Object> postFoto(@RequestBody FotoBanner fotoBanner){
        List<FotoBanner> foto = fotoBannerService.findAll();
        for (FotoBanner banner : foto) {
            fotoBannerService.delete(banner);
        }
        try{
            return new ResponseEntity<>(fotoBannerService.save(fotoBanner),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("FALLO",HttpStatus.BAD_REQUEST);
        }
    }

}
