package clicker.back.controllers.utils;

import clicker.back.Setup;
import clicker.back.services.AmazonService;
import clicker.back.utils.entities.FotoBanner;
import clicker.back.utils.errors.ResponseService;
import clicker.back.utils.services.FotoBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/fotoBanner")
@CrossOrigin(origins = {Setup.local/*,Setup.route*/}, allowedHeaders = "*")
public class FotoBannerController {
    @Autowired
    FotoBannerService fotoBannerService;

    @Autowired
    AmazonService amazonService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<Object> getFotoBanner(){
        try{
            return new ResponseEntity<>(fotoBannerService.findAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("fallo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Object> postFoto(@RequestPart("file")MultipartFile multipartFile){
        try{
            if(multipartFile==null){
                return ResponseService.genError("no se envio imagen",HttpStatus.BAD_REQUEST);
            }
            List<FotoBanner> foto = fotoBannerService.findAll();
            for (FotoBanner banner : foto) {
                amazonService.deleteFileFromS3Bucket(banner.getFoto());
                fotoBannerService.delete(banner);
            }
            FotoBanner fotoBanner=new FotoBanner();
            fotoBanner.setFoto(amazonService.uploadFile(multipartFile,"server","fotoBanner"));
            return new ResponseEntity<>(fotoBannerService.save(fotoBanner),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("FALLO",HttpStatus.BAD_REQUEST);
        }
    }

}
