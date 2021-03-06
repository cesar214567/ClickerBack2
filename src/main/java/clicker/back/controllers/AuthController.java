package clicker.back.controllers;

import clicker.back.Setup;
import clicker.back.entities.Users;
import clicker.back.entities.Usuario;
import clicker.back.services.CryptoService;
import clicker.back.services.EmailService;
import clicker.back.services.UsersService;
import clicker.back.services.UsuariosService;
import clicker.back.utils.errors.ResponseService;
import com.sendgrid.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth")
@CrossOrigin(origins = {Setup.local/*,Setup.route*/}, allowedHeaders = "*")
public class AuthController {
    @Autowired
    UsuariosService usuariosService;

    @Autowired
    UsersService usersService;

    @Autowired
    CryptoService cryptoService;

    @Autowired
    EmailService emailService;

    @PostMapping(value = "/register")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> register(@RequestBody Usuario usuario) {
        if(usuario.getCorreo()==null || usuario.getPassword()==null){
            return ResponseService.genError("no se envio el email o contrasena",HttpStatus.BAD_REQUEST);
        }
        if (usuario.getNumDocumento()==null){
            return ResponseService.genError("no se envio el numero de documento",HttpStatus.BAD_REQUEST);
        }
        Users users = usersService.getByEmail(usuario.getCorreo());
        if(users ==null){
            Usuario temp = usuariosService.getByCorreo(usuario.getCorreo());
            if(temp==null){
                if(usuariosService.existByNumDocumento(usuario.getNumDocumento())){
                    return ResponseService.genError("un usuario con el mismo documento ya ha sido registrado",HttpStatus.FOUND);
                }
                if(usuario.getRol().equals("REMAX")) {
                    usuario.getForm().setUsuario(usuario);
                    usuario.setCantidadCarrosAno(20);
                }else {
                    usuario.setForm(null);
                    usuario.setCantidadCarrosAno(3);
                }
                usuario.setBalance((float) 0);
                usuario.setValidated(false);
                usuario.setEnabled(true);
                try{
                    usuariosService.save(usuario);
                    String secret = cryptoService.encrypt3(usuario.getCorreo());
                    System.out.println(secret);
                    Response response = emailService.sendTemplateMessage(usuario.getCorreo(),"Validacion de correo",secret,true);
                    //Response response = emailService.sendSimpleMessage(usuario.getCorreo(), "clicker@gmail.com", String.format("<head> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" /> <style> * { padding: 0; margin: 0; box-sizing: border-box; font-family: Arial, Helvetica, sans-serif; } body { background-color: #29abe2; } .main-container { padding: 0 5vw; } .container { display: flex; flex-direction: column; justify-content: center; align-items: center; width: 100%%; } .container-top { background-color: rgb(83, 169, 221); display: flex; flex-direction: column; justify-content: flex-start; align-items: center; margin-top: 50px; margin-bottom: 20px; } .container-bottom { display: flex; flex-direction: column; justify-content: flex-end; align-items: center; text-align: center; font-size: 10px; margin: 20px 0; text-align: justify; } .container-center { padding: 25px 85px; border-radius: 35px; display: flex; flex-direction: column; justify-content: center; align-items: center; background-color: white; box-shadow: 1px 1px 1px gray; text-align: justify; } .container-center p { margin: 10px 0; } .social-media { display: flex; justify-content: center; align-items: center; } .social-media p { margin: 5px 10px; } h2 { margin-bottom: 10px; } button { margin: 20px 20px; width: 250px; border: none; background-color: rgb(83, 169, 221); color: white; border-radius: 60px; padding: 10px 20px; font-size: 18px; } a { text-decoration: none; color: white; font-weight: 200; opacity: 0.9; } a:link { color: white; } .divider { width: 100%%; border-top: 1px solid gray; margin: 15px 0; filter: brightness(1.8); } h4 { margin-bottom: 10px; } @media screen and (max-width: 480px) { .container-center { width: 95vw; padding: 25px 20px; } .container-center p { font-size: 14px; } button { width: 90%%; } } @media screen and (min-width: 726px) { .container-center { max-width: 700px; } .container-bottom { max-width: 700px; font-size: 12px; } } </style> </head> <body> <div class=\"main-container\"> <div class=\"container\"> <div class=\"container-top\"> <img alt=\"Clicker Logo\" /> </div> <div class=\"container-center\"> <h2>Verifica tu cuenta</h2> <p> Haz click en el bot??n de abajo para confirmar que ??sta es la direcci??n correcta del correo que usar??s para acceder a tu cuenta. </p> <button><a href=\"https://prieto-family.com/validation/%s\" target=\"_blank\">Verificar mi cuenta</a></button> <p>Puedes actualizar los datos de tu cuenta en cualquier momento.</p> <p>Gracias.</p> <div class=\"divider\"></div> <h4>Equipo Clicker</h4> <div class=\"social-media\"> <p>Facebook</p> <p>Instagram</p> </div> </div> <div class=\"container-bottom\"> Este correo se envi?? en nombre de Startech Ventures SAC. Este correo electr??nico y cualquier archivo transmitido con ??l son confidenciales y pueden tener privilegios legales y est??n destinados ??nicamente para el uso de la persona o entidad a la que se dirigen. Si ha recibido este correo electr??nico por error, por favor notifique al remitente. </div> </div> </div> </body>", secret));
                        //Response response =emailService.sendSimpleMessage(usuario.getCorreo(),"clicker@gmail.com","buenas tardes mi estimadisimo" +
                    //        "Le invitamos a confirmar su correo https://prieto-family.com/validation/"+ secret);
                    System.out.println(response.getStatusCode());
                    if (response.getStatusCode()==202){
                        Map<String,String> jsonElement = new HashMap<String,String>();
                        jsonElement.put("secret",secret);
                        return ResponseService.genSuccess(jsonElement);
                    }else{
                        System.out.println(response.getBody());
                        return ResponseService.genError("fallo interno del servidor ",HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }catch (Exception e ){
                    e.printStackTrace();
                    return ResponseService.genError("fallo externo del servidor",HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return ResponseService.genError("se encontro un usuario con ese correo",HttpStatus.FOUND);
    }

    @PostMapping(value = "/login")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> login(@RequestBody Usuario usuario)  {
        if(usuario.getCorreo()==null || usuario.getPassword()==null){
            return ResponseService.genError("no se envio credenciales",HttpStatus.BAD_REQUEST);
        }
        Users users = usersService.login(usuario.getCorreo(),usuario.getPassword());
        if(users ==null){
            Usuario user = usuariosService.login(usuario.getCorreo(),usuario.getPassword());
            if(user==null){
                return ResponseService.genError("no se encontro el usuario",HttpStatus.BAD_REQUEST);
            }else{
                try{
                    String secret = cryptoService.encrypt3(usuario.getCorreo());
                    user.setSecret(secret);
                    return ResponseService.genSuccess(user);
                }catch (Exception e){
                    return ResponseService.genError("fallo la encriptacion",HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }else {
            return ResponseService.genSuccess(users);
        }
    }


}
