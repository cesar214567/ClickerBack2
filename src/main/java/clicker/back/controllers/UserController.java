package clicker.back.controllers;

import clicker.back.Setup;
import clicker.back.entities.*;
import clicker.back.services.*;
import clicker.back.utils.errors.ResponseService;
import com.sendgrid.Response;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin(origins = {Setup.local/*,Setup.route*/}, allowedHeaders = "*")
public class UserController {

    @Autowired
    UsersService userService;

    @Autowired
    UsuariosService usuariosService;

    @Autowired
    VentaSemiNuevoService ventaSemiNuevoService;

    @Autowired
    SolicitudesRetiroService solicitudesRetiroService;

    @Autowired
    AutoSemiNuevoService autoSemiNuevoService;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Object> create(@RequestBody Users users){
        if(users.getEmail()==null || users.getPassword()==null){
            return ResponseService.genError("no se envio el email",HttpStatus.BAD_REQUEST);
        }
        Usuario usuario= usuariosService.getByCorreo(users.getEmail());
        if(usuario==null){
            Users temp = userService.getByEmail(users.getEmail());
            if(temp==null){
                return new ResponseEntity<>(userService.save(users), HttpStatus.OK);
            }
        }
        return ResponseService.genError("se encontro un user con ese correo",HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/id")
    @ResponseBody
    public ResponseEntity<Object> getById(@RequestParam("id") String id ) {
        Users user = userService.getByEmail(id);
        if (user == null){
            return new ResponseEntity<>(usuariosService.getByCorreo(id), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<Object> getAll() {
        return new ResponseEntity<>(usuariosService.getAll(),HttpStatus.OK);
    }

    @PostMapping(value = "/remax")
    @ResponseBody
    public ResponseEntity<Object> postFormRemax(@RequestBody Form form){
        if(form.getUsuario()==null || form.getUsuario().getCorreo()==null){
            return ResponseService.genError("no se envio el usuario",HttpStatus.BAD_REQUEST);
        }
        form.setUsuario(usuariosService.getByCorreo(form.getUsuario().getCorreo()));
        if(form.getUsuario()==null){
            return ResponseService.genError("no se encontro el usuario",HttpStatus.NOT_FOUND);
        }
        if(!form.getUsuario().getRol().equals("REMAX")){
            return ResponseService.genError("el usuario no es un tipo REMAX, no se le debe hacer un forms",HttpStatus.BAD_REQUEST);
        }
        form.getUsuario().setForm(form);
        return new ResponseEntity<>(usuariosService.save(form.getUsuario()),HttpStatus.OK);
    }

    @GetMapping(value = "/number")
    @ResponseBody
    public ResponseEntity<Object> getNumberUsers(){
        try{
            return new ResponseEntity<>(usuariosService.countUsers(),HttpStatus.OK);
        }catch (Exception e ){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    CryptoService cryptoService;

    @PutMapping(value = "/validate")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> validar(@RequestBody Usuario usuario){
        String correo;
        try{
            correo = cryptoService.decrypt3(usuario.getCorreo());
        }catch (Exception e){
            return ResponseService.genError("no se pudo desencriptar el id",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Usuario real = usuariosService.getByCorreo(correo);
        if(real == null)return ResponseService.genError("usuario no se encontro",HttpStatus.NOT_FOUND);
        real.setValidated(true);
        try{
            usuariosService.save(real);
            return ResponseService.genSuccess(null);
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/registroBalance")
    @ResponseBody
    public ResponseEntity<Object> getRegistroBalance(@RequestParam("id")Long id){
        List<VentaSemiNuevo> ventaSemiNuevos =  ventaSemiNuevoService.getByIdAuto(autoSemiNuevoService.getAllAutosVendidosByUsuario(id));
        List<SolicitudesRetiro> solicitudesRetiros = solicitudesRetiroService.getAllAceptadosByUsuario(id);
        List<VentaSemiNuevo> ventaSemiNuevos1 = ventaSemiNuevoService.getVentasByUsuario(id);
        JSONArray jsonArray = new JSONArray();
        for (VentaSemiNuevo ventaSemiNuevo : ventaSemiNuevos) {
            if(ventaSemiNuevo.getGananciaUsuario()>0){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("monto",ventaSemiNuevo.getGananciaUsuario());
                jsonObject.put("fecha",ventaSemiNuevo.getFecha().toString());
                jsonObject.put("descripcion","Ingreso de dinero por la venta de su auto: "+ventaSemiNuevo.getAutoSemiNuevo().getMarca()+" "+ventaSemiNuevo.getAutoSemiNuevo().getModelo()+" placa: "+ventaSemiNuevo.getAutoSemiNuevo().getPlaca());
                jsonArray.appendElement(jsonObject);
            }
        }
        for (SolicitudesRetiro solicitudesRetiro : solicitudesRetiros) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("monto",solicitudesRetiro.getMonto()*(-1));
            jsonObject.put("fecha",solicitudesRetiro.getDate().toString());
            jsonObject.put("descripcion","Solicitud de retiro aceptada por el monto de: "+solicitudesRetiro.getMonto()+" soles ");
            jsonArray.appendElement(jsonObject);
        }
        for (VentaSemiNuevo ventaSemiNuevo : ventaSemiNuevos1) {
            if(ventaSemiNuevo.getGananciaUsuario()>0){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("monto",ventaSemiNuevo.getGananciaUsuario());
                jsonObject.put("fecha",ventaSemiNuevo.getFecha().toString());
                jsonObject.put("descripcion","Ingreso de dinero por la reventa del auto: "+ventaSemiNuevo.getAutoSemiNuevo().getMarca()+" "+ventaSemiNuevo.getAutoSemiNuevo().getModelo());
                jsonArray.appendElement(jsonObject);
            }
        }
        try{
            return new ResponseEntity<>(jsonArray,HttpStatus.OK);

        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Autowired
    InteresadoReventaService interesadoReventaService;

    @GetMapping(value = "/interesadoVenta")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getIntVenta(@RequestParam("id")Long userId  ){
        try{
            if(userId==null){
                return ResponseService.genError("no mando correo",HttpStatus.BAD_REQUEST);
            }
            if(!usuariosService.existById(userId)){
                return ResponseService.genError("no existe el usuario",HttpStatus.BAD_REQUEST);
            }
            List<InteresadoReventa> interesadoReventas= interesadoReventaService.getAllByUsuario(userId);
            return new ResponseEntity<>(interesadoReventas,HttpStatus.OK);
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "interesadoVenta")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> deleteInteresadoReventa(@RequestParam("id") Long id){
        if(id==null){
            return ResponseService.genError("no se mando el id de la solicitud",HttpStatus.BAD_REQUEST);
        }
        InteresadoReventa interesadoReventa = interesadoReventaService.getById(id);
        if(interesadoReventa==null){
            return ResponseService.genError("no se encontro solicitud",HttpStatus.BAD_REQUEST);
        }
        interesadoReventa.getUsuario().getInteresadoReventas().removeIf(t-> t.getId().equals(id));

        try{
            usuariosService.save(interesadoReventa.getUsuario());
            interesadoReventaService.delete(interesadoReventa);
            return ResponseService.genSuccess(null);
        }catch (Exception e ){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/ventas")
    @ResponseBody
    public ResponseEntity<Object> getVentasByUsuario(@RequestParam("id")Long id){
        if(id!=null){
            try{
                return new ResponseEntity<>(ventaSemiNuevoService.getVentasByUsuario(id),HttpStatus.OK);
            }catch (Exception e){
                return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            return ResponseService.genError("no se encontro usuario",HttpStatus.BAD_REQUEST);
        }
    }

    @Autowired
    EmailService emailService;

    @PutMapping
    @ResponseBody
    public ResponseEntity<Object> updateUsuario(@RequestBody Usuario usuario){
        if(usuario.getId()==null)return ResponseService.genError("no se mando el id",HttpStatus.BAD_REQUEST);
        Usuario temp = usuariosService.getById(usuario.getId());
        if(temp == null)return ResponseService.genError("no se encontro al usuario",HttpStatus.NOT_FOUND);
        try{
            temp.updateInfo(usuario);
            if(usuario.getCorreo()!=null && !temp.getCorreo().equals(usuario.getCorreo())){
                temp.setValidated(false);
                if(usuariosService.existByCorreo(usuario.getCorreo())){
                    return ResponseService.genError("ya se esta usando el correo",HttpStatus.CONFLICT);
                }
                temp.setCorreo(usuario.getCorreo());
                String secret = cryptoService.encrypt3(usuario.getCorreo());
                Response response = emailService.sendTemplateMessage(usuario.getCorreo(),"validar correo",secret,true);
                //Response response = emailService.sendSimpleMessage(usuario.getCorreo(), "clicker@gmail.com", String.format("<head> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" /> <style> * { padding: 0; margin: 0; box-sizing: border-box; font-family: Arial, Helvetica, sans-serif; } body { background-color: #29abe2; } .main-container { padding: 0 5vw; } .container { display: flex; flex-direction: column; justify-content: center; align-items: center; width: 100%%; } .container-top { background-color: rgb(83, 169, 221); display: flex; flex-direction: column; justify-content: flex-start; align-items: center; margin-top: 50px; margin-bottom: 20px; } .container-bottom { display: flex; flex-direction: column; justify-content: flex-end; align-items: center; text-align: center; font-size: 10px; margin: 20px 0; text-align: justify; } .container-center { padding: 25px 85px; border-radius: 35px; display: flex; flex-direction: column; justify-content: center; align-items: center; background-color: white; box-shadow: 1px 1px 1px gray; text-align: justify; } .container-center p { margin: 10px 0; } .social-media { display: flex; justify-content: center; align-items: center; } .social-media p { margin: 5px 10px; } h2 { margin-bottom: 10px; } button { margin: 20px 20px; width: 250px; border: none; background-color: rgb(83, 169, 221); color: white; border-radius: 60px; padding: 10px 20px; font-size: 18px; } a { text-decoration: none; color: white; font-weight: 200; opacity: 0.9; } a:link { color: white; } .divider { width: 100%%; border-top: 1px solid gray; margin: 15px 0; filter: brightness(1.8); } h4 { margin-bottom: 10px; } @media screen and (max-width: 480px) { .container-center { width: 95vw; padding: 25px 20px; } .container-center p { font-size: 14px; } button { width: 90%%; } } @media screen and (min-width: 726px) { .container-center { max-width: 700px; } .container-bottom { max-width: 700px; font-size: 12px; } } </style> </head> <body> <div class=\"main-container\"> <div class=\"container\"> <div class=\"container-top\"> <img alt=\"Clicker Logo\" /> </div> <div class=\"container-center\"> <h2>Verifica tu cuenta</h2> <p> Haz click en el botón de abajo para confirmar que ésta es la dirección correcta del correo que usarás para acceder a tu cuenta. </p> <button><a href=\"https://prieto-family.com/validation/%s\" target=\"_blank\">Verificar mi cuenta</a></button> <p>Puedes actualizar los datos de tu cuenta en cualquier momento.</p> <p>Gracias.</p> <div class=\"divider\"></div> <h4>Equipo Clicker</h4> <div class=\"social-media\"> <p>Facebook</p> <p>Instagram</p> </div> </div> <div class=\"container-bottom\"> Este correo se envió en nombre de Startech Ventures SAC. Este correo electrónico y cualquier archivo transmitido con él son confidenciales y pueden tener privilegios legales y están destinados únicamente para el uso de la persona o entidad a la que se dirigen. Si ha recibido este correo electrónico por error, por favor notifique al remitente. </div> </div> </div> </body>", secret));
            }
            usuariosService.save(temp);
            return ResponseService.genSuccess(null);
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/revalidate/{id}")
    @ResponseBody
    public ResponseEntity<Object> reValidate(@PathVariable("id") Long id){
        try{
            Usuario usuario =usuariosService.getById(id);
            if(!usuario.getValidated()){
                String secret = cryptoService.encrypt3(usuario.getCorreo());
                Response response = emailService.sendTemplateMessage(usuario.getCorreo(), "Validacion de correo",secret,true);
                return ResponseService.genSuccess(null);
            }else{
                return ResponseService.genError("ya esta validado el usuario",HttpStatus.CONFLICT);
            }
        }catch (Exception e){
            return ResponseService.genError("fallo en el servidor",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping("/recover")
    @ResponseBody
    public ResponseEntity<Object> recoverPassword(@RequestBody Usuario usuario){
        try{
            Usuario temp = usuariosService.getByCorreo(usuario.getCorreo());
            if(temp==null){
                return ResponseService.genError("no se encontro el usuario",HttpStatus.NOT_FOUND);
            }else{
                String correo = temp.getCorreo();
                String secret = cryptoService.encrypt3(correo);
                emailService.sendTemplateMessage(correo,"Recuperacion de contrasena",secret,false);
                return ResponseService.genSuccess(null);
            }
        }catch (Exception e){
            return ResponseService.genError("fallo del servidor",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/recover")
    @ResponseBody
    public ResponseEntity<Object> updatePassword(@RequestBody Usuario usuario){
        try{
            String correo = cryptoService.decrypt3(usuario.getCorreo());
            Usuario usuarioTemp = usuariosService.getByCorreo(correo);
            if(usuarioTemp!=null){
                usuarioTemp.setPassword(usuario.getPassword());
                usuariosService.save(usuarioTemp );
                return ResponseService.genSuccess(null);
            }else{
                return ResponseService.genError("no se encontro el usuario",HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return ResponseService.genError("fallo del servidor",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
