package com.GuideAPP_AKS.SecondSubHeading;

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

    public ResponseEntity<?> addSubDataEnglish(String uId, MainDTO mainDTO) {
        try {
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



//    public ResponseEntity<List<com.GuideAPP_AKS.SecondSubHeading.CombinedDataSub>> getCombinedListEng() {
//        try {
//            List<com.GuideAPP_AKS.SecondSubHeading.CombinedDataSub> combinedData = new ArrayList<>();
//            List<SecondSubEnglish> secondSubEnglishes = secondSubEnglishRepo.findAll();
//            for (SecondSubEnglish secondSubEnglish : secondSubEnglishes){
//                com.GuideAPP_AKS.SecondSubHeading.CombinedDataSub combinedData1 = new com.GuideAPP_AKS.SecondSubHeading.CombinedDataSub();
//                combinedData1.setTitle(secondSubEnglish.getTitle());
//                combinedData1.setDescription(secondSubEnglish.getDescription());
//                combinedData1.setReferenceUrl(secondSubEnglish.getRef());
//                combinedData1.setuId(secondSubEnglish.getSsUid());
//                combinedData1.setmUid(secondSubEnglish.getFsUid());
//
//                List<ImgSubSecond> imgData =imgSubSecondRepo.findByengId(secondSubEnglish.getFsUid());
//                combinedData1.setImgDataList(imgData);
//
//                List<Mp3Data2> mp3Data = mp3Data2Repo.findBydtId(secondSubEnglish.getSsUid());
//                combinedData1.setMp3DataList(mp3Data);
//
//                List<Mp4Data2> mp4Data = mp4Data2Repo.findBydtId(secondSubEnglish.getSsUid());
//                combinedData1.setMp4DataList(mp4Data);
//
//                combinedData.add(combinedData1);
//            }
//            return new ResponseEntity<>(combinedData,HttpStatus.OK);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    public ResponseEntity<List<com.GuideAPP_AKS.firstSubHeading.CombinedDataSub>> getCombinedListMal() {
//        try {
//            List<com.GuideAPP_AKS.firstSubHeading.CombinedDataSub> combinedData = new ArrayList<>();
//            List<FirstSubMalayalam> firstSubMalayalams = firstSubMalayalamRepo.findAll();
//            for (FirstSubMalayalam firstSubMalayalam :firstSubMalayalams){
//                com.GuideAPP_AKS.firstSubHeading.CombinedDataSub combinedData1 = new CombinedDataSub();
//                combinedData1.setTitle(firstSubMalayalam.getTitle());
//                combinedData1.setDescription(firstSubMalayalam.getDescription());
//                combinedData1.setReferenceUrl(firstSubMalayalam.getRef());
//                combinedData1.setmUid(firstSubMalayalam.getMainUid());
//                combinedData1.setuId(firstSubMalayalam.getFsUid());
//
//                List<ImgSubFirst> imgData =imgSubFirstRepo.findBymalId(firstSubMalayalam.getFsUid());
//                combinedData1.setImgDataList(imgData);
//
//                List<Mp3Data1> mp3Data = mp3Data1Repo.findBydtId(firstSubMalayalam.getFsUid());
//                combinedData1.setMp3DataList(mp3Data);
//
//                List<Mp4Data1> mp4Data = mp4Data1Repo.findBydtId(firstSubMalayalam.getFsUid());
//                combinedData1.setMp4DataList(mp4Data);
//
//                combinedData.add(combinedData1);
//            }
//            return new ResponseEntity<>(combinedData,HttpStatus.OK);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
