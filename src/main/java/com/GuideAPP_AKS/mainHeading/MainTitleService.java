package com.GuideAPP_AKS.mainHeading;

import com.GuideAPP_AKS.firstSubHeading.english.FirstSubEnglish;
import com.GuideAPP_AKS.firstSubHeading.FirstSubEngData;
import com.GuideAPP_AKS.firstSubHeading.english.FirstSubEnglishRepo;
import com.GuideAPP_AKS.firstSubHeading.malayalam.FirstSubMalayalam;
import com.GuideAPP_AKS.firstSubHeading.malayalam.FirstSubMalayalamRepo;
import com.GuideAPP_AKS.img.firstSubHeading.ImgSubFirst;
import com.GuideAPP_AKS.img.firstSubHeading.ImgSubFirstRepo;
import com.GuideAPP_AKS.img.mainHeading.ImgData;
import com.GuideAPP_AKS.img.mainHeading.ImgRepo;
import com.GuideAPP_AKS.mainHeading.mainEng.MainTitleEng;
import com.GuideAPP_AKS.mainHeading.mainEng.MainTitleEngRepo;
import com.GuideAPP_AKS.mainHeading.mainMal.MainTitleMal;
import com.GuideAPP_AKS.mainHeading.mainMal.MainTitleMalRepo;
import com.GuideAPP_AKS.mpFileData.mp3.mainHeading.Mp3Data;
import com.GuideAPP_AKS.mpFileData.mp3.mainHeading.Mp3Repo;
import com.GuideAPP_AKS.mpFileData.mp4.mainHeading.Mp4Data;
import com.GuideAPP_AKS.mpFileData.mp4.mainHeading.Mp4DataRepo;
import com.GuideAPP_AKS.util.AlphaNumeric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MainTitleService {
    @Autowired
    private MainTitleEngRepo mtEngRepo;
    @Autowired
    private MainTitleMalRepo mtMalRepo;
    @Autowired
    private AlphaNumeric alphaNumeric;
    @Autowired
    private ImgRepo imgRepo;

    @Autowired
    private Mp3Repo mp3Repo;
    @Autowired
    private Mp4DataRepo mp4DataRepo;

    @Autowired
    private FirstSubEnglishRepo firstSubEnglishRepo;

    @Autowired
    private FirstSubMalayalamRepo firstSubMalayalamRepo;

    @Autowired
    private ImgSubFirstRepo imgSubFirstRepo;


    public ResponseEntity<?> addMainTitleEng(MainDTO mainDTO) {
        try {
            String randomId = alphaNumeric.generateRandomNumber();
            MainTitleEng mtEng = new MainTitleEng();
            mtEng.setMEngUid(randomId);
            mtEng.setTitle(mainDTO.getTitle());
            mtEng.setDescription(mainDTO.getDescription());
            mtEng.setRef(mainDTO.getReferenceURL());
            mtEngRepo.save(mtEng);
            return new ResponseEntity<>(mtEng,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> addMainTitleMal(MainDTO mainDTO) {
        try {
            String randomId = alphaNumeric.generateRandomNumber();
            MainTitleMal mtMal = new MainTitleMal();
            mtMal.setMMalUid(randomId);
            mtMal.setTitle(mainDTO.getTitle());
            mtMal.setDescription(mainDTO.getDescription());
            mtMal.setRef(mainDTO.getReferenceURL());
            mtMalRepo.save(mtMal);
            return new ResponseEntity<>(mtMal,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public ResponseEntity<List<CombinedData>> getCombinedList() {
        try {
            List<CombinedData> combinedData = new ArrayList<>();
            List<MainTitleEng> mainTitleEngs = mtEngRepo.findAll();
            for (MainTitleEng mainTitleEng : mainTitleEngs){
                CombinedData combinedData1 = new CombinedData();
                combinedData1.setTitle(mainTitleEng.getTitle());
                combinedData1.setDescription(mainTitleEng.getDescription());
                combinedData1.setReferenceUrl(mainTitleEng.getRef());
                combinedData1.setuId(mainTitleEng.getMEngUid());

                List<ImgData> imgData =imgRepo.findByengId(mainTitleEng.getMEngUid());
                combinedData1.setImgDataList(imgData);

                List<Mp3Data> mp3Data = mp3Repo.findBydtId(mainTitleEng.getMEngUid());
                combinedData1.setMp3DataList(mp3Data);

                List<Mp4Data> mp4Data = mp4DataRepo.findBydtId(mainTitleEng.getMEngUid());
                combinedData1.setMp4DataList(mp4Data);

                combinedData.add(combinedData1);
            }
            return new ResponseEntity<>(combinedData,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<CombinedData>> getCombinedListMal() {
        try {
            List<CombinedData> combinedDataList = new ArrayList<>();
            List<MainTitleMal> mainTitleMals = mtMalRepo.findAll();
            for (MainTitleMal mainTitleMal : mainTitleMals){
                CombinedData combinedData = new CombinedData();
                combinedData.setTitle(mainTitleMal.getTitle());
                combinedData.setDescription(mainTitleMal.getDescription());
                combinedData.setReferenceUrl(mainTitleMal.getRef());
                combinedData.setuId(mainTitleMal.getMMalUid());

                List<ImgData> imgData =imgRepo.findBymalId(mainTitleMal.getMMalUid());
                combinedData.setImgDataList(imgData);

                List<Mp3Data> mp3Data = mp3Repo.findBydtId(mainTitleMal.getMMalUid());
                combinedData.setMp3DataList(mp3Data);

                List<Mp4Data> mp4Data = mp4DataRepo.findBydtId(mainTitleMal.getMMalUid());
                combinedData.setMp4DataList(mp4Data);

                combinedDataList.add(combinedData);

            }
            return new ResponseEntity<>(combinedDataList,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<CombinedData>> getCombinedList(String mainId) {
        try {
            List<CombinedData> combinedDataList = new ArrayList<>();
            Optional<MainTitleEng> mainTitleEngs = Optional.ofNullable(mtEngRepo.findBymEngUid(mainId));
            mainTitleEngs.ifPresent(mainTitleEng -> {
                CombinedData combinedData1 = new CombinedData();
                combinedData1.setTitle(mainTitleEng.getTitle());
                combinedData1.setDescription(mainTitleEng.getDescription());
                combinedData1.setReferenceUrl(mainTitleEng.getRef());
                combinedData1.setuId(mainTitleEng.getMEngUid());

                List<FirstSubEnglish> firstSubEnglishList = firstSubEnglishRepo.findByMainUid(mainId);
                List<FirstSubEngData> firstSubDataList = new ArrayList<>();
                firstSubEnglishList.forEach(firstSubEnglish -> {
                    FirstSubEngData firstSubData = new FirstSubEngData();
                    firstSubData.setFirstSubEnglish(firstSubEnglish);

                    // Fetching images for the current first subheading
                    List<ImgSubFirst> imgSubFirstList = imgSubFirstRepo.findByEngId(firstSubEnglish.getFsUid());
                    firstSubData.setImgSubFirstList(imgSubFirstList);

                    firstSubDataList.add(firstSubData);
                });

                combinedData1.setFirstSubEnglishList(firstSubDataList);

                List<ImgData> imgData =imgRepo.findByengId(mainTitleEng.getMEngUid());
                combinedData1.setImgDataList(imgData);

                List<Mp3Data> mp3Data = mp3Repo.findBydtId(mainTitleEng.getMEngUid());
                combinedData1.setMp3DataList(mp3Data);

                List<Mp4Data> mp4Data = mp4DataRepo.findBydtId(mainTitleEng.getMEngUid());
                combinedData1.setMp4DataList(mp4Data);

                combinedDataList.add(combinedData1);
            });
            return new ResponseEntity<>(combinedDataList,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<CombinedData>> getCombinedListMal(String mainId) {
        try {
            List<CombinedData> combinedDataList = new ArrayList<>();
            Optional<MainTitleMal> mainTitleMals = mtMalRepo.findBymMalUid(mainId);
            mainTitleMals.ifPresent(mainTitleMal -> {
                CombinedData combinedData = new CombinedData();
                combinedData.setTitle(mainTitleMal.getTitle());
                combinedData.setDescription(mainTitleMal.getDescription());
                combinedData.setReferenceUrl(mainTitleMal.getRef());
                combinedData.setuId(mainTitleMal.getMMalUid());

                List<ImgData> imgData =imgRepo.findBymalId(mainTitleMal.getMMalUid());
                combinedData.setImgDataList(imgData);

                List<Mp3Data> mp3Data = mp3Repo.findBydtId(mainTitleMal.getMMalUid());
                combinedData.setMp3DataList(mp3Data);

                List<Mp4Data> mp4Data = mp4DataRepo.findBydtId(mainTitleMal.getMMalUid());
                combinedData.setMp4DataList(mp4Data);

                combinedDataList.add(combinedData);

            });
            return new ResponseEntity<>(combinedDataList,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
