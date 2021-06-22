package clicker.back.controllers.utils;
import clicker.back.Setup;
import clicker.back.utils.entities.Accesorio;
import clicker.back.utils.errors.ResponseService;
import clicker.back.utils.services.AccesorioService;
import org.hibernate.sql.ordering.antlr.OrderingSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.*;

@RestController
@RequestMapping(value = "/accesorio")
@CrossOrigin(origins = {Setup.local/*,Setup.route*/}, allowedHeaders = "*")
public class AccesorioController {
    @Autowired
    AccesorioService accesorioService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<Object> getAll(){
        try{
            List<Accesorio> accesorioList = accesorioService.getAllEnabled();
            Map<String,List<Accesorio>> map = new HashMap<>();
            for (Accesorio accesorio : accesorioList) {
                if(!map.containsKey(accesorio.getTipo())){
                    map.put(accesorio.getTipo(),new ArrayList<>());
                }
                map.get(accesorio.getTipo()).add(accesorio);

            }
            Collection<List<Accesorio>> result =  map.values();

            return ResponseService.genSuccess(result.stream().sorted(new Comparator<List<Accesorio>>() {
                @Override
                public int compare(List<Accesorio> o1, List<Accesorio> o2) {
                    Integer x = o2.size();
                    Integer y = o1.size();

                    return x.compareTo(y);
                }
            }));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseService.genError("fallo en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<Object> getAllWithEnabled(){
        try{
            return ResponseService.genSuccess(accesorioService.getAll());
        }catch (Exception e){
            return ResponseService.genError("fallo en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Object> save(@RequestBody Accesorio accesorio){
        try{
            accesorio.setId(null);
            return ResponseService.genSuccess(accesorioService.save(accesorio));

        }catch (Exception e){
            return ResponseService.genError("fallo en el servidor ",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<Object> update(@RequestBody Accesorio accesorio){
        try{
            if(accesorio.getId()==null){
                return ResponseService.genError("no se encontro el accesorio",HttpStatus.NOT_FOUND);
            }
            Accesorio temp = accesorioService.getById(accesorio.getId());
            temp.setNombre(accesorio.getNombre());
            temp.setEnabled(accesorio.getEnabled());
            temp.setTipo(accesorio.getTipo());
            return ResponseService.genSuccess(accesorioService.save(temp));
        }catch (Exception e){
            return ResponseService.genError("error del servidor ",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Object> delete(@PathVariable("id")Long id){
        try{
            Accesorio accesorio = accesorioService.getById(id);
            accesorio.setEnabled(false);
            accesorioService.save(accesorio);
            return ResponseService.genSuccess(null);
        }catch (Exception e){
            return ResponseService.genError("fallo en el servidor ",HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


}
