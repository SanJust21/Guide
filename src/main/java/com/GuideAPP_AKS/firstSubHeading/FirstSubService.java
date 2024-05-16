package com.GuideAPP_AKS.firstSubHeading;

import com.GuideAPP_AKS.firstSubHeading.english.FirstSubEnglish;
import com.GuideAPP_AKS.firstSubHeading.english.FirstSubEnglishRepo;
import com.GuideAPP_AKS.firstSubHeading.malayalam.FirstSubMalayalam;
import com.GuideAPP_AKS.firstSubHeading.malayalam.FirstSubMalayalamRepo;
import com.GuideAPP_AKS.img.firstSubHeading.ImgSubFirst;
import com.GuideAPP_AKS.img.firstSubHeading.ImgSubFirstRepo;
import com.GuideAPP_AKS.img.mainHeading.ImgData;
import com.GuideAPP_AKS.mainHeading.CombinedData;
import com.GuideAPP_AKS.mainHeading.MainDTO;
import com.GuideAPP_AKS.mainHeading.mainEng.MainTitleEng;
import com.GuideAPP_AKS.mpFileData.mp3.firstSub.Mp3Data1;
import com.GuideAPP_AKS.mpFileData.mp3.firstSub.Mp3Data1Repo;
import com.GuideAPP_AKS.mpFileData.mp3.mainHeading.Mp3Data;
import com.GuideAPP_AKS.mpFileData.mp4.firstSub.Mp4Data1;
import com.GuideAPP_AKS.mpFileData.mp4.firstSub.Mp4Data1Repo;
import com.GuideAPP_AKS.mpFileData.mp4.mainHeading.Mp4Data;
import com.GuideAPP_AKS.util.AlphaNumeric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

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

    public ResponseEntity<?> addSubDataMalayalam(String uId, MainDTO mainDTO) {
        try {
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
}
