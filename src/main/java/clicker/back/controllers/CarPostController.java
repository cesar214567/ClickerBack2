package clicker.back.controllers;


import clicker.back.Setup;
import clicker.back.controllers.beans.FiltrosBean;
import clicker.back.entities.*;
import clicker.back.services.*;
import clicker.back.utils.services.LocacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

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
            if(!semiNuevo.getComprado() && semiNuevo.getEnabled()){
                return new ResponseEntity<>("este auto esta siendo vendido",HttpStatus.IM_USED);
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
            autoSemiNuevo.setComprado(false);
            autoSemiNuevo.setValidado(false);
            autoSemiNuevo.setEnabled(true);
            autoSemiNuevo.setRevisado(true);
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

    @PostMapping(value = "/interesadoCompra")
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

    @PostMapping(value = "/interesadoVenta")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> intVenta(@RequestBody InteresadoReventa interesadoReventa) {
        if(interesadoReventa.getAutoSemiNuevo()==null || interesadoReventa.getAutoSemiNuevo().getId()==null) return new ResponseEntity<>("No se envio el auto por el cual esta interesado",HttpStatus.BAD_REQUEST);
        interesadoReventa.setAutoSemiNuevo(autoSemiNuevoService.getById(interesadoReventa.getAutoSemiNuevo().getId()));
        if(interesadoReventa.getAutoSemiNuevo() == null) return new ResponseEntity<>("no se encontro el auto",HttpStatus.NOT_FOUND);
        if(interesadoReventa.getUsuario()==null || interesadoReventa.getUsuario().getCorreo()==null) return new ResponseEntity<>("No se envio el usuario interesado",HttpStatus.BAD_REQUEST);
        interesadoReventa.setUsuario(usuariosService.getById(interesadoReventa.getUsuario().getCorreo()));
        if(interesadoReventa.getUsuario() == null) return new ResponseEntity<>("no se encontro el usuario en la base de datos",HttpStatus.NOT_FOUND);
        if(!interesadoReventa.getUsuario().getRol().equals("REMAX")){
            return new ResponseEntity<>("el usuario no es REMAX",HttpStatus.LOCKED);
        }
        interesadoReventa.setId(null);
        interesadoReventa.setUsuario(interesadoReventa.getUsuario());
        interesadoReventa.getUsuario().getInteresadoReventas().add(interesadoReventa);
        interesadoReventa.setAutoSemiNuevo(interesadoReventa.getAutoSemiNuevo());
        try{
            interesadoReventaService.save(interesadoReventa);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("se encontro algun error",HttpStatus.BAD_REQUEST);
        }
    }
 
    @DeleteMapping(value = "interesadoVenta")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> deleteInteresadoReventa(@RequestParam("id") Long id){
        if(id==null){
            return new ResponseEntity<>("no se mando el id de la solicitud",HttpStatus.BAD_REQUEST);
        }
        InteresadoReventa interesadoReventa = interesadoReventaService.getById(id);
        if(interesadoReventa==null){
            return new ResponseEntity<>("no se encontro solicitud",HttpStatus.BAD_REQUEST);
        }
        interesadoReventa.getUsuario().getInteresadoReventas().removeIf(t-> t.getId().equals(id));

        try{
            usuariosService.save(interesadoReventa.getUsuario());
            interesadoReventaService.delete(interesadoReventa);
            return new ResponseEntity<>("eliminado",HttpStatus.OK);
        }catch (Exception e ){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(value = "/venta")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> ventaSemiNuevo(@RequestBody VentaSemiNuevo ventaSemiNuevo){
        if(ventaSemiNuevo.getComisionGeneral()==null || ventaSemiNuevo.getPrecioFinalVenta()==null){
            return new ResponseEntity<>("no se enviaron la comision o el precio fianl",HttpStatus.BAD_REQUEST);
        }
        if(ventaSemiNuevo.getFoto()==null) return new ResponseEntity<>("no se envio la foto",HttpStatus.BAD_REQUEST);
        if(ventaSemiNuevo.getAutoSemiNuevo()==null || ventaSemiNuevo.getAutoSemiNuevo().getId()==null){
            return new ResponseEntity<>("no se mando el auto",HttpStatus.BAD_REQUEST);
        }
        ventaSemiNuevo.setAutoSemiNuevo(autoSemiNuevoService.getById(ventaSemiNuevo.getAutoSemiNuevo().getId()));
        if(ventaSemiNuevo.getAutoSemiNuevo()==null )return new ResponseEntity<>("no se encontro el auto con ese id",HttpStatus.BAD_REQUEST);
        if(ventaSemiNuevo.getAutoSemiNuevo().getComprado())return new ResponseEntity<>("este auto ya ha sido comprado anteriormente",HttpStatus.CONFLICT);
        ventaSemiNuevo.setFecha(new Date());
        float gananciaVendedor=0;
        float gananciaClicker = 0;
        float gananciaUsuario = 0;
        if(ventaSemiNuevo.getAutoSemiNuevo().getUsuario().getRol().equals("USUARIO") ){
            if(ventaSemiNuevo.getVendedor()!=null){
                ventaSemiNuevo.setVendedor(usuariosService.getById(ventaSemiNuevo.getVendedor().getCorreo()));
                if(ventaSemiNuevo.getVendedor()==null){
                    return new ResponseEntity<>("no se encontro el vendedor con ese id",HttpStatus.BAD_REQUEST);
                }
                gananciaVendedor= (float) (ventaSemiNuevo.getPrecioFinalVenta()*ventaSemiNuevo.getComisionGeneral()*0.4);
                gananciaClicker= (float) (ventaSemiNuevo.getPrecioFinalVenta()*ventaSemiNuevo.getComisionGeneral()*0.6);
            }else{
                gananciaClicker=  (ventaSemiNuevo.getPrecioFinalVenta()*ventaSemiNuevo.getComisionGeneral()*1);
            }
        }else{
            if(ventaSemiNuevo.getVendedor()!=null){
                ventaSemiNuevo.setVendedor(usuariosService.getById(ventaSemiNuevo.getVendedor().getCorreo()));
                if(ventaSemiNuevo.getVendedor()==null){
                    return new ResponseEntity<>("no se encontro el vendedor con ese id",HttpStatus.BAD_REQUEST);
                }
                gananciaVendedor= (float) (ventaSemiNuevo.getPrecioFinalVenta()*ventaSemiNuevo.getComisionGeneral()*0.4);
                gananciaUsuario= (float) (ventaSemiNuevo.getPrecioFinalVenta()*ventaSemiNuevo.getComisionGeneral()*0.4);
                gananciaClicker= (float) (ventaSemiNuevo.getPrecioFinalVenta()*ventaSemiNuevo.getComisionGeneral()*0.2);

            }else{
                gananciaUsuario= (float) (ventaSemiNuevo.getPrecioFinalVenta()*ventaSemiNuevo.getComisionGeneral()*0.4);
                gananciaClicker = (float) (ventaSemiNuevo.getPrecioFinalVenta()*ventaSemiNuevo.getComisionGeneral()*0.6);

            }
        }
        ventaSemiNuevo.getAutoSemiNuevo().setComprado(true);
        ventaSemiNuevo.setGananciaEmpresa(gananciaClicker);
        ventaSemiNuevo.setGananciaUsuario(gananciaUsuario);
        ventaSemiNuevo.setGananciaVendedor(gananciaVendedor);
        try{

            usuariosService.updateBalance(gananciaUsuario,ventaSemiNuevo.getAutoSemiNuevo().getUsuario().getCorreo());
            if(ventaSemiNuevo.getVendedor()!=null){
                usuariosService.updateBalance(gananciaVendedor,ventaSemiNuevo.getVendedor().getCorreo());
            }
            ventaSemiNuevoService.save(ventaSemiNuevo);
            return new ResponseEntity<>(null,HttpStatus.OK);
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

    @Value("${DB_USERNAME2}")
    String db2Username;
    @Value("${DB_PASSWORD2}")
    String db2Password;
    @Value("${DB_URL2}")
    String db2Url;

    @GetMapping("/novendidos")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getNoVendidos() throws SQLException {
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(db2Url, db2Username, db2Password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT count(*) as count FROM autos");
            resultSet.next();
            Long sumatotal = autoSemiNuevoService.getAllNoVendidos()+resultSet.getLong("count");
            connection.close();
            return new ResponseEntity<>(sumatotal,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            if(connection!=null){
                connection.close();
            }
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/marcas")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getMarcas() throws SQLException {
        Connection connection=null;
        try{
            List<String> marcas = autoSemiNuevoService.getAllMarcasString();
            connection = DriverManager.getConnection(db2Url, db2Username, db2Password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select distinct (a.marca) as marcas from autos a ");
            while(resultSet.next()){
                marcas.add(resultSet.getString("marcas"));
            }
            //marcas.addAll(autosService.findAllMarcas());
            //TODO
            Set<String> set = new HashSet<>(marcas);
            connection.close();
            return new ResponseEntity<>(set.size(),HttpStatus.OK);
        }catch (Exception e){
            if(connection!=null){
                connection.close();
            }
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/filtros")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getFiltros() throws SQLException {
        Connection connection=null;
        try{
            List<FiltrosBean> filtrosBeans = new ArrayList<>();
            List<Tuple> temp = autoSemiNuevoService.getFilters();
            for (Tuple tuple : temp) {
                filtrosBeans.add(new FiltrosBean((String)tuple.get("marca"),(String)tuple.get("modelo"),(String)tuple.get("tipo_carroceria"),"USED"));
            }
            //TODO
            connection = DriverManager.getConnection(db2Url, db2Username, db2Password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(a.id_auto) as count ,a.marca as marca ,a.modelo as modelo ,a.tipocarroceria as tipo_carroceria from autos a  group by (a.marca,a.modelo,a.tipocarroceria )");
            while(resultSet.next()){
                filtrosBeans.add(new FiltrosBean(resultSet.getString("marca"),resultSet.getString("modelo"),resultSet.getString("tipo_carroceria"),"NEW"));
            }

            connection.close();
            return new ResponseEntity<>(filtrosBeans,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            if(connection!=null)connection.close();
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

