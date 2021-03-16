package clicker.back.controllers;

import clicker.back.Setup;
import clicker.back.antiguo.Solicitudes;
import clicker.back.entities.*;
import clicker.back.services.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sendgrid.Response;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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
        if(users.getEmail()==null || users.getPassword()==null)return new ResponseEntity<>("no se envio el email", HttpStatus.BAD_REQUEST);
        Usuario usuario= usuariosService.getById(users.getEmail());
        if(usuario==null){
            Users temp = userService.getById(users.getEmail());
            if(temp==null){
                return new ResponseEntity<>(userService.save(users), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("se encontro un user con ese correo", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/id")
    @ResponseBody
    public ResponseEntity<Object> getById(@RequestParam("id") String id ) {
        System.out.println(id);
        Users user = userService.getById(id);
        if (user == null)
            return new ResponseEntity<>(usuariosService.getById(id), HttpStatus.OK);
        else
            return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<Object> getAll() {
        return new ResponseEntity<>(usuariosService.getAll(),HttpStatus.OK);
    }

    @PostMapping(value = "/remax")
    @ResponseBody
    public ResponseEntity<Object> postFormRemax(@RequestBody Form form){
        if(form.getUsuario()==null || form.getUsuario().getCorreo()==null)return new ResponseEntity<>("no se envio el usuario",HttpStatus.BAD_REQUEST);
        form.setUsuario(usuariosService.getById(form.getUsuario().getCorreo()));
        if(form.getUsuario()==null) return new ResponseEntity<>("no se encontro el usuario",HttpStatus.BAD_REQUEST);
        if(!form.getUsuario().getRol().equals("REMAX"))return new ResponseEntity<>("el usuario no es un tipo REMAX, no se le debe hacer un forms",HttpStatus.BAD_REQUEST);
        form.getUsuario().setForm(form);
        return new ResponseEntity<>(usuariosService.save(form.getUsuario()),HttpStatus.OK);
    }

    @GetMapping(value = "/number")
    @ResponseBody
    public ResponseEntity<Object> getNumberUsers(){
        try{
            return new ResponseEntity<>(usuariosService.countUsers(),HttpStatus.OK);
        }catch (Exception e ){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Autowired
    CryptoService cryptoService;

    @PutMapping(value = "/validate")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> validar(@RequestBody Usuario usuario){
        if(usuario.getNombre()==null || usuario.getNumTelefono()==null){
            return new ResponseEntity<>("no envio los datos",HttpStatus.BAD_REQUEST);
        }
        String correo;
        System.out.println(usuario.getCorreo().length());;
        try{
            correo = cryptoService.decrypt3(usuario.getCorreo());
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("no se pudo desencriptar el id",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Usuario real = usuariosService.getById(correo);
        if(real.getRol().equals("USUARIO")){
            real.setNombre(usuario.getNombre());
            real.setNumTelefono(usuario.getNumTelefono());
        }else{
            real.setNombre(usuario.getNombre());
            real.setNumTelefono(usuario.getNumTelefono());
        }
        real.setValidated(true);
        try{
            usuariosService.save(real);
            return new ResponseEntity<>(null,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/registroBalance")
    @ResponseBody
    public ResponseEntity<Object> getRegistroBalance(@RequestParam("id")String correo){
        List<VentaSemiNuevo> ventaSemiNuevos =  ventaSemiNuevoService.getByIdAuto(autoSemiNuevoService.getAllAutosVendidosByUsuario(correo));
        List<SolicitudesRetiro> solicitudesRetiros = solicitudesRetiroService.getAllAceptadosByUsuario(correo);
        List<VentaSemiNuevo> ventaSemiNuevos1 = ventaSemiNuevoService.getVentasByUsuario(correo);
        JSONArray jsonArray = new JSONArray();
        for (VentaSemiNuevo ventaSemiNuevo : ventaSemiNuevos) {
            if(ventaSemiNuevo.getGananciaUsuario()>0){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("monto",ventaSemiNuevo.getGananciaUsuario());
                jsonObject.put("fecha",ventaSemiNuevo.getFecha().toString());
                jsonObject.put("descripcion","el usuario vendio su auto: "+ventaSemiNuevo.getAutoSemiNuevo().getMarca()+" "+ventaSemiNuevo.getAutoSemiNuevo().getModelo());
                jsonArray.appendElement(jsonObject);
            }
        }
        for (SolicitudesRetiro solicitudesRetiro : solicitudesRetiros) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("monto",solicitudesRetiro.getMonto()*(-1));
            jsonObject.put("fecha",solicitudesRetiro.getDate().toString());
            jsonObject.put("descripcion","el usuario retiro "+solicitudesRetiro.getMonto()+" soles ");
            jsonArray.appendElement(jsonObject);
        }
        for (VentaSemiNuevo ventaSemiNuevo : ventaSemiNuevos1) {
            if(ventaSemiNuevo.getGananciaUsuario()>0){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("monto",ventaSemiNuevo.getGananciaUsuario());
                jsonObject.put("fecha",ventaSemiNuevo.getFecha().toString());
                jsonObject.put("descripcion","el usuario revendio un auto: "+ventaSemiNuevo.getAutoSemiNuevo().getMarca()+" "+ventaSemiNuevo.getAutoSemiNuevo().getModelo());
                jsonArray.appendElement(jsonObject);
            }
        }
        try{
            return new ResponseEntity<>(jsonArray,HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
