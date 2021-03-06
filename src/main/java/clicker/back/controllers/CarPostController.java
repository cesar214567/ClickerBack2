package clicker.back.controllers;


import clicker.back.Setup;
import clicker.back.controllers.beans.FiltrosBean;
import clicker.back.entities.*;
import clicker.back.services.*;
import clicker.back.utils.services.LocacionesService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping(value = "/post")
@CrossOrigin(origins = {Setup.local/*,Setup.route*/}, allowedHeaders = "*")
public class CarPostController {
    @Autowired
    UsuariosService usuariosService;

    @Autowired
    DenunciaService denunciaService;

    @Autowired
    AutoSemiNuevoService autoSemiNuevoService;

    @Autowired
    VentaSemiNuevoService ventaSemiNuevoService;

    @Autowired
    LocacionesService locacionesService;

    @PostMapping
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> post(@RequestBody AutoSemiNuevo autoSemiNuevo){
        if(autoSemiNuevo.getUsuario()==null || autoSemiNuevo.getUsuario().getCorreo()==null)
            return new ResponseEntity<>("no se envio un usuario",HttpStatus.BAD_REQUEST);
        List<AutoSemiNuevo> temp = autoSemiNuevoService.getByPlaca(autoSemiNuevo.getPlaca());
        if(autoSemiNuevo.getPlaca()==null) {
            return new ResponseEntity<>(" no se mando la placa", HttpStatus.BAD_REQUEST);
        }
        for (AutoSemiNuevo semiNuevo : temp) {
            if(!semiNuevo.getComprado()){
                return new ResponseEntity<>("este auto esta siendo vendido",HttpStatus.BAD_REQUEST);
            }
        }
        if(autoSemiNuevo.getLocaciones()==null || autoSemiNuevo.getLocaciones().getId()==null) {
            return new ResponseEntity<>("no se envio la locacion", HttpStatus.BAD_REQUEST);
        }
        autoSemiNuevo.setLocaciones(locacionesService.findById(autoSemiNuevo.getLocaciones().getId()));
        if(autoSemiNuevo.getLocaciones()==null){
            return new ResponseEntity<>("no se encontro la locacion",HttpStatus.BAD_REQUEST);
        }
        Usuario user = usuariosService.getById(autoSemiNuevo.getUsuario().getCorreo());
        if(user == null){
            return new ResponseEntity<>("no se encontro el usuario con ese id",  HttpStatus.BAD_REQUEST);
        }else{
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c2.setTime(new Date());
            int cont=0;
            List<AutoSemiNuevo> autos = user.getCarrosPosteados();
            for(AutoSemiNuevo auto:autos){
                c1.setTime(auto.getFechaPublicacion());
                if(c1.get(Calendar.YEAR)==c2.get(Calendar.YEAR))cont++;
            }
            if(cont>=user.getCantidadCarrosAno())return new ResponseEntity<>("ya agoto sus subidas anuales",  HttpStatus.LOCKED);
            else{
                autoSemiNuevo.setValidado(false);
                if(user.getRol().equals("PARTICULAR")){
                    autoSemiNuevo.setComisionEmpresa(40);
                    autoSemiNuevo.setComisionUsuario(40);
                    autoSemiNuevo.setComisionVendedor(20);
                }else{
                    autoSemiNuevo.setComisionEmpresa(40);
                    autoSemiNuevo.setComisionUsuario(40);
                    autoSemiNuevo.setComisionVendedor(20);
                }

            }
            autoSemiNuevo.setComprado(false);
            autoSemiNuevo.setValidado(false);
            autoSemiNuevo.setEnabled(true);
            autoSemiNuevo.setFechaPublicacion(new Date());
            autos.add(autoSemiNuevo);
            try{
                usuariosService.save(user);
                return new ResponseEntity<>(null, HttpStatus.OK);
            }catch (Exception e ){
                return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

    }

    @Autowired
    InteresadoCompraService interesadoCompraService;

    @PostMapping(value = "/interesadosCompra")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> intCompra(@RequestBody InteresadoCompra interesadoCompra) {
        if(interesadoCompra.getAutoSemiNuevo()==null || interesadoCompra.getAutoSemiNuevo().getId()==null) return new ResponseEntity<>("No se envio el auto por el cual esta interesado",HttpStatus.BAD_REQUEST);
        interesadoCompra.setAutoSemiNuevo(autoSemiNuevoService.getById(interesadoCompra.getAutoSemiNuevo().getId()));
        if(interesadoCompra.getAutoSemiNuevo() == null) return new ResponseEntity<>("no se encontro el auto",HttpStatus.NOT_FOUND);
        interesadoCompra.setId(null);
        interesadoCompra.setAutoSemiNuevo(interesadoCompra.getAutoSemiNuevo());
        try{
            interesadoCompraService.save(interesadoCompra);
            return new ResponseEntity<>("se realizo correctamente el post",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("se encontro algun error",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(autoSemiNuevoService.getById(id),HttpStatus.OK);
    }

    @Autowired
    InteresadoReventaService interesadoReventaService;

    @PostMapping(value = "/interesadosVenta")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> intVenta(@RequestBody InteresadoReventa interesadoReventa) {
        if(interesadoReventa.getAutoSemiNuevo()==null || interesadoReventa.getAutoSemiNuevo().getId()==null) return new ResponseEntity<>("No se envio el auto por el cual esta interesado",HttpStatus.BAD_REQUEST);
        interesadoReventa.setAutoSemiNuevo(autoSemiNuevoService.getById(interesadoReventa.getAutoSemiNuevo().getId()));
        if(interesadoReventa.getAutoSemiNuevo() == null) return new ResponseEntity<>("no se encontro el auto",HttpStatus.NOT_FOUND);
        if(interesadoReventa.getUsuario()==null || interesadoReventa.getUsuario().getCorreo()==null) return new ResponseEntity<>("No se envio el usuario interesado",HttpStatus.BAD_REQUEST);
        interesadoReventa.setUsuario(usuariosService.getById(interesadoReventa.getUsuario().getCorreo()));
        if(interesadoReventa.getUsuario() == null) return new ResponseEntity<>("no se encontro el usuario en la base de datos",HttpStatus.NOT_FOUND);
        interesadoReventa.setId(null);
        interesadoReventa.setUsuario(interesadoReventa.getUsuario());
        interesadoReventa.getUsuario().getInteresadoReventas().add(interesadoReventa);
        interesadoReventa.setAutoSemiNuevo(interesadoReventa.getAutoSemiNuevo());
        try{
            interesadoReventaService.save(interesadoReventa);
            return new ResponseEntity<>("se realizo correctamente el post",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("se encontro algun error",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/venta")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> ventaSemiNuevo(@RequestBody VentaSemiNuevo ventaSemiNuevo){
        //TODO balance total
        if(ventaSemiNuevo.getAutoSemiNuevo()==null || ventaSemiNuevo.getAutoSemiNuevo().getId()==null)return new ResponseEntity<>("no se mando el auto",HttpStatus.BAD_REQUEST);
        ventaSemiNuevo.setAutoSemiNuevo(autoSemiNuevoService.getById(ventaSemiNuevo.getAutoSemiNuevo().getId()));
        if(ventaSemiNuevo.getAutoSemiNuevo()==null)return new ResponseEntity<>("no se encontro el auto con ese id",HttpStatus.BAD_REQUEST);
        if(ventaSemiNuevo.getVendedor()==null)return new ResponseEntity<>("no se mando el vendedor",HttpStatus.BAD_REQUEST);
        ventaSemiNuevo.setVendedor(usuariosService.getById(ventaSemiNuevo.getVendedor().getCorreo()));
        if(ventaSemiNuevo.getVendedor()==null)return new ResponseEntity<>("no se encontro el vendedor con ese id",HttpStatus.BAD_REQUEST);
        ventaSemiNuevo.getAutoSemiNuevo().setComprado(true);
        long gananciaUsuario = ventaSemiNuevo.getAutoSemiNuevo().getPrecioVenta()*ventaSemiNuevo.getAutoSemiNuevo().getComisionVendedor()/100;
        ventaSemiNuevo.getVendedor().setBalance( (ventaSemiNuevo.getVendedor().getBalance()+gananciaUsuario));
        ventaSemiNuevo.getVendedor().getHistorialBalance().add(new RegistroBalance(new Date(),gananciaUsuario,ventaSemiNuevo.getVendedor(),
                "el usuario ha vendido un "+ventaSemiNuevo.getAutoSemiNuevo().getNombredeauto()));
        try{
            return new ResponseEntity<>(ventaSemiNuevoService.save(ventaSemiNuevo),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo del servidor",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/enabled")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getEnabled(){
        try{
            return new ResponseEntity<>(autoSemiNuevoService.getAllEnabled(true,true,false),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/enabled/{pageId}")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getEnabledPaginated(@PathVariable("pageId") Integer pageId){
        try{
            return new ResponseEntity<>(autoSemiNuevoService.getAllEnabled(true,true,false,PageRequest.of(pageId,8, Sort.by("fechaPublicacion").descending())),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/enabled/count")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getEnabledCount(){
        try{
            return new ResponseEntity<>(autoSemiNuevoService.getPages(true,true,false),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/validate/{id}")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> modify(@PathVariable("id")Long id ){
        AutoSemiNuevo autoSemiNuevo = autoSemiNuevoService.getById(id);
        if(autoSemiNuevo==null)return new ResponseEntity<>("no se encontro el post con ese id",HttpStatus.BAD_REQUEST);
        autoSemiNuevo.setValidado(true);
        try{
            return new ResponseEntity<>(autoSemiNuevoService.save(autoSemiNuevo),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/reported")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getAllReported() throws IOException {
        List<Long> ids = denunciaService.getIdsAutosDenunciados();
        List<AutoSemiNuevo> autoSemiNuevos = autoSemiNuevoService.getAllFromIdList(ids);
        if(autoSemiNuevos==null){
            return new ResponseEntity<>("BAD",HttpStatus.BAD_REQUEST);
        }
        for (AutoSemiNuevo autoSemiNuevo : autoSemiNuevos) {
            if (autoSemiNuevo.getDenuncias()!=null){
                for (Denuncia denuncia : autoSemiNuevo.getDenuncias()) {
                    denuncia.getUsuario().setNumeroDenuncias((long) denuncia.getUsuario().getDenuncias().size());
                }
            }
        }
        return new ResponseEntity<>(autoSemiNuevos,HttpStatus.OK);
    }

    @PutMapping
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> updatePost(@RequestBody AutoSemiNuevo autoSemiNuevo){
        if(autoSemiNuevo.getId()==null)return new ResponseEntity<>("no se envio id",HttpStatus.BAD_REQUEST);
        AutoSemiNuevo temp = autoSemiNuevoService.getById(autoSemiNuevo.getId());
        if(temp==null)return new ResponseEntity<>("no se encontro el auto",HttpStatus.BAD_REQUEST);
        if(temp.getComprado())return new ResponseEntity<>("el auto no puede ser modificado porque ya se vendio",HttpStatus.BAD_REQUEST);
        /*seting info*/
        autoSemiNuevo.setComprado(temp.getComprado());
        autoSemiNuevo.setValidado(temp.getValidado());
        autoSemiNuevo.setUsuario(temp.getUsuario());
        autoSemiNuevo.setSolicitudRemocionAuto(temp.getSolicitudRemocionAuto());
        autoSemiNuevo.setDenuncias(temp.getDenuncias());
        return new ResponseEntity<>("ok",HttpStatus.OK);

    }

    @GetMapping("/vendidos")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getVendidos(){
        try{
            return new ResponseEntity<>(autoSemiNuevoService.getAllVendidos(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/novendidos")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getNoVendidos(){
        try{
            return new ResponseEntity<>(autoSemiNuevoService.getAllNoVendidos()+autosService.countAll(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/marcas")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getMarcas(){
        try{
            List<String> marcas = autoSemiNuevoService.getAllMarcasString();
            marcas.addAll(autosService.findAllMarcas());
            Set<String> set = new HashSet<>(marcas);
            return new ResponseEntity<>(set.size(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("nice",HttpStatus.OK);
        }
    }




    @Autowired
    AutosService autosService;

    @GetMapping("/filtros")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getFiltros(){
        try{
            List<FiltrosBean> filtrosBeans = new ArrayList<>();
            List<Tuple> temp = autoSemiNuevoService.getFilters();
            for (Tuple tuple : temp) {
                filtrosBeans.add(new FiltrosBean((String)tuple.get("marca"),(String)tuple.get("modelo"),(String)tuple.get("tipo_carroceria"),"USED"));
            }
            temp = autosService.getFiltros();
            for (Tuple tuple : temp) {
                filtrosBeans.add(new FiltrosBean((String)tuple.get("marca"),(String)tuple.get("modelo"),(String)tuple.get("tipo_carroceria"),"NEW"));
            }
            return new ResponseEntity<>(filtrosBeans,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/novalidados")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getAutosNoValidados(){
        try{
            return new ResponseEntity<>(autoSemiNuevoService.getAutosNoValidados(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}

