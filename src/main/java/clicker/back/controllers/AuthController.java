package clicker.back.controllers;

import clicker.back.Setup;
import clicker.back.entities.Users;
import clicker.back.entities.Usuario;
import clicker.back.services.CryptoService;
import clicker.back.services.EmailService;
import clicker.back.services.UsersService;
import clicker.back.services.UsuariosService;
import com.google.gson.*;
import com.sendgrid.Response;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.transaction.Transactional;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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
        if(usuario.getCorreo()==null || usuario.getPassword()==null)return new ResponseEntity<>("no se envio el email", HttpStatus.BAD_REQUEST);
        Users users = usersService.getByEmail(usuario.getCorreo());
        if(users ==null){
            Usuario temp = usuariosService.getById(usuario.getCorreo());
            if(temp==null){
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
                usuario.setNombre(null);
                usuario.setNumTelefono(null);
                try{
                    usuariosService.save(usuario);

                    String secret = cryptoService.encrypt3(usuario.getCorreo());
                    Response response =emailService.sendSimpleMessage(usuario.getCorreo(),"clicker@gmail.com","buenas tardes mi estimadisimo" +
                            "Le invitamos a confirmar su correo <b><font face=\"sans-serif\">" +
                            "<a href=\"https://prieto-family.com/validation/"+ secret +"\" " +
                            "style=\"color:#00bfff\" target=\"_blank\" >aqui</a></font></b>");
                    if (response.getStatusCode()==202){
                        Map<String,String> jsonElement = new HashMap<String,String>();
                        jsonElement.put("secret",secret);
                        return new ResponseEntity<>(jsonElement, HttpStatus.OK);
                    }else{
                        return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }catch (Exception e ){
                    e.printStackTrace();
                    return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>("se encontro un usuario con ese correo", HttpStatus.BAD_REQUEST);

    }

    @PostMapping(value = "/login")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> login(@RequestBody Usuario usuario)  {
        if(usuario.getCorreo()==null || usuario.getPassword()==null)return new ResponseEntity<>("no se envio credenciales", HttpStatus.BAD_REQUEST);
        Users users = usersService.login(usuario.getCorreo(),usuario.getPassword());
        if(users ==null){
            Usuario user = usuariosService.login(usuario.getCorreo(),usuario.getPassword());
            if(user==null){
                return new ResponseEntity<>("no se encontro el usuario", HttpStatus.BAD_REQUEST);
            }else{
                try{
                    String secret = cryptoService.encrypt3(usuario.getCorreo());
                    user.setSecret(secret);
                    return new ResponseEntity<>(user, HttpStatus.OK);
                }catch (Exception e){
                    return new ResponseEntity<>("fallo la encriptacion",HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }


}
