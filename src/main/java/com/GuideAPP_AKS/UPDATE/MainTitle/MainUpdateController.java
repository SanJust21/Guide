package com.GuideAPP_AKS.UPDATE.MainTitle;

import com.GuideAPP_AKS.img.ImgService;
import com.GuideAPP_AKS.img.mainHeading.ImgData;
import com.GuideAPP_AKS.img.mainHeading.ImgRepo;
import com.GuideAPP_AKS.mainHeading.MainDTO;
import com.GuideAPP_AKS.mainHeading.mainEng.MainTitleEng;
import com.GuideAPP_AKS.mainHeading.mainEng.MainTitleEngRepo;
import com.GuideAPP_AKS.mainHeading.mainMal.MainTitleMal;
import com.GuideAPP_AKS.mainHeading.mainMal.MainTitleMalRepo;
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
@RequestMapping(path = "/updateMain")
@CrossOrigin
public class MainUpdateController {
    @Autowired
    private MainUpdateService mainUpdateService;
    @Autowired
    private MainTitleMalRepo mainTitleMalRepo;
    @Autowired
    private MainTitleEngRepo mainTitleEngRepo;
    @Autowired
    private ImgRepo imgRepo;
    @Autowired
    private FileTypeRepo fileTypeRepo;
    @Autowired
    private MediaTypeService mediaTypeService;
    @Autowired
    private ImgService imgService;
    @PutMapping(path = "/stringUpdate/{uId}")
    public ResponseEntity<?> mainDataUpdate(@PathVariable String uId,
                                            @RequestBody MainDTO mainDTO){
        try {
            if (uId==null || uId.isEmpty()){
                return new ResponseEntity<>("ID is required", HttpStatus.BAD_REQUEST);
            }else {
                Optional<MainTitleMal> mainTitleMal =mainTitleMalRepo.findBymMalUid(uId);
                if (mainTitleMal.isPresent()){
                    MainTitleMal mainTitleMal1 = mainTitleMal.get();
                    if (mainTitleMal1.getMMalUid().equals(uId)){
                        return mainUpdateService.updateDataMalyalam(uId,mainDTO);
                    }else {
                        return new ResponseEntity<>("No Data",HttpStatus.BAD_REQUEST);
                    }
                }else {
                    MainTitleEng mainTitleEng = mainTitleEngRepo.findBymEngUid(uId);
                    if (mainTitleEng!=null && mainTitleEng.getMEngUid().equals(uId)){
                        return mainUpdateService.updateDataEnglish(uId,mainDTO);
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @PutMapping(path = "/updateUploadImg")
//    public ResponseEntity<?> updateJpgMain(@RequestParam(value = "file") MultipartFile[] files,
//                                        @RequestParam String englishUId,
//                                        @RequestParam String malUid){
//
//        try {
//            if (englishUId == null || malUid == null) {
//                return new ResponseEntity<>("English UID and Malayalam UID are required", HttpStatus.BAD_REQUEST);
//            }else {
//                List<ImgData> existingImgData = imgRepo.findByengId(englishUId);
//                if (!existingImgData.isEmpty()){
//                    List<ImgData> responses = new ArrayList<>();
//                    for (MultipartFile file : files){
//                        responses.add(imgService.updateMainJPG(file,englishUId,malUid));
//                    }
//                    return new ResponseEntity<>(responses,HttpStatus.OK);
//                }else {
//                    return new ResponseEntity<>("imgRepo is empty",HttpStatus.BAD_REQUEST);
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//

    @PutMapping(path = "/updateUploadImg")
    public ResponseEntity<?> updateJpgMain(
            @RequestParam(value = "files") MultipartFile[] files,
            @RequestParam List<Integer> imgIds,
            @RequestParam String commonId) {

        try {
            if (commonId == null || imgIds.isEmpty() || files.length != imgIds.size()) {
                return new ResponseEntity<>("Common ID, image IDs, and files are required, and the number of files must match the number of image IDs", HttpStatus.BAD_REQUEST);
            } else {
                List<ImgData> existingImgDataList = imgRepo.findByCommonId(commonId);
                if (!existingImgDataList.isEmpty()) {
                    List<ImgData> responses = new ArrayList<>();
                    for (int i = 0; i < files.length; i++) {
                        responses.add(imgService.updateMainJPG(files[i], imgIds.get(i), commonId));
                    }
                    return new ResponseEntity<>(responses, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("No image data found for the provided Common ID", HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(path = "/updateMpData")
    public ResponseEntity<?> addMp3Data(@RequestParam String uId,
                                        @RequestParam Integer mtId,
                                        @RequestParam MultipartFile[] files){
        try {

            if (uId == null || mtId == null ) {
                return new ResponseEntity<>("Topic ID, Media Type ID required", HttpStatus.BAD_REQUEST);
            }

            Optional<FileType> fileTypeOptional =fileTypeRepo.findById(mtId);
            if (fileTypeOptional.isPresent()) {
                FileType fileType = fileTypeOptional.get();
                String fData = fileType.getFileType();
                if (fData != null && "Audio".equalsIgnoreCase(fData)) {
                    List<MediaTypeDTO> responses = new ArrayList<>();
                    for (MultipartFile file : files){
                        responses.add(mediaTypeService.updateUploadMp3(file,uId));
                    }
                    return new ResponseEntity<>(responses,HttpStatus.OK);
                    //return mediaTypeService.addMp3(files,dtId);
                } else if (fData != null && "Video".equalsIgnoreCase(fData)) {
                    List<MediaTypeDTO> responses = new ArrayList<>();
                    for (MultipartFile file : files){
                        responses.add(mediaTypeService.updateUploadMp4(file,uId));
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
