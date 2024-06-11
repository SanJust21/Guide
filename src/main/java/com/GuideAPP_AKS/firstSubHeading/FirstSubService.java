package com.GuideAPP_AKS.firstSubHeading;

import com.GuideAPP_AKS.SecondSubHeading.CombinedDataSubSub;
import com.GuideAPP_AKS.SecondSubHeading.commonId.CommonIdSs;
import com.GuideAPP_AKS.SecondSubHeading.commonId.CommonIdSsRepo;
import com.GuideAPP_AKS.SecondSubHeading.english.SecondSubEnglish;
import com.GuideAPP_AKS.SecondSubHeading.english.SecondSubEnglishRepo;
import com.GuideAPP_AKS.SecondSubHeading.malayalam.SecondSubMalayalam;
import com.GuideAPP_AKS.SecondSubHeading.malayalam.SecondSubMalayalamRepo;
import com.GuideAPP_AKS.firstSubHeading.FScommonId.CommonIdFs;
import com.GuideAPP_AKS.firstSubHeading.FScommonId.FsCommonIdRepo;
import com.GuideAPP_AKS.firstSubHeading.english.FirstSubEnglish;
import com.GuideAPP_AKS.firstSubHeading.english.FirstSubEnglishRepo;
import com.GuideAPP_AKS.firstSubHeading.malayalam.FirstSubMalayalam;
import com.GuideAPP_AKS.firstSubHeading.malayalam.FirstSubMalayalamRepo;
import com.GuideAPP_AKS.img.firstSubHeading.ImgSubFirst;
import com.GuideAPP_AKS.img.firstSubHeading.ImgSubFirstRepo;
import com.GuideAPP_AKS.img.mainHeading.ImgData;
import com.GuideAPP_AKS.img.secondSubHeading.ImgSubSecond;
import com.GuideAPP_AKS.img.secondSubHeading.ImgSubSecondRepo;
import com.GuideAPP_AKS.mainHeading.CombinedData;
import com.GuideAPP_AKS.mainHeading.MainDTO;
import com.GuideAPP_AKS.mainHeading.mainEng.MainTitleEng;
import com.GuideAPP_AKS.mpFileData.mp3.firstSub.Mp3Data1;
import com.GuideAPP_AKS.mpFileData.mp3.firstSub.Mp3Data1Repo;
import com.GuideAPP_AKS.mpFileData.mp3.mainHeading.Mp3Data;
import com.GuideAPP_AKS.mpFileData.mp3.secondSub.Mp3Data2;
import com.GuideAPP_AKS.mpFileData.mp3.secondSub.Mp3Data2Repo;
import com.GuideAPP_AKS.mpFileData.mp4.firstSub.Mp4Data1;
import com.GuideAPP_AKS.mpFileData.mp4.firstSub.Mp4Data1Repo;
import com.GuideAPP_AKS.mpFileData.mp4.mainHeading.Mp4Data;
import com.GuideAPP_AKS.mpFileData.mp4.secondSub.Mp4Data2;
import com.GuideAPP_AKS.mpFileData.mp4.secondSub.Mp4Data2Repo;
import com.GuideAPP_AKS.util.AlphaNumeric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FirstSubService {

    @Autowired
    private FirstSubEnglishRepo firstSubEnglishRepo;
    @Autowired
    private FirstSubMalayalamRepo firstSubMalayalamRepo;
    @Autowired
    private AlphaNumeric alphaNumeric;
    @Autowired
    private ImgSubFirstRepo imgSubFirstRepo;
    @Autowired
    private Mp3Data1Repo mp3Data1Repo;
    @Autowired
    private Mp4Data1Repo mp4Data1Repo;
    @Autowired
    private Mp3Data2Repo mp3Data2Repo;

    @Autowired
    private Mp4Data2Repo mp4Data2Repo;
    @Autowired
    private FsCommonIdRepo fsCommonIdRepo;

    @Autowired
    private ImgSubSecondRepo imgSubSecondRepo;

    @Autowired
    private CommonIdSsRepo commonIdSsRepo;

    @Autowired
    private SecondSubEnglishRepo secondSubEnglishRepo;
    @Autowired
    private SecondSubMalayalamRepo secondSubMalayalamRepo;

    public ResponseEntity<?> addSubDataMalayalam(String uId, MainDTO mainDTO) {
        try {

            Optional<FirstSubMalayalam> existingTitle = firstSubMalayalamRepo.findBytitle(mainDTO.getTitle());
            if (existingTitle.isPresent()){
                String titleData = mainDTO.getTitle();
                return new ResponseEntity<>(titleData+" is already exist in the database",HttpStatus.CONFLICT);
            }

            String randomId = alphaNumeric.generateRandomNumber();
            FirstSubMalayalam firstSubMalayalam = new FirstSubMalayalam();
            firstSubMalayalam.setFsUid(randomId);
            firstSubMalayalam.setMainUid(uId);
            firstSubMalayalam.setTitle(mainDTO.getTitle());
            firstSubMalayalam.setDescription(mainDTO.getDescription());
            firstSubMalayalam.setRef(mainDTO.getReferenceURL());
            firstSubMalayalamRepo.save(firstSubMalayalam);
            return new ResponseEntity<>(firstSubMalayalam,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> addSubDataEnglish(String uId, MainDTO mainDTO) {
        try {

            Optional<FirstSubEnglish> existingTitle = firstSubEnglishRepo.findBytitle(mainDTO.getTitle());
            if (existingTitle.isPresent()){
                String titleData = mainDTO.getTitle();
                return new ResponseEntity<>(titleData+" is already exist in the database",HttpStatus.CONFLICT);
            }

            String randomId = alphaNumeric.generateRandomNumber();
            FirstSubEnglish firstSubEnglish = new FirstSubEnglish();
            firstSubEnglish.setFsUid(randomId);
            firstSubEnglish.setMainUid(uId);
            firstSubEnglish.setTitle(mainDTO.getTitle());
            firstSubEnglish.setDescription(mainDTO.getDescription());
            firstSubEnglish.setRef(mainDTO.getReferenceURL());
            firstSubEnglishRepo.save(firstSubEnglish);
            return new ResponseEntity<>(firstSubEnglish,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<CombinedDataSub>> getCombinedListEng() {
        try {
            List<CombinedDataSub> combinedData = new ArrayList<>();
            List<FirstSubEnglish> firstSubEnglishes = firstSubEnglishRepo.findAll();
            for (FirstSubEnglish firstSubEnglish : firstSubEnglishes){
                CombinedDataSub combinedData1 = new CombinedDataSub();
                combinedData1.setTitle(firstSubEnglish.getTitle());
                combinedData1.setDescription(firstSubEnglish.getDescription());
                combinedData1.setReferenceUrl(firstSubEnglish.getRef());
                combinedData1.setuId(firstSubEnglish.getFsUid());
                combinedData1.setmUid(firstSubEnglish.getMainUid());

                List<ImgSubFirst> imgData =imgSubFirstRepo.findByengId(firstSubEnglish.getFsUid());
                combinedData1.setImgDataList(imgData);

                List<Mp3Data1> mp3Data = mp3Data1Repo.findBydtId(firstSubEnglish.getFsUid());
                combinedData1.setMp3DataList(mp3Data);

                List<Mp4Data1> mp4Data = mp4Data1Repo.findBydtId(firstSubEnglish.getFsUid());
                combinedData1.setMp4DataList(mp4Data);

                combinedData.add(combinedData1);
            }
            return new ResponseEntity<>(combinedData,HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<CombinedDataSub>> getCombinedListMal() {
        try {
            List<CombinedDataSub> combinedData = new ArrayList<>();
            List<FirstSubMalayalam> firstSubMalayalams = firstSubMalayalamRepo.findAll();
            for (FirstSubMalayalam firstSubMalayalam :firstSubMalayalams){
                CombinedDataSub combinedData1 = new CombinedDataSub();
                combinedData1.setTitle(firstSubMalayalam.getTitle());
                combinedData1.setDescription(firstSubMalayalam.getDescription());
                combinedData1.setReferenceUrl(firstSubMalayalam.getRef());
                combinedData1.setmUid(firstSubMalayalam.getMainUid());
                combinedData1.setuId(firstSubMalayalam.getFsUid());

                List<ImgSubFirst> imgData =imgSubFirstRepo.findBymalId(firstSubMalayalam.getFsUid());
                combinedData1.setImgDataList(imgData);

                List<Mp3Data1> mp3Data = mp3Data1Repo.findBydtId(firstSubMalayalam.getFsUid());
                combinedData1.setMp3DataList(mp3Data);

                List<Mp4Data1> mp4Data = mp4Data1Repo.findBydtId(firstSubMalayalam.getFsUid());
                combinedData1.setMp4DataList(mp4Data);

                combinedData.add(combinedData1);
            }
            return new ResponseEntity<>(combinedData,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> generateCommonIdFs(String engId, String malId) {
        try {
            CommonIdFs commonIdFs = new CommonIdFs();
            commonIdFs.setFsMalId(malId);
            commonIdFs.setFsEngId(engId);
            String genId = alphaNumeric.generateRandomNumber();
            commonIdFs.setFsCommonId(genId);
            fsCommonIdRepo.save(commonIdFs);
            return new ResponseEntity<>(commonIdFs,HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> getFirstSubCombinedListEng(String id) {
        try {
            List<CombinedDataSub> combinedDataSubList = new ArrayList<>();
            CommonIdFs commonIdFs = fsCommonIdRepo.findByfsCommonId(id);
            String fsMalId = commonIdFs.getFsMalId();
            if (commonIdFs!=null){
                Optional<FirstSubEnglish> firstSubEnglish = firstSubEnglishRepo.findByfsUid(commonIdFs.getFsEngId());
                if (firstSubEnglish.isPresent()){
                    FirstSubEnglish firstSubEnglish1 = firstSubEnglish.get();
                    CombinedDataSub combinedDataSub = new CombinedDataSub();
                    combinedDataSub.setFsEngId(firstSubEnglish1.getFsUid());
                    combinedDataSub.setTitle(firstSubEnglish1.getTitle());
                    combinedDataSub.setDescription(firstSubEnglish1.getDescription());
                    combinedDataSub.setReferenceUrl(firstSubEnglish1.getRef());
                    combinedDataSub.setFsMalId(fsMalId);
                    combinedDataSub.setFsCommonId(id);

                    List<ImgSubFirst> imgSubFirsts = imgSubFirstRepo.findByEngId(firstSubEnglish1.getFsUid());
                    combinedDataSub.setImgDataList(imgSubFirsts);

                    List<Mp3Data1> mp3Data1List = mp3Data1Repo.findBydtId(firstSubEnglish1.getFsUid());
                    combinedDataSub.setMp3DataList(mp3Data1List);

                    List<Mp4Data1> mp4Data1List = mp4Data1Repo.findBydtId(firstSubEnglish1.getFsUid());
                    combinedDataSub.setMp4DataList(mp4Data1List);

                    List<SecondSubEnglish> secondSubEnglishList = secondSubEnglishRepo.findByfsUid(firstSubEnglish1.getFsUid());
                    List<CombinedDataSubSub> combinedDataSubSubList = new ArrayList<>();

                    secondSubEnglishList.forEach(secondSubEnglish -> {
                        CombinedDataSubSub combinedDataSubSub = new CombinedDataSubSub();
                        // Set details of SecondSubEnglish
                        combinedDataSubSub.setTitle(secondSubEnglish.getTitle());
                        combinedDataSubSub.setDescription(secondSubEnglish.getDescription());
                        combinedDataSubSub.setReferenceUrl(secondSubEnglish.getRef());
                        combinedDataSubSub.setuId(secondSubEnglish.getSsUid());
                        combinedDataSubSub.setmUid(secondSubEnglish.getFsUid());

                        Optional<CommonIdSs> commonIdSs = commonIdSsRepo.findByssEngId(secondSubEnglish.getSsUid());
                        if (commonIdSs.isPresent()){
                            CommonIdSs commonIdSs1 = commonIdSs.get();
                            combinedDataSubSub.setSsCommonId(commonIdSs1.getSsCommonId());
                            combinedDataSubSub.setSsEngId(commonIdSs1.getSsEngId());
                            combinedDataSubSub.setSsMalId(commonIdSs1.getSsMalId());
                        }
                        // Fetching images for SecondSubEnglish
                        List<ImgSubSecond> imgSubSecondList = imgSubSecondRepo.findByengId(secondSubEnglish.getSsUid());
                        combinedDataSubSub.setImgData2List(imgSubSecondList);
                        // Fetching audio for SecondSubEnglish
                        List<Mp3Data2> mp3Data2List = mp3Data2Repo.findBydtId(secondSubEnglish.getSsUid());
                        combinedDataSubSub.setMp3Data2List(mp3Data2List);
                        // Fetching video for SecondSubEnglish
                        List<Mp4Data2> mp4Data2List = mp4Data2Repo.findBydtId(secondSubEnglish.getSsUid());
                        combinedDataSubSub.setMp4Data2List(mp4Data2List);

                        combinedDataSubSubList.add(combinedDataSubSub);

                    });
                    combinedDataSub.setCombinedDataSubSubList(combinedDataSubSubList);

                    combinedDataSubList.add(combinedDataSub);
                }
                return new ResponseEntity<>(combinedDataSubList,HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> getFirstSubCombinedListMal(String id) {
        try {
            List<CombinedDataSub> combinedDataSubList = new ArrayList<>();
            CommonIdFs commonIdFs = fsCommonIdRepo.findByfsCommonId(id);
            String fsEngId = commonIdFs.getFsEngId();
            if (commonIdFs!=null){
                Optional<FirstSubMalayalam> firstSubMalayalam = firstSubMalayalamRepo.findByfsUid(commonIdFs.getFsMalId());
                if (firstSubMalayalam.isPresent()){
                    FirstSubMalayalam firstSubMalayalam1 =firstSubMalayalam.get();
                    CombinedDataSub combinedDataSub = new CombinedDataSub();
                    combinedDataSub.setFsMalId(firstSubMalayalam1.getFsUid());
                    combinedDataSub.setTitle(firstSubMalayalam1.getTitle());
                    combinedDataSub.setDescription(firstSubMalayalam1.getDescription());
                    combinedDataSub.setReferenceUrl(firstSubMalayalam1.getRef());
                    combinedDataSub.setFsEngId(fsEngId);
                    combinedDataSub.setFsCommonId(commonIdFs.getFsCommonId());
                    combinedDataSub.setFsMalId(commonIdFs.getFsMalId());

                    List<ImgSubFirst> imgSubFirsts = imgSubFirstRepo.findBymalId(firstSubMalayalam1.getFsUid());
                    combinedDataSub.setImgDataList(imgSubFirsts);

                    List<Mp3Data1> mp3Data1List = mp3Data1Repo.findBydtId(firstSubMalayalam1.getFsUid());
                    combinedDataSub.setMp3DataList(mp3Data1List);

                    List<Mp4Data1> mp4Data1List = mp4Data1Repo.findBydtId(firstSubMalayalam1.getFsUid());
                    combinedDataSub.setMp4DataList(mp4Data1List);

                    List<SecondSubMalayalam> secondSubMalayalamList = secondSubMalayalamRepo.findByfsUid(firstSubMalayalam1.getFsUid());
                    List<CombinedDataSubSub> combinedDataSubSubList = new ArrayList<>();
                    secondSubMalayalamList.forEach(secondSubMalayalam -> {
                        CombinedDataSubSub combinedDataSubSub = new CombinedDataSubSub();
                        // Set details of SecondSubMalayalam
                        combinedDataSubSub.setTitle(secondSubMalayalam.getTitle());
                        combinedDataSubSub.setDescription(secondSubMalayalam.getDescription());
                        combinedDataSubSub.setReferenceUrl(secondSubMalayalam.getRef());
                        combinedDataSubSub.setuId(secondSubMalayalam.getSsUid());
                        combinedDataSubSub.setmUid(secondSubMalayalam.getFsUid());

                        Optional<CommonIdSs> commonIdSs = commonIdSsRepo.findByssMalId(secondSubMalayalam.getSsUid());
                        if (commonIdSs.isPresent()){
                            CommonIdSs commonIdSs1 = commonIdSs.get();
                            combinedDataSubSub.setSsCommonId(commonIdSs1.getSsCommonId());
                            combinedDataSubSub.setSsEngId(commonIdSs1.getSsEngId());
                            combinedDataSubSub.setSsMalId(commonIdSs1.getSsMalId());
                        }
                        // Fetching images for SecondSubMalayalam
                        List<ImgSubSecond> imgSubSecondList = imgSubSecondRepo.findBymalId(secondSubMalayalam.getSsUid());
                        combinedDataSubSub.setImgData2List(imgSubSecondList);
                        // Fetching audio for SecondSubMalayalam
                        List<Mp3Data2> mp3Data2List = mp3Data2Repo.findBydtId(secondSubMalayalam.getSsUid());
                        combinedDataSubSub.setMp3Data2List(mp3Data2List);
                        // Fetching video for SecondSubMalayalam
                        List<Mp4Data2> mp4Data2List = mp4Data2Repo.findBydtId(secondSubMalayalam.getSsUid());
                        combinedDataSubSub.setMp4Data2List(mp4Data2List);
                        combinedDataSubSubList.add(combinedDataSubSub);
                    });
                   // combinedDataSub.add(combinedDataSubSubList);
                    combinedDataSub.setCombinedDataSubSubList(combinedDataSubSubList);
                    combinedDataSubList.add(combinedDataSub);
                }
                return new ResponseEntity<>(combinedDataSubList,HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
