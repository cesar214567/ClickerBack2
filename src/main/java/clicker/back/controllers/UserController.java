package clicker.back.controllers;

import clicker.back.Setup;
import clicker.back.entities.*;
import clicker.back.services.*;
import clicker.back.utils.errors.ResponseService;
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
            Users temp = userService.getById(users.getEmail());
            if(temp==null){
                return new ResponseEntity<>(userService.save(users), HttpStatus.OK);
            }
        }
        return ResponseService.genError("se encontro un user con ese correo",HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/id")
    @ResponseBody
    public ResponseEntity<Object> getById(@RequestParam("id") String id ) {
        System.out.println(id);
        Users user = userService.getById(id);
        if (user == null)
            return new ResponseEntity<>(usuariosService.getByCorreo(id), HttpStatus.OK);
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
            return ResponseService.genSuccess("eliminado");
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
}
