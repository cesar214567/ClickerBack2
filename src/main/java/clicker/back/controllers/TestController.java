package clicker.back.controllers;

import clicker.back.Setup;
import clicker.back.services.CryptoService;
import clicker.back.services.EmailService;
import clicker.back.services.VentaSemiNuevoService;
import clicker.back.utils.errors.ResponseService;
import com.sendgrid.Response;
import net.minidev.json.JSONObject;
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

    @Autowired
    CryptoService cryptoService;
    @Autowired
    EmailService emailService;

    @GetMapping("/ventas")
    @ResponseBody
    public ResponseEntity<Object> getAllVentas(){
        try{
            return new ResponseEntity<>(ventaSemiNuevoService.getAll(),HttpStatus.OK);
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/mail")
    @ResponseBody
    public ResponseEntity<Object> testMail(){
        try{
            String secret = cryptoService.encrypt3("camgcamg11@gmail.com");
            Response response = emailService.sendTemplateMessage("camgcamg11@gmail.com","template",secret);
            if (response.getStatusCode()==202) {
                JSONObject jsonElement = new JSONObject();
                jsonElement.put("secret", secret);
                return ResponseService.genSuccess(jsonElement);
            }else{
                return ResponseService.genError(response.getBody(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
