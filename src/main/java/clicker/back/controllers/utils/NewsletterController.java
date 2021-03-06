package clicker.back.controllers.utils;


import clicker.back.Setup;
import clicker.back.utils.entities.Newsletter;
import clicker.back.utils.services.NewsletterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping(value = "/newsletter")
@CrossOrigin(origins = {Setup.local/*,Setup.route*/}, allowedHeaders = "*")
public class NewsletterController {

    @Autowired
    NewsletterService newsletterService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<Object> getAll(){
        try{
            return new ResponseEntity<>(newsletterService.findAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> save(@RequestBody Newsletter newsletter){
        try{
            newsletterService.save(newsletter);
            return new ResponseEntity<>(null,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //TODO
}
