package com.GuideAPP_AKS.SecondSubHeading;

import com.GuideAPP_AKS.SecondSubHeading.commonId.CommonIdSs;
import com.GuideAPP_AKS.SecondSubHeading.commonId.CommonIdSsRepo;
import com.GuideAPP_AKS.SecondSubHeading.english.SecondSubEnglish;
import com.GuideAPP_AKS.SecondSubHeading.english.SecondSubEnglishRepo;
import com.GuideAPP_AKS.SecondSubHeading.malayalam.SecondSubMalayalam;
import com.GuideAPP_AKS.SecondSubHeading.malayalam.SecondSubMalayalamRepo;
import com.GuideAPP_AKS.img.secondSubHeading.ImgSubSecondRepo;
import com.GuideAPP_AKS.mainHeading.MainDTO;
import com.GuideAPP_AKS.mpFileData.mp3.secondSub.Mp3Data2Repo;
import com.GuideAPP_AKS.mpFileData.mp4.secondSub.Mp4Data2Repo;
import com.GuideAPP_AKS.util.AlphaNumeric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecondSubService {
    @Autowired
    private SecondSubEnglishRepo secondSubEnglishRepo;
    @Autowired
    private SecondSubMalayalamRepo secondSubMalayalamRepo;
    @Autowired
    private AlphaNumeric alphaNumeric;
    @Autowired
    private ImgSubSecondRepo imgSubSecondRepo;
    @Autowired
    private Mp3Data2Repo mp3Data2Repo;
    @Autowired
    private Mp4Data2Repo mp4Data2Repo;
    @Autowired
    private CommonIdSsRepo commonIdSsRepo;

    public ResponseEntity<?> addSubDataEnglish(String uId, MainDTO mainDTO) {
        try {
            Optional<SecondSubEnglish> existingTitle = secondSubEnglishRepo.findBytitle(mainDTO.getTitle());
            if (existingTitle.isPresent()){
                String titleData = mainDTO.getTitle();
                return new ResponseEntity<>(titleData+" is already exist in the database",HttpStatus.CONFLICT);
            }
            String randomId = alphaNumeric.generateRandomNumber();
            SecondSubEnglish secondSubEnglish = new SecondSubEnglish();
            secondSubEnglish.setSsUid(randomId);
            secondSubEnglish.setFsUid(uId);
            secondSubEnglish.setTitle(mainDTO.getTitle());
            secondSubEnglish.setDescription(mainDTO.getDescription());
            secondSubEnglish.setRef(mainDTO.getReferenceURL());
            secondSubEnglishRepo.save(secondSubEnglish);
            return new ResponseEntity<>(secondSubEnglish,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> addSubDataMalayalam(String uId, MainDTO mainDTO) {
        try {
            Optional<SecondSubMalayalam> existingTitle = secondSubMalayalamRepo.findBytitle(mainDTO.getTitle());
            if (existingTitle.isPresent()){
                String titleData = mainDTO.getTitle();
                return new ResponseEntity<>(titleData+" is already exist in the database",HttpStatus.CONFLICT);
            }
            String randomId = alphaNumeric.generateRandomNumber();
            SecondSubMalayalam secondSubMalayalam = new SecondSubMalayalam();
            secondSubMalayalam.setSsUid(randomId);
            secondSubMalayalam.setFsUid(uId);
            secondSubMalayalam.setTitle(mainDTO.getTitle());
            secondSubMalayalam.setDescription(mainDTO.getDescription());
            secondSubMalayalam.setRef(mainDTO.getReferenceURL());
            secondSubMalayalamRepo.save(secondSubMalayalam);
            return new ResponseEntity<>(secondSubMalayalam, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> generateCommonSs(String englishId, String malId) {
        CommonIdSs commonIdSs = new CommonIdSs();
        commonIdSs.setSsEngId(englishId);
        commonIdSs.setSsMalId(malId);
        commonIdSs.setSsCommonId(alphaNumeric.generateRandomNumber());
        commonIdSsRepo.save(commonIdSs);
        return new ResponseEntity<>(commonIdSs,HttpStatus.OK);
    }
}
