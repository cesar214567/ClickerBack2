package clicker.back.utils.errors;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseService {
    public static ResponseEntity<Object> genError(String message, HttpStatus httpStatus){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error",message);
        return new ResponseEntity<>(jsonObject,httpStatus);
    }
    public static ResponseEntity<Object> genSuccess(Object message){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error",message);
        return new ResponseEntity<>(jsonObject,HttpStatus.OK);
    }

}
