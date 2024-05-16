package com.GuideAPP_AKS.img;

import com.GuideAPP_AKS.img.firstSubHeading.ImgSubFirst;
import com.GuideAPP_AKS.img.mainHeading.ImgData;
import com.GuideAPP_AKS.img.secondSubHeading.ImgSubSecond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "imgData")
@CrossOrigin
public class ImgController {
    @Autowired
    private ImgService imgService;

    @PostMapping(path = "/uploadImg")
    public ResponseEntity<?> uploadData(@RequestParam(value = "file") MultipartFile[] files,
                                                    @RequestParam String englishUId,
                                                    @RequestParam String malUid){

        if (englishUId == null || malUid == null) {
            return new ResponseEntity<>("English UID and Malayalam UID are required", HttpStatus.BAD_REQUEST);
        }

        List<ImgData> responses = new ArrayList<>();
        for (MultipartFile file : files){
            responses.add(imgService.uploadJPG(file,englishUId,malUid));
        }
        return new ResponseEntity<>(responses,HttpStatus.OK);
    }

    @PostMapping(path = "/uploadImg1")
    public ResponseEntity<?> uploadData1(@RequestParam MultipartFile[] files,
                                                        @RequestParam String englishUId,
                                                        @RequestParam String malUid){
        try {

            if (englishUId == null || malUid == null) {
                return new ResponseEntity<>("English UID and Malayalam UID are required", HttpStatus.BAD_REQUEST);
            }

            List<ImgSubFirst> imgSubFirsts = new ArrayList<>();
            for (MultipartFile file : files){
                imgSubFirsts.add(imgService.uploadData1(file,englishUId,malUid));
            }
            return new ResponseEntity<>(imgSubFirsts,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PostMapping(path = "/uploadImg2")
    public ResponseEntity<?>uploadData2(@RequestParam MultipartFile[] files,
                                                         @RequestParam String englishUId,
                                                         @RequestParam String malUid){
        try {

            if (englishUId == null || malUid == null) {
                return new ResponseEntity<>("English UID and Malayalam UID are required", HttpStatus.BAD_REQUEST);
            }

            List<ImgSubSecond> imgSubSeconds = new ArrayList<>();
            for (MultipartFile file : files){
                imgSubSeconds.add(imgService.uploadData2(file,englishUId,malUid));
            }
            return new ResponseEntity<>(imgSubSeconds,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
