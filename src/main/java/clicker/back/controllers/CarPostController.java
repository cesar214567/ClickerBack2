package clicker.back.controllers;


import clicker.back.Setup;
import clicker.back.antiguo.Autos;
import clicker.back.controllers.beans.FiltrosBean;
import clicker.back.controllers.beans.PilotBean;
import clicker.back.entities.*;
import clicker.back.services.*;
import clicker.back.utils.entities.Accesorio;
import clicker.back.utils.errors.ResponseService;
import clicker.back.utils.services.AccesorioService;
import com.amazonaws.services.ec2.model.transform.UnmonitorInstancesResultStaxUnmarshaller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Tuple;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    AutoPatrocinadoService autoPatrocinadoService;

    @Autowired
    AmazonService amazonService;

    @Autowired
    AccesorioService accesorioService;

    ExecutorService executorService =new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
    @Value("${apitoken}")
    String apiToken;
    @Value("${apiurl}")
    String apiUrl;

    public ResultSet executeQuery(String sql) throws SQLException {
        Connection connection = DriverManager.getConnection(db2Url, db2Username, db2Password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        connection.close();
        return resultSet;
    }

    public void executeUpdate(String sql) throws SQLException {
        Connection connection = DriverManager.getConnection(db2Url, db2Username, db2Password);
        Statement statement = connection.createStatement();
        int resultSet = statement.executeUpdate(sql);
        connection.close();
    }

    @PostMapping
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> post(HttpServletRequest request) {
        try {
            List<MultipartFile> multipartFiles = new ArrayList<>();
            MultipartFile firstFile = null;
            String model = "{}";
            for (Part part : request.getParts()) {
                if (part.getContentType() != null) {
                    if (part.getName().equals("fotoPrincipal")) {
                        firstFile = new MockMultipartFile(part.getSubmittedFileName(), part.getSubmittedFileName(), part.getContentType(), part.getInputStream());
                    } else {
                        multipartFiles.add(new MockMultipartFile(part.getSubmittedFileName(), part.getSubmittedFileName(), part.getContentType(), part.getInputStream()));
                    }
                } else {
                    String theString = IOUtils.toString(part.getInputStream(), StandardCharsets.UTF_8);
                    model = String.valueOf(theString);
                }
            }
            ObjectMapper mapper = new ObjectMapper();
            AutoSemiNuevo autoSemiNuevo = mapper.readValue(model, AutoSemiNuevo.class);
            //public ResponseEntity<Object> post(@RequestBody AutoSemiNuevo autoSemiNuevo){
            if (autoSemiNuevo.getUsuario() == null || autoSemiNuevo.getUsuario().getCorreo() == null)
                return ResponseService.genError("no se envio un usuario", HttpStatus.BAD_REQUEST);
            List<AutoSemiNuevo> temp = autoSemiNuevoService.getByPlaca(autoSemiNuevo.getPlaca());
            if (autoSemiNuevo.getPlaca() == null) {
                return ResponseService.genError("no se mando la placa", HttpStatus.BAD_REQUEST);
            }
            for (AutoSemiNuevo semiNuevo : temp) {
                if (!semiNuevo.getComprado() && semiNuevo.getEnabled()) {
                    return ResponseService.genError("este auto esta siendo vendido", HttpStatus.BAD_REQUEST);
                }
            }
            Usuario user = usuariosService.getByCorreo(autoSemiNuevo.getUsuario().getCorreo());
            if (user == null) {
                return ResponseService.genError("no se encontro el usuario con ese id", HttpStatus.BAD_REQUEST);
            } else {
                Calendar c1 = Calendar.getInstance();
                Calendar c2 = Calendar.getInstance();
                c2.setTime(new Date());
                int cont = 0;
                List<AutoSemiNuevo> autos = user.getCarrosPosteados();
                for (AutoSemiNuevo auto : autos) {
                    c1.setTime(auto.getFechaPublicacion());
                    if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) cont++;
                }
                if (cont >= user.getCantidadCarrosAno()) {
                    return ResponseService.genError("ya agoto sus subidas anuales", HttpStatus.BAD_REQUEST);
                }
                autoSemiNuevo.setComprado(false);
                autoSemiNuevo.setValidado(false);
                autoSemiNuevo.setEnabled(true);
                autoSemiNuevo.setRevisado(true);
                autoSemiNuevo.setFechaPublicacion(new Date());
                List<String> fotos = new ArrayList<>();


                autoSemiNuevo.setUsuario(user);
                List<Accesorio> accesoriosList = new ArrayList<>();
                for (Accesorio accesorio : autoSemiNuevo.getAccesorios()) {
                    Accesorio accesorioTemp = accesorioService.getById(accesorio.getId());
                    if (accesorioTemp != null) {
                        accesoriosList.add(accesorioTemp);
                    }
                }
                autoSemiNuevo.setAccesorios(accesoriosList);
                autoSemiNuevo = autoSemiNuevoService.save(autoSemiNuevo);
                AutoSemiNuevo finalAutoSemiNuevo = autoSemiNuevo;
                (multipartFiles).forEach(file -> {
                    fotos.add(amazonService.uploadFile(file, user.getId().toString(), "fotosAutos/" + finalAutoSemiNuevo.getId().toString()));
                });
                if (firstFile != null) {
                    String fotoPrincipal = amazonService.uploadFile(firstFile, user.getId().toString(), "fotosAutos/" + finalAutoSemiNuevo.getId().toString());
                    finalAutoSemiNuevo.setFotoPrincipal(fotoPrincipal);
                }
                finalAutoSemiNuevo.setFotos(fotos);
                autos.add(finalAutoSemiNuevo);
                usuariosService.save(user);

                Runnable runnableTask = () -> {
                    try {
                        var values = new HashMap<String, String>() {{
                            put("placa", finalAutoSemiNuevo.getPlaca());
                            put("token", apiToken);
                        }};
                        var objectMapper = new ObjectMapper();
                        String requestBody = objectMapper
                                .writeValueAsString(values);
                        System.out.println(requestBody);
                        HttpClient client = HttpClient.newHttpClient();
                        HttpRequest requestApi = HttpRequest.newBuilder()
                                .uri(URI.create(apiUrl))
                                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                                .header("Content-Type", "application/json")
                                .build();
                        HttpResponse<String> response = client.send(requestApi,
                                HttpResponse.BodyHandlers.ofString());
                        String placa = finalAutoSemiNuevo.getPlaca();
                        JSONParser parser = new JSONParser();
                        JSONObject responseJson = (JSONObject) parser.parse(response.body());
                        JSONObject data = (JSONObject) responseJson.get("data");
                        String vin = (String) data.get("vin");

                        String color = (String) data.get("color");
                        autoSemiNuevoService.setVinAndColorByPlaca(placa, color, vin);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
                executorService.execute(runnableTask);
                return ResponseService.genSuccess(null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseService.genError("fallo", HttpStatus.BAD_REQUEST);
        }
    }

    @Autowired
    InteresadoCompraService interesadoCompraService;

    @PostMapping(value = "/interesadoCompra")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> intCompra(@RequestBody InteresadoCompra interesadoCompra) {
        if(interesadoCompra.getAutoSemiNuevo()==null || interesadoCompra.getAutoSemiNuevo().getId()==null){
            return ResponseService.genError("No se envio el auto por el cual esta interesado",HttpStatus.BAD_REQUEST);
        }
        if(interesadoCompraService.existByAutoAndCorreo(interesadoCompra.getAutoSemiNuevo().getId(),interesadoCompra.getCorreo())!=0){
            return ResponseService.genError("locked",HttpStatus.LOCKED);
        }
        interesadoCompra.setAutoSemiNuevo(autoSemiNuevoService.getById(interesadoCompra.getAutoSemiNuevo().getId()));
        if(interesadoCompra.getAutoSemiNuevo() == null){
            return ResponseService.genError("no se encontro el auto",HttpStatus.BAD_REQUEST);
        }
        interesadoCompra.setId(null);
        interesadoCompra.setAutoSemiNuevo(interesadoCompra.getAutoSemiNuevo());
        try{
            interesadoCompraService.save(interesadoCompra);
            return ResponseService.genSuccess(HttpStatus.OK);
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Object> getValidateById(@PathVariable("id") Long id) {
        try{
            if(id==null)return ResponseService.genError("no se mando el id",HttpStatus.BAD_REQUEST);
        AutoSemiNuevo autoSemiNuevo = autoSemiNuevoService.getById(id);
        if(autoSemiNuevo==null)return ResponseService.genError("no se encontro el auto",HttpStatus.NOT_FOUND);
        return ResponseService.genSuccess(autoSemiNuevo);
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    InteresadoReventaService interesadoReventaService;


    @PostMapping(value = "/interesadoVenta")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> intVenta(@RequestBody InteresadoReventa interesadoReventa) {
        if(interesadoReventa.getAutoSemiNuevo()==null || interesadoReventa.getAutoSemiNuevo().getId()==null){
            return ResponseService.genError("No se envio el auto por el cual esta interesado",HttpStatus.BAD_REQUEST);
        }
        if(interesadoReventaService.existByAutoIdAndUsuarioId(interesadoReventa.getAutoSemiNuevo().getId(), interesadoReventa.getUsuario().getId())!=0){
            return ResponseService.genError("este auto ya se ha marcado como interesado en vender",HttpStatus.LOCKED);

        }
        interesadoReventa.setAutoSemiNuevo(autoSemiNuevoService.getById(interesadoReventa.getAutoSemiNuevo().getId()));
        if(interesadoReventa.getAutoSemiNuevo() == null){
            return ResponseService.genError("no se encontro el auto",HttpStatus.NOT_FOUND);
        }
        if(interesadoReventa.getUsuario()==null || interesadoReventa.getUsuario().getId()==null){
            return ResponseService.genError("No se envio el usuario interesado",HttpStatus.BAD_REQUEST);
        }
        interesadoReventa.setUsuario(usuariosService.getById(interesadoReventa.getUsuario().getId()));
        if(interesadoReventa.getUsuario() == null){
            return ResponseService.genError(" se encontro el usuario en la base de datos",HttpStatus.NOT_FOUND);
        }
        if(!interesadoReventa.getUsuario().getRol().equals("REMAX")){
            return ResponseService.genError("el usuario no es REMAX",HttpStatus.LOCKED);
        }
        interesadoReventa.setId(null);
        interesadoReventa.setUsuario(interesadoReventa.getUsuario());
        interesadoReventa.getUsuario().getInteresadoReventas().add(interesadoReventa);
        interesadoReventa.setAutoSemiNuevo(interesadoReventa.getAutoSemiNuevo());
        try{
            interesadoReventaService.save(interesadoReventa);
            return ResponseService.genSuccess(null);
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    CompradorService compradorService;


    @PostMapping(value = "/venta")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> ventaSemiNuevo(@RequestPart("file")MultipartFile file,@RequestPart("ventaSemiNuevo") String model) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        VentaSemiNuevo ventaSemiNuevo = objectMapper.readValue(model,VentaSemiNuevo.class);
        if(ventaSemiNuevo.getComisionGeneral()==null || ventaSemiNuevo.getPrecioFinalVenta()==null){
            return ResponseService.genError("no se enviaron la comision o el precio final",HttpStatus.BAD_REQUEST);
        }
        if(file==null){
            return ResponseService.genError("no se envio la foto",HttpStatus.BAD_REQUEST);
        }
        if(ventaSemiNuevo.getComprador()!=null){
            if(ventaSemiNuevo.getComprador().getCorreo()==null){
                return ResponseService.genError("no se envio el correo",HttpStatus.BAD_REQUEST);
            }
            Comprador comprador = null;
            comprador= compradorService.getById(ventaSemiNuevo.getComprador().getCorreo());
            if(comprador==null ){
                Tuple usuario= usuariosService.getData(ventaSemiNuevo.getComprador().getCorreo());
                if(usuariosService.existByCorreo(ventaSemiNuevo.getComprador().getCorreo())){
                    comprador=new Comprador();
                    comprador.setCorreo(ventaSemiNuevo.getComprador().getCorreo());
                    comprador.setNombre((String) usuario.get("nombre"));
                    if(usuario.get("num_telefono")!=null)comprador.setTelefono( usuario.get("num_telefono").toString());
                    ventaSemiNuevo.setComprador(comprador);
                }
            }else{
                ventaSemiNuevo.setComprador(comprador);
            }
        }
        if(ventaSemiNuevo.getAutoSemiNuevo()==null || ventaSemiNuevo.getAutoSemiNuevo().getId()==null){
            return ResponseService.genError("no se mando el auto",HttpStatus.BAD_REQUEST);
        }
        ventaSemiNuevo.setAutoSemiNuevo(autoSemiNuevoService.getById(ventaSemiNuevo.getAutoSemiNuevo().getId()));
        if(ventaSemiNuevo.getAutoSemiNuevo()==null ){
            return ResponseService.genError("no se encontro el auto con ese id",HttpStatus.BAD_REQUEST);
        }
        if(ventaSemiNuevo.getAutoSemiNuevo().getComprado()){
            return ResponseService.genError("este auto ya ha sido comprado anteriormente",HttpStatus.BAD_REQUEST);
        }
        ventaSemiNuevo.setFecha(new Date());
        float gananciaVendedor=0;
        float gananciaClicker = 0;
        float gananciaUsuario = 0;
        if(ventaSemiNuevo.getAutoSemiNuevo().getUsuario().getRol().equals("USUARIO") ){
            if(ventaSemiNuevo.getVendedor()!=null){
                ventaSemiNuevo.setVendedor(usuariosService.getByCorreo(ventaSemiNuevo.getVendedor().getCorreo()));
                if(ventaSemiNuevo.getVendedor()==null){
                    return ResponseService.genError("no se encontro el vendedor con ese id",HttpStatus.BAD_REQUEST);
                }
                gananciaVendedor= (float) (ventaSemiNuevo.getPrecioFinalVenta()*ventaSemiNuevo.getComisionGeneral()*0.4);
                gananciaClicker= (float) (ventaSemiNuevo.getPrecioFinalVenta()*ventaSemiNuevo.getComisionGeneral()*0.6);
            }else{
                gananciaClicker=  (ventaSemiNuevo.getPrecioFinalVenta()*ventaSemiNuevo.getComisionGeneral()*1);
            }
        }else{
            if(ventaSemiNuevo.getVendedor()!=null){
                ventaSemiNuevo.setVendedor(usuariosService.getByCorreo(ventaSemiNuevo.getVendedor().getCorreo()));
                if(ventaSemiNuevo.getVendedor()==null){
                    return ResponseService.genError("no se encontro el vendedor con ese id",HttpStatus.BAD_REQUEST);
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
        ventaSemiNuevo.getAutoSemiNuevo().setEnabled(false);
        ventaSemiNuevo.setGananciaEmpresa(gananciaClicker);
        ventaSemiNuevo.setGananciaUsuario(gananciaUsuario);
        ventaSemiNuevo.setGananciaVendedor(gananciaVendedor);
        AutoPatrocinado autoPatrocinado = autoPatrocinadoService.findByAutoSemiNuevo(ventaSemiNuevo.getAutoSemiNuevo());
        String foto ="";
        try{
            foto = amazonService.uploadFile(file,ventaSemiNuevo.getAutoSemiNuevo().getUsuario().getId().toString(),
                    "fotosVentas/"+ ventaSemiNuevo.getAutoSemiNuevo().getId().toString());
            ventaSemiNuevo.setFoto(foto);
            usuariosService.updateBalance(gananciaUsuario,ventaSemiNuevo.getAutoSemiNuevo().getUsuario().getCorreo());
            if(ventaSemiNuevo.getVendedor()!=null){
                usuariosService.updateBalance(gananciaVendedor,ventaSemiNuevo.getVendedor().getCorreo());
            }
            if(autoPatrocinado!=null){
                autoPatrocinadoService.delete(autoPatrocinado);
            }

            ventaSemiNuevoService.save(ventaSemiNuevo);

            return ResponseService.genSuccess(null);
        }catch (Exception e){
            if(!foto.equals("")){
                amazonService.deleteFileFromS3Bucket(foto);
            }
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/enabled")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getEnabled(){
        try{
            return ResponseService.genSuccess(autoSemiNuevoService.getAllEnabled(true,true,false));
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/all")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getAll() throws SQLException {
        try{
            List<Object> autos = new ArrayList<>();
            autos.addAll(autoSemiNuevoService.getAllEnabled(true,true,false));
            ResultSet resultSet = executeQuery("select * from autos a where a.presentar!=false");
            while(resultSet.next()){
                autos.add(new Autos(resultSet));
                /*Array array= resultSet.getArray("ciudadesdisp");
                String[] nullable = (String[])array.getArray();
                for()*/
            }
            return ResponseService.genSuccess(autos);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping(value = "/enabled/{pageId}")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getEnabledPaginated(@PathVariable("pageId") Integer pageId){
        try{
            return ResponseService.genSuccess(autoSemiNuevoService.getAllEnabled(true,true,false,PageRequest.of(pageId,8, Sort.by("fechaPublicacion").descending())));
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/enabled/count")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getEnabledCount(){
        try{
            return ResponseService.genSuccess(autoSemiNuevoService.getPages(true,true,false));
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/validate/{id}")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> modify(@PathVariable("id")Long id ){
        AutoSemiNuevo autoSemiNuevo = autoSemiNuevoService.getById(id);
        if(autoSemiNuevo==null){
            return ResponseService.genError("no se encontro el post con ese id",HttpStatus.BAD_REQUEST);
        }
        autoSemiNuevo.setValidado(true);
        try{
            return ResponseService.genSuccess(autoSemiNuevoService.save(autoSemiNuevo));
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @PutMapping
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> updatePost(HttpServletRequest request) throws JsonProcessingException {
//  public ResponseEntity<Object> updatePost(@RequestBody AutoSemiNuevo autoSemiNuevo){
        try{

            List<MultipartFile> multipartFiles = new ArrayList<>();
            MultipartFile firstFile = null;
            String model="{}";
            for (Part part:request.getParts()){
                if(part.getContentType()!=null ){
                    if (part.getName().equals("fotoPrincipal")){
                        firstFile = new MockMultipartFile(part.getSubmittedFileName(),part.getSubmittedFileName(),part.getContentType(),part.getInputStream());
                    }else{
                        multipartFiles.add(new MockMultipartFile(part.getSubmittedFileName(),part.getSubmittedFileName(),part.getContentType(),part.getInputStream()) );
                    }
                }else{
                    String theString = IOUtils.toString(part.getInputStream(), StandardCharsets.UTF_8);
                    model = String.valueOf(theString);
                }
            }
            ObjectMapper objectMapper = new ObjectMapper();
            AutoSemiNuevo autoSemiNuevo = objectMapper.readValue(model,AutoSemiNuevo.class);
            if(autoSemiNuevo.getId()==null)return ResponseService.genError("no se envio id",HttpStatus.BAD_REQUEST);
            AutoSemiNuevo temp = autoSemiNuevoService.getById(autoSemiNuevo.getId());
            if(temp==null){
                return ResponseService.genError("no se encontro el auto",HttpStatus.BAD_REQUEST);
            }
            if(temp.getComprado()){
                return ResponseService.genError("el auto no puede ser modificado porque ya se vendio",HttpStatus.BAD_REQUEST);
            }
            List<Accesorio> accesoriosList = new ArrayList<>();
            for (Accesorio accesorio : autoSemiNuevo.getAccesorios()) {
                Accesorio accesorioTemp = accesorioService.getById(accesorio.getId());
                if (accesorioTemp!=null){
                    accesoriosList.add(accesorioTemp);
                }
            }
            temp.setAccesorios(accesoriosList);
            autoSemiNuevo.info(temp);
            Map<String,Integer> fotos = new HashMap<>();
            autoSemiNuevo.getFotos().forEach(foto->fotos.put(foto,0));
            if (temp.getFotoPrincipal()!=null && !autoSemiNuevo.getFotoPrincipal().equals(temp.getFotoPrincipal())) {
                fotos.put(autoSemiNuevo.getFotoPrincipal(), 0);
            }
            temp.setFotoPrincipal(autoSemiNuevo.getFotoPrincipal());
            temp.getFotos().forEach(foto->{
                if(!fotos.containsKey(foto)){
                    amazonService.deleteFileFromS3Bucket(foto);
                }
            });
            temp.setFotos(autoSemiNuevo.getFotos());
            (multipartFiles).forEach(file->temp.getFotos().add(amazonService.uploadFile(file,temp.getUsuario().getId().toString(),"fotosAutos/"+ temp.getId().toString())));

            if(firstFile!=null){
                if(temp.getFotoPrincipal()!=null)amazonService.deleteFileFromS3Bucket(temp.getFotoPrincipal());
                temp.setFotoPrincipal(amazonService.uploadFile(firstFile,temp.getUsuario().getId().toString(),"fotosAutos/"+ temp.getId().toString()));

            }
            autoSemiNuevoService.save(temp);
            return ResponseService.genSuccess(null);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/vendidos")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getVendidos(){
        try{
            return ResponseService.genSuccess(autoSemiNuevoService.getAllVendidos());
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<Object> getNoVendidos()  {
        try{
            ResultSet resultSet = executeQuery("SELECT count(*) as count FROM autos a where a.presentar!=false");
            resultSet.next();
            Long sumatotal = autoSemiNuevoService.getAllNoVendidos()+resultSet.getLong("count");
            return ResponseService.genSuccess(sumatotal);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/novendidos/nuevo")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getNoVendidosNuevos()  {
        try{
            ResultSet resultSet = executeQuery("SELECT count(*) as count FROM autos a where a.presentar!=false");
            resultSet.next();
            Long sumatotal = resultSet.getLong("count");
            return ResponseService.genSuccess(sumatotal);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/novendidos/semiNuevo")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getNoVendidosSemiNuevos() throws SQLException {
        try{
            ResultSet resultSet = executeQuery("SELECT count(*) as count FROM autos a where a.presentar!=false");
            resultSet.next();
            Long sumatotal = autoSemiNuevoService.getAllNoVendidos();
            return ResponseService.genSuccess(sumatotal);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @GetMapping("/marcas")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getMarcas() throws SQLException {
        try{
            List<String> marcas = autoSemiNuevoService.getAllMarcasString();
            ResultSet resultSet = executeQuery("select distinct (a.marca) as marcas from autos a where a.presentar!=false");

            while(resultSet.next()){
                marcas.add(resultSet.getString("marcas"));
            }
            Set<String> set = new HashSet<>(marcas);
            return ResponseService.genSuccess(set.size());
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/filtros")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getFiltros() {
        Connection connection=null;
        try{
            List<FiltrosBean> filtrosBeans = new ArrayList<>();
            List<Tuple> temp = autoSemiNuevoService.getFilters();
            for (Tuple tuple : temp) {
                filtrosBeans.add(new FiltrosBean((String)tuple.get("marca"),(String)tuple.get("modelo"),(String)tuple.get("tipo_carroceria"),"USED"));
            }
            //TODO
            ResultSet resultSet = executeQuery("select count(a.id_auto) as count ,a.marca as marca ,a.modelo as modelo " +
                    ",a.tipocarroceria as tipo_carroceria from autos a where a.presentar!=false group by (a.marca,a.modelo,a.tipocarroceria )");
            while(resultSet.next()){
                filtrosBeans.add(new FiltrosBean(resultSet.getString("marca"),resultSet.getString("modelo"),resultSet.getString("tipo_carroceria"),"NEW"));
            }

            return ResponseService.genSuccess(filtrosBeans);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/novalidados")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getAutosNoValidados(){
        try{
            return ResponseService.genSuccess(autoSemiNuevoService.getAutosNoValidados());
        }catch (Exception e){
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/nuevo")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getAutosNuevos(){
        try{
            ResultSet resultSet = executeQuery("select * from autos a where a.presentar!=false");
            List<Autos> autosNuevos = new ArrayList<>();
            while(resultSet.next()){
                Autos autos = new Autos(resultSet);
                autosNuevos.add(autos);
            }
            return ResponseService.genSuccess(autosNuevos);
        } catch (Exception e ) {
            e.printStackTrace();
            return ResponseService.genError("fallo ",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/nuevo/{id}")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> getAutosNuevos(@PathVariable("id")String id){
        if(id==null)return ResponseService.genError("no se mando el id",HttpStatus.BAD_REQUEST);
        try{
            String statements ="select * from autos a where a.presentar!=false and a.id_auto=\'"+id+"\'";
            ResultSet resultSet = executeQuery(statements);
            Autos auto=null;
            if(resultSet.next()){
                auto = new Autos(resultSet);
                return ResponseService.genSuccess(auto);
            }else{
                return ResponseService.genError("no se encontro ",HttpStatus.NOT_FOUND);
            }

        } catch (Exception e ) {
            e.printStackTrace();
            return ResponseService.genError("fallo ",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/pilot")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> postPilot(@RequestBody PilotBean pilotBean){
        try{
            String query = "select id_user,numtelefono from usuarios where numdocumento=\'"+
                    pilotBean.getDni()+"\'";
            System.out.println(query);
            ResultSet resultSet = executeQuery(query);
            Long id_user = 0L;
            if(resultSet.next()){
                id_user = resultSet.getLong("id_user");
                String numtelefono = resultSet.getString("numtelefono");
                if(numtelefono==null){
                    executeUpdate("update usuarios set numtelefono=\'"+pilotBean.getNumTelefono()+
                            "\' where id_user="+id_user);
                }
            }else{
                String query2="insert into usuarios(nombre,appellidos,correo,tipodocumento,numdocumento,numtelefono) " +
                        "values(\'nombre2\',\'appellidos2\',\'correo2\',\'tipodocumento2\',\'numdocumento2\',\'numtelefono2\')";
                query2 =query2.replace("nombre2",pilotBean.getNombre());
                query2 =query2.replace("appellidos2",pilotBean.getApellidos());
                query2 =query2.replace("correo2",pilotBean.getCorreo());
                query2 =query2.replace("tipodocumento2","DNI");
                query2 =query2.replace("numdocumento2",pilotBean.getDni());
                query2 =query2.replace("numtelefono2",pilotBean.getNumTelefono());
                executeUpdate(query2);
                resultSet = executeQuery(query);
                if(resultSet.next()) {
                    id_user = resultSet.getLong("id_user");
                }
            }
            String query3="insert into solicitudes(tipouso,tipoauto,id_auto,id_user,fecha,canalinput,hora,ciudadcompra) " +
                    "values(\'tipouso2\',\'tipoauto2\',\'id_auto2\',\'id_user2\',\'fecha2\',\'canalinput2\',\'hora2\',\'ciudadcompra2\')";
            query3 =query3.replace("tipouso2",pilotBean.getTipouso());
            query3 =query3.replace("tipoauto2",pilotBean.getCarroceria());
            query3 =query3.replace("id_auto2","424");
            query3 =query3.replace("id_user2",id_user.toString());
            query3 =query3.replace("fecha2",LocalDate.now().toString());
            query3 =query3.replace("canalinput2","CRM-WEB-CLICKER");
            query3 =query3.replace("hora2", LocalTime.now().format(DateTimeFormatter.ofPattern("H:mm:ss")));
            query3 =query3.replace("ciudadcompra2", "Lima");

            executeUpdate(query3);
            return ResponseService.genSuccess(null);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseService.genError("fallo ",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Autowired
    SolicitudRemocionService solicitudRemocionService;

    @PostMapping("/solicitudRemocion")
    @ResponseBody
    @Transactional
    public ResponseEntity<Object> removePost(@RequestBody SolicitudRemocionAuto solicitudRemocionAuto){
        try{
            if (solicitudRemocionAuto.getAutoSemiNuevo()==null || solicitudRemocionAuto.getAutoSemiNuevo().getId()==null){
                return ResponseService.genError("no se envio los datos del auto",HttpStatus.BAD_REQUEST);
            }
            Boolean existPost = autoSemiNuevoService.existsById(solicitudRemocionAuto.getAutoSemiNuevo().getId());
            Boolean existSolicitud = solicitudRemocionService.existSolicitudByCar(solicitudRemocionAuto.getAutoSemiNuevo().getId());
            if(existSolicitud!=null){
                return ResponseService.genError("el carro ya tiene una solicitud",HttpStatus.CONFLICT);
            }
            if (existPost ){
                solicitudRemocionAuto.setId(null);
                solicitudRemocionAuto.setAccepted(null);
                AutoSemiNuevo autoSemiNuevo = autoSemiNuevoService.getById(solicitudRemocionAuto.getAutoSemiNuevo().getId());
                autoSemiNuevo.setSolicitudRemocionAuto(solicitudRemocionAuto);
                solicitudRemocionAuto.setAutoSemiNuevo(autoSemiNuevo);
                return ResponseService.genSuccess(solicitudRemocionService.save(solicitudRemocionAuto));
            }else{
                return ResponseService.genError("el carro no fue encontrado",HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseService.genError("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

