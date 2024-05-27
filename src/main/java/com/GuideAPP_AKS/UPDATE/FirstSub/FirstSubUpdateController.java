package com.GuideAPP_AKS.UPDATE.FirstSub;

import com.GuideAPP_AKS.firstSubHeading.english.FirstSubEnglish;
import com.GuideAPP_AKS.firstSubHeading.english.FirstSubEnglishRepo;
import com.GuideAPP_AKS.firstSubHeading.malayalam.FirstSubMalayalam;
import com.GuideAPP_AKS.firstSubHeading.malayalam.FirstSubMalayalamRepo;
import com.GuideAPP_AKS.img.ImgService;
import com.GuideAPP_AKS.img.firstSubHeading.ImgSubFirst;
import com.GuideAPP_AKS.img.firstSubHeading.ImgSubFirstRepo;
import com.GuideAPP_AKS.img.mainHeading.ImgData;
import com.GuideAPP_AKS.mainHeading.MainDTO;
import com.GuideAPP_AKS.mainHeading.mainEng.MainTitleEng;
import com.GuideAPP_AKS.mainHeading.mainMal.MainTitleMal;
import com.GuideAPP_AKS.mpFileData.MediaTypeDTO;
import com.GuideAPP_AKS.mpFileData.MediaTypeService;
import com.GuideAPP_AKS.mpFileData.mp3.firstSub.Mp3Data1Repo;
import com.GuideAPP_AKS.mpFileData.mp4.firstSub.Mp4Data1Repo;
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
@RequestMapping(path = "/updateFirst")
@CrossOrigin
public class FirstSubUpdateController {
    @Autowired
    private FirstSubUpdateService firstSubUpdateService;
    @Autowired
    private FirstSubEnglishRepo firstSubEnglishRepo;
    @Autowired
    private FirstSubMalayalamRepo firstSubMalayalamRepo ;
    @Autowired
    private ImgSubFirstRepo imgSubFirstRepo;
    @Autowired
    private Mp3Data1Repo mp3Data1Repo;
    @Autowired
    private Mp4Data1Repo mp4Data1Repo;
    @Autowired
    private ImgService imgService;
    @Autowired
    private FileTypeRepo fileTypeRepo;
    @Autowired
    private MediaTypeService mediaTypeService;


    @PutMapping(path = "/updateFirstData/{uId}")
    public ResponseEntity<?> firstDataUpdate(@PathVariable String uId,
                                            @RequestBody MainDTO mainDTO){
        try {
            if (uId==null || uId.isEmpty()){
                return new ResponseEntity<>("ID is required", HttpStatus.BAD_REQUEST);
            }else {
                Optional<FirstSubMalayalam> firstSubMalayalam =firstSubMalayalamRepo.findByfsUid(uId);
                if (firstSubMalayalam.isPresent()){
                    FirstSubMalayalam firstSubMalayalam1 = firstSubMalayalam.get();
                    if (firstSubMalayalam1.getFsUid().equals(uId)){
                        return firstSubUpdateService.updateFirstSubDataMalyalam(uId,mainDTO);
                    }else {
                        return new ResponseEntity<>("No Data",HttpStatus.BAD_REQUEST);
                    }
                }else {
                    Optional<FirstSubEnglish> firstSubEnglish = firstSubEnglishRepo.findByfsUid(uId);
                    if (firstSubEnglish.isPresent()){
                        FirstSubEnglish firstSubEnglish1 = firstSubEnglish.get();
                        if (firstSubEnglish1.getFsUid().equals(uId)){
                            return firstSubUpdateService.updateFirstSubDataEnglish(uId,mainDTO);
                        }
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PutMapping(path = "/updateUploadImgSubFirst")
    public ResponseEntity<?> updateJpgSubFirst(
            @RequestParam(value = "files") MultipartFile[] files,
            @RequestParam List<Integer> imgIds,
            @RequestParam String commonId) {

        try {
            if (commonId == null || imgIds.isEmpty() || files.length != imgIds.size()) {
                return new ResponseEntity<>("Common ID, image IDs, and files are required, and the number of files must match the number of image IDs", HttpStatus.BAD_REQUEST);
            } else {
                List<ImgSubFirst> existingImgDataList = imgSubFirstRepo.findByCommonId(commonId);
                if (!existingImgDataList.isEmpty()) {
                    List<ImgSubFirst> responses = new ArrayList<>();
                    for (int i = 0; i < files.length; i++) {
                        responses.add(imgService.updateFirstSubJPG(files[i], imgIds.get(i), commonId));
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

    @PutMapping(path = "/updateMpData1/{uId}")
    public ResponseEntity<?> addMp3Data(@PathVariable String uId,
                                        @RequestParam Integer mtId,
                                        @RequestParam MultipartFile[] files) {
        try {
            if (uId == null || mtId == null) {
                return new ResponseEntity<>("Topic ID, Media Type ID required", HttpStatus.BAD_REQUEST);
            }

            Optional<FileType> fileTypeOptional = fileTypeRepo.findById(mtId);
            if (fileTypeOptional.isPresent()) {
                FileType fileType = fileTypeOptional.get();
                String fData = fileType.getFileType();
                if (fData != null && "Audio".equalsIgnoreCase(fData)) {
                    List<MediaTypeDTO> responses = new ArrayList<>();
                    for (MultipartFile file : files) {
                        responses.add(mediaTypeService.updateFirstSubUploadMp3(file, uId));
                    }
                    return new ResponseEntity<>(responses, HttpStatus.OK);
                } else if (fData != null && "Video".equalsIgnoreCase(fData)) {
                    List<MediaTypeDTO> responses = new ArrayList<>();
                    for (MultipartFile file : files) {
                        responses.add(mediaTypeService.updateFirstSubUploadMp4(file, uId));
                    }
                    return new ResponseEntity<>(responses, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("File not present. Resend the file.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("File not present. Resend the file.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
