package com.GuideAPP_AKS.SecondSubHeading;

import com.GuideAPP_AKS.QR.CommonIdQRCode;
import com.GuideAPP_AKS.SecondSubHeading.commonId.CommonIdSs;
import com.GuideAPP_AKS.SecondSubHeading.commonId.CommonIdSsRepo;
import com.GuideAPP_AKS.SecondSubHeading.english.SecondSubEnglish;
import com.GuideAPP_AKS.SecondSubHeading.english.SecondSubEnglishRepo;
import com.GuideAPP_AKS.SecondSubHeading.malayalam.SecondSubMalayalam;
import com.GuideAPP_AKS.SecondSubHeading.malayalam.SecondSubMalayalamRepo;
import com.GuideAPP_AKS.firstSubHeading.CombinedDataSub;
import com.GuideAPP_AKS.firstSubHeading.FScommonId.CommonIdFs;
import com.GuideAPP_AKS.firstSubHeading.english.FirstSubEnglish;
import com.GuideAPP_AKS.img.firstSubHeading.ImgSubFirst;
import com.GuideAPP_AKS.img.mainHeading.ImgData;
import com.GuideAPP_AKS.img.secondSubHeading.ImgSubSecond;
import com.GuideAPP_AKS.img.secondSubHeading.ImgSubSecondRepo;
import com.GuideAPP_AKS.mainHeading.CombinedData;
import com.GuideAPP_AKS.mainHeading.MainDTO;
import com.GuideAPP_AKS.mainHeading.mainEng.MainTitleEng;
import com.GuideAPP_AKS.mpFileData.mp3.firstSub.Mp3Data1;
import com.GuideAPP_AKS.mpFileData.mp3.mainHeading.Mp3Data;
import com.GuideAPP_AKS.mpFileData.mp3.secondSub.Mp3Data2;
import com.GuideAPP_AKS.mpFileData.mp3.secondSub.Mp3Data2Repo;
import com.GuideAPP_AKS.mpFileData.mp4.firstSub.Mp4Data1;
import com.GuideAPP_AKS.mpFileData.mp4.mainHeading.Mp4Data;
import com.GuideAPP_AKS.mpFileData.mp4.secondSub.Mp4Data2;
import com.GuideAPP_AKS.mpFileData.mp4.secondSub.Mp4Data2Repo;
import com.GuideAPP_AKS.util.AlphaNumeric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

    public ResponseEntity<List<CombinedDataSubSub>> getCombinedList(String ssCommonId) {
        try {
            CommonIdSs commonIdSs = commonIdSsRepo.findByssCommonId(ssCommonId);
            if (commonIdSs != null) {
                List<SecondSubEnglish> secondSubEnglishList = secondSubEnglishRepo.findBySsUid(commonIdSs.getSsEngId());
                secondSubEnglishList.sort(Comparator.comparing(SecondSubEnglish::getId));
                List<CombinedDataSubSub> combinedDataSubSubList = new ArrayList<>();

                secondSubEnglishList.forEach(secondSubEnglish -> {
                    CombinedDataSubSub combinedDataSubSub = new CombinedDataSubSub();
                    // Set details of SecondSubEnglish
                    combinedDataSubSub.setTitle(secondSubEnglish.getTitle());
                    combinedDataSubSub.setDescription(secondSubEnglish.getDescription());
                    combinedDataSubSub.setReferenceUrl(secondSubEnglish.getRef());
                    combinedDataSubSub.setuId(secondSubEnglish.getSsUid());
                    combinedDataSubSub.setmUid(secondSubEnglish.getFsUid());

                    CommonIdSs commonIdSsEng = commonIdSsRepo.findBySsEngId(secondSubEnglish.getSsUid());
                    if (commonIdSsEng != null){
                        combinedDataSubSub.setSsCommonId(commonIdSsEng.getSsCommonId());
                        combinedDataSubSub.setSsEngId(commonIdSsEng.getSsEngId());
                        combinedDataSubSub.setSsMalId(commonIdSsEng.getSsMalId());
                    }

                    // Fetching images for SecondSubEnglish
                    List<ImgSubSecond> imgSubSecondList = imgSubSecondRepo.findByengId(secondSubEnglish.getSsUid());
                    imgSubSecondList.sort(Comparator.comparing(ImgSubSecond::getImgID));
                    combinedDataSubSub.setImgData2List(imgSubSecondList);
                    // Fetching audio for SecondSubEnglish
                    List<Mp3Data2> mp3Data2List = mp3Data2Repo.findBydtId(secondSubEnglish.getSsUid());
                    mp3Data2List.sort(Comparator.comparing(Mp3Data2::getId));
                    combinedDataSubSub.setMp3Data2List(mp3Data2List);
                    // Fetching video for SecondSubEnglish
                    List<Mp4Data2> mp4Data2List = mp4Data2Repo.findBydtId(secondSubEnglish.getSsUid());
                    mp4Data2List.sort(Comparator.comparing(Mp4Data2::getId));
                    combinedDataSubSub.setMp4Data2List(mp4Data2List);

                    combinedDataSubSubList.add(combinedDataSubSub);
                });

                return new ResponseEntity<>(combinedDataSubSubList, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<CombinedDataSubSub>> getCombinedListMal(String ssCommonId) {
        try {

            CommonIdSs commonIdSs = commonIdSsRepo.findByssCommonId(ssCommonId);
            if (commonIdSs != null) {
                List<SecondSubMalayalam> secondSubMalayalamList = secondSubMalayalamRepo.findBySsUid(commonIdSs.getSsMalId());
                secondSubMalayalamList.sort(Comparator.comparing(SecondSubMalayalam::getId));
                List<CombinedDataSubSub> combinedDataSubSubList = new ArrayList<>();

                secondSubMalayalamList.forEach(secondSubMalayalam -> {
                    CombinedDataSubSub combinedDataSubSub = new CombinedDataSubSub();
                    // Set details of SecondSubEnglish
                    combinedDataSubSub.setTitle(secondSubMalayalam.getTitle());
                    combinedDataSubSub.setDescription(secondSubMalayalam.getDescription());
                    combinedDataSubSub.setReferenceUrl(secondSubMalayalam.getRef());
                    combinedDataSubSub.setuId(secondSubMalayalam.getSsUid());
                    combinedDataSubSub.setmUid(secondSubMalayalam.getFsUid());

                    CommonIdSs commonIdSsEng = commonIdSsRepo.findBySsMalId(secondSubMalayalam.getSsUid());
                    if (commonIdSsEng != null){
                        combinedDataSubSub.setSsCommonId(commonIdSsEng.getSsCommonId());
                        combinedDataSubSub.setSsEngId(commonIdSsEng.getSsEngId());
                        combinedDataSubSub.setSsMalId(commonIdSsEng.getSsMalId());
                    }

                    // Fetching images for SecondSubEnglish
                    List<ImgSubSecond> imgSubSecondList = imgSubSecondRepo.findBymalId(secondSubMalayalam.getSsUid());
                    imgSubSecondList.sort(Comparator.comparing(ImgSubSecond::getImgID));
                    combinedDataSubSub.setImgData2List(imgSubSecondList);
                    // Fetching audio for SecondSubEnglish
                    List<Mp3Data2> mp3Data2List = mp3Data2Repo.findBydtId(secondSubMalayalam.getSsUid());
                    mp3Data2List.sort(Comparator.comparing(Mp3Data2::getId));
                    combinedDataSubSub.setMp3Data2List(mp3Data2List);
                    // Fetching video for SecondSubEnglish
                    List<Mp4Data2> mp4Data2List = mp4Data2Repo.findBydtId(secondSubMalayalam.getSsUid());
                    mp4Data2List.sort(Comparator.comparing(Mp4Data2::getId));
                    combinedDataSubSub.setMp4Data2List(mp4Data2List);

                    combinedDataSubSubList.add(combinedDataSubSub);
                });

                return new ResponseEntity<>(combinedDataSubSubList, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
