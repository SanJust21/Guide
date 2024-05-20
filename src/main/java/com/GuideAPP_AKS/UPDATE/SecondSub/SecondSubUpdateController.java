package com.GuideAPP_AKS.UPDATE.SecondSub;

import com.GuideAPP_AKS.SecondSubHeading.english.SecondSubEnglish;
import com.GuideAPP_AKS.SecondSubHeading.english.SecondSubEnglishRepo;
import com.GuideAPP_AKS.SecondSubHeading.malayalam.SecondSubMalayalam;
import com.GuideAPP_AKS.SecondSubHeading.malayalam.SecondSubMalayalamRepo;
import com.GuideAPP_AKS.firstSubHeading.english.FirstSubEnglish;
import com.GuideAPP_AKS.firstSubHeading.malayalam.FirstSubMalayalam;
import com.GuideAPP_AKS.img.ImgService;
import com.GuideAPP_AKS.img.firstSubHeading.ImgSubFirst;
import com.GuideAPP_AKS.img.secondSubHeading.ImgSubSecond;
import com.GuideAPP_AKS.img.secondSubHeading.ImgSubSecondRepo;
import com.GuideAPP_AKS.mainHeading.MainDTO;
import com.GuideAPP_AKS.mpFileData.MediaTypeDTO;
import com.GuideAPP_AKS.mpFileData.MediaTypeService;
import com.GuideAPP_AKS.mpType.FileType;
import com.GuideAPP_AKS.mpType.FileTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/updateSecondSub")
public class SecondSubUpdateController {
    @Autowired
    private SecondSubUpdateService secondSubUpdateService;
    @Autowired
    private SecondSubMalayalamRepo secondSubMalayalamRepo;
    @Autowired
    private SecondSubEnglishRepo secondSubEnglishRepo;
    @Autowired
    private ImgSubSecondRepo imgSubSecondRepo;
    @Autowired
    private ImgService imgService;
    @Autowired
    private FileTypeRepo fileTypeRepo;
    @Autowired
    private MediaTypeService mediaTypeService;


    @PutMapping(path = "/updateSecondData/{uId}")
    public ResponseEntity<?> secondDataUpdate(@PathVariable String uId,
                                             @RequestBody MainDTO mainDTO){
        try {
            if (uId==null || uId.isEmpty()){
                return new ResponseEntity<>("ID is required", HttpStatus.BAD_REQUEST);
            }else {
                Optional<SecondSubMalayalam> secondSubMalayalam =secondSubMalayalamRepo.findByssUid(uId);
                if (secondSubMalayalam.isPresent()){
                    SecondSubMalayalam secondSubMalayalam1 = secondSubMalayalam.get();
                    if (secondSubMalayalam1.getSsUid().equals(uId)){
                        return secondSubUpdateService.updateSecondSubDataMalayalam(uId,mainDTO);
                    }else {
                        return new ResponseEntity<>("No Data",HttpStatus.BAD_REQUEST);
                    }
                }else {
                    Optional<SecondSubEnglish> secondSubEnglish = secondSubEnglishRepo.findByssUid(uId);
                    if (secondSubEnglish.isPresent()){
                        SecondSubEnglish secondSubEnglish1 = secondSubEnglish.get();
                        if (secondSubEnglish1.getSsUid().equals(uId)){
                            return secondSubUpdateService.updateSecondSubDataEnglish(uId,mainDTO);
                        }
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/updateUploadImg2/{englishUId}")
    public ResponseEntity<?> updateJpgSecond(@RequestParam(value = "file") MultipartFile[] files,
                                           @PathVariable String englishUId,
                                           @RequestParam String malUid){

        try {
            if (englishUId == null || malUid == null) {
                return new ResponseEntity<>("English UID and Malayalam UID are required", HttpStatus.BAD_REQUEST);
            }else {
                List<ImgSubSecond> existingImgData = imgSubSecondRepo.findByengId(englishUId);
                if (!existingImgData.isEmpty()){
                    List<ImgSubSecond> responses = new ArrayList<>();
                    for (MultipartFile file : files){
                        responses.add(imgService.updateSecondSubJPG(file,englishUId,malUid));
                    }
                    return new ResponseEntity<>(responses,HttpStatus.OK);
                }else {
                    return new ResponseEntity<>("imgRepo is empty",HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/updateMpData2/{uId}")
    public ResponseEntity<?> addMp3DataSecond(@PathVariable String uId,
                                        @RequestParam Integer mtId,
                                        @RequestParam MultipartFile[] files){
        try {

            if (uId == null || mtId == null ) {
                return new ResponseEntity<>("Topic ID, Media Type ID required", HttpStatus.BAD_REQUEST);
            }

            Optional<FileType> fileTypeOptional =fileTypeRepo .findById(mtId);
            if (fileTypeOptional.isPresent()) {
                FileType fileType = fileTypeOptional.get();
                String fData = fileType.getFileType();
                if (fData != null && "Audio".equalsIgnoreCase(fData)) {
                    List<MediaTypeDTO> responses = new ArrayList<>();
                    for (MultipartFile file : files){
                        responses.add(mediaTypeService.updateSecondSubUploadMp3(file,uId));
                    }
                    return new ResponseEntity<>(responses,HttpStatus.OK);
                    //return mediaTypeService.addMp3(files,dtId);
                } else if (fData != null && "Video".equalsIgnoreCase(fData)) {
                    List<MediaTypeDTO> responses = new ArrayList<>();
                    for (MultipartFile file : files){
                        responses.add(mediaTypeService.updateSecondSubUploadMp4(file,uId));
                    }
                    return new ResponseEntity<>(responses,HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("File not present. Resend the file.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("File not present. Resend the file.", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
