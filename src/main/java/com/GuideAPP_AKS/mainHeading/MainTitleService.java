package com.GuideAPP_AKS.mainHeading;

import com.GuideAPP_AKS.Language.DataType;
import com.GuideAPP_AKS.Language.DataTypeRepo;
import com.GuideAPP_AKS.QR.CommonIdQRCode;
import com.GuideAPP_AKS.QR.CommonIdQRCodeRepo;
import com.GuideAPP_AKS.SecondSubHeading.CombinedDataSubSub;
import com.GuideAPP_AKS.SecondSubHeading.commonId.CommonIdSs;
import com.GuideAPP_AKS.SecondSubHeading.commonId.CommonIdSsRepo;
import com.GuideAPP_AKS.SecondSubHeading.english.SecondSubEnglish;
import com.GuideAPP_AKS.SecondSubHeading.english.SecondSubEnglishRepo;
import com.GuideAPP_AKS.SecondSubHeading.malayalam.SecondSubMalayalam;
import com.GuideAPP_AKS.SecondSubHeading.malayalam.SecondSubMalayalamRepo;
import com.GuideAPP_AKS.firstSubHeading.CombinedDataSub;
import com.GuideAPP_AKS.firstSubHeading.FScommonId.CommonIdFs;
import com.GuideAPP_AKS.firstSubHeading.FScommonId.FsCommonIdRepo;
import com.GuideAPP_AKS.firstSubHeading.english.FirstSubEnglish;
import com.GuideAPP_AKS.firstSubHeading.english.FirstSubEnglishRepo;
import com.GuideAPP_AKS.firstSubHeading.malayalam.FirstSubMalayalam;
import com.GuideAPP_AKS.firstSubHeading.malayalam.FirstSubMalayalamRepo;
import com.GuideAPP_AKS.img.firstSubHeading.ImgSubFirst;
import com.GuideAPP_AKS.img.firstSubHeading.ImgSubFirstRepo;
import com.GuideAPP_AKS.img.mainHeading.ImgData;
import com.GuideAPP_AKS.img.mainHeading.ImgRepo;
import com.GuideAPP_AKS.img.secondSubHeading.ImgSubSecond;
import com.GuideAPP_AKS.img.secondSubHeading.ImgSubSecondRepo;
import com.GuideAPP_AKS.mainHeading.mainEng.MainTitleEng;
import com.GuideAPP_AKS.mainHeading.mainEng.MainTitleEngRepo;
import com.GuideAPP_AKS.mainHeading.mainMal.MainTitleMal;
import com.GuideAPP_AKS.mainHeading.mainMal.MainTitleMalRepo;
import com.GuideAPP_AKS.mpFileData.mp3.firstSub.Mp3Data1;
import com.GuideAPP_AKS.mpFileData.mp3.firstSub.Mp3Data1Repo;
import com.GuideAPP_AKS.mpFileData.mp3.mainHeading.Mp3Data;
import com.GuideAPP_AKS.mpFileData.mp3.mainHeading.Mp3Repo;
import com.GuideAPP_AKS.mpFileData.mp3.secondSub.Mp3Data2;
import com.GuideAPP_AKS.mpFileData.mp3.secondSub.Mp3Data2Repo;
import com.GuideAPP_AKS.mpFileData.mp4.firstSub.Mp4Data1;
import com.GuideAPP_AKS.mpFileData.mp4.firstSub.Mp4Data1Repo;
import com.GuideAPP_AKS.mpFileData.mp4.mainHeading.Mp4Data;
import com.GuideAPP_AKS.mpFileData.mp4.mainHeading.Mp4DataRepo;
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
import java.util.stream.Collectors;

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

    @Autowired
    private Mp3Data1Repo mp3Data1Repo;

    @Autowired
    private Mp4Data1Repo mp4Data1Repo;

    @Autowired
    private SecondSubEnglishRepo secondSubEnglishRepo;

    @Autowired
    private SecondSubMalayalamRepo secondSubMalayalamRepo;


    @Autowired
    private ImgSubSecondRepo imgSubSecondRepo;

    @Autowired
    private Mp3Data2Repo mp3Data2Repo;

    @Autowired
    private Mp4Data2Repo mp4Data2Repo;

    @Autowired
    private CommonIdQRCodeRepo commonIdQRCodeRepo;

    @Autowired
    private DataTypeRepo dataTypeRepo;

    @Autowired
    private FsCommonIdRepo fsCommonIdRepo;

    @Autowired
    private CommonIdSsRepo commonIdSsRepo;


    public ResponseEntity<?> addMainTitleEng(MainDTO mainDTO) {
        try {

            Optional<MainTitleEng> existingTitle = mtEngRepo.findBytitle(mainDTO.getTitle());
            if (existingTitle.isPresent()){
                String titleData = mainDTO.getTitle();
                return new ResponseEntity<>(titleData+" is already exist in the database",HttpStatus.CONFLICT);
            }

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

            Optional<MainTitleMal> existingTitle =mtMalRepo.findBytitle(mainDTO.getTitle());
            if (existingTitle.isPresent()){
                String titleData = mainDTO.getTitle();
                return new ResponseEntity<>(titleData+" is already exist in the database",HttpStatus.CONFLICT);
            }

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

            mainTitleEngs.sort(Comparator.comparing(MainTitleEng::getMEngId));

            for (MainTitleEng mainTitleEng : mainTitleEngs){
                CombinedData combinedData1 = new CombinedData();
                combinedData1.setTitle(mainTitleEng.getTitle());
                combinedData1.setDescription(mainTitleEng.getDescription());
                combinedData1.setReferenceUrl(mainTitleEng.getRef());
                combinedData1.setuId(mainTitleEng.getMEngUid());

                Optional<CommonIdQRCode> commonIdQRCode = commonIdQRCodeRepo.findByEngId(mainTitleEng.getMEngUid());
                if (commonIdQRCode.isPresent()) {
                    combinedData1.setCommonId(commonIdQRCode.get().getCommonId());
                    combinedData1.setQrCodeUrl(commonIdQRCode.get().getQrCodeUrl());
                    combinedData1.setEngId(commonIdQRCode.get().getEngId());
                    combinedData1.setMalId(commonIdQRCode.get().getMalId());
                }

                List<ImgData> imgData =imgRepo.findByengId(mainTitleEng.getMEngUid());
                imgData.sort(Comparator.comparing(ImgData::getImgID));
                combinedData1.setImgDataList(imgData);

                List<Mp3Data> mp3Data = mp3Repo.findBydtId(mainTitleEng.getMEngUid());
                mp3Data.sort(Comparator.comparing(Mp3Data::getId));
                combinedData1.setMp3DataList(mp3Data);

                List<Mp4Data> mp4Data = mp4DataRepo.findBydtId(mainTitleEng.getMEngUid());
                mp4Data.sort(Comparator.comparing((Mp4Data::getId)));
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

            mainTitleMals.sort(Comparator.comparing(MainTitleMal::getMMalId));

            for (MainTitleMal mainTitleMal : mainTitleMals){
                CombinedData combinedData = new CombinedData();
                combinedData.setTitle(mainTitleMal.getTitle());
                combinedData.setDescription(mainTitleMal.getDescription());
                combinedData.setReferenceUrl(mainTitleMal.getRef());
                combinedData.setuId(mainTitleMal.getMMalUid());

                Optional<CommonIdQRCode> commonIdQRCode = commonIdQRCodeRepo.findByMalId(mainTitleMal.getMMalUid());
                if (commonIdQRCode.isPresent()) {
                    combinedData.setCommonId(commonIdQRCode.get().getCommonId());
                    combinedData.setQrCodeUrl(commonIdQRCode.get().getQrCodeUrl());
                    combinedData.setEngId(commonIdQRCode.get().getEngId());
                    combinedData.setMalId(commonIdQRCode.get().getMalId());
                }

                List<ImgData> imgData =imgRepo.findBymalId(mainTitleMal.getMMalUid());
                imgData.sort(Comparator.comparing(ImgData::getImgID));
                combinedData.setImgDataList(imgData);

                List<Mp3Data> mp3Data = mp3Repo.findBydtId(mainTitleMal.getMMalUid());
                mp3Data.sort(Comparator.comparing(Mp3Data::getId));
                combinedData.setMp3DataList(mp3Data);

                List<Mp4Data> mp4Data = mp4DataRepo.findBydtId(mainTitleMal.getMMalUid());
                mp4Data.sort(Comparator.comparing((Mp4Data::getId)));
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

                Optional<CommonIdQRCode> commonIdQRCode = commonIdQRCodeRepo.findByEngId(mainTitleEng.getMEngUid());
                if (commonIdQRCode.isPresent()) {
                    combinedData1.setCommonId(commonIdQRCode.get().getCommonId());
                    combinedData1.setQrCodeUrl(commonIdQRCode.get().getQrCodeUrl());
                    combinedData1.setEngId(commonIdQRCode.get().getEngId());
                    combinedData1.setMalId(commonIdQRCode.get().getMalId());
                }

                List<FirstSubEnglish> firstSubEnglishList = firstSubEnglishRepo.findByMainUid(mainId);
                firstSubEnglishList.sort(Comparator.comparing(FirstSubEnglish::getId));
                List<CombinedDataSub> combinedDataSubList = new ArrayList<>();
                firstSubEnglishList.forEach(firstSubEnglish -> {
                    CombinedDataSub combinedDataSub = new CombinedDataSub();

                    // Set first subheading details
                    combinedDataSub.setTitle(firstSubEnglish.getTitle());
                    combinedDataSub.setDescription(firstSubEnglish.getDescription());
                    combinedDataSub.setReferenceUrl(firstSubEnglish.getRef());
                    combinedDataSub.setuId(firstSubEnglish.getFsUid());
                    combinedDataSub.setmUid(mainId);

                    CommonIdFs commonIdFs = fsCommonIdRepo.findByfsEngId(firstSubEnglish.getFsUid());
                    if (commonIdFs!=null){
                        combinedDataSub.setFsCommonId(commonIdFs.getFsCommonId());
                        combinedDataSub.setFsEngId(commonIdFs.getFsEngId());
                        combinedDataSub.setFsMalId(commonIdFs.getFsMalId());
                    }

                    // Fetching images for the current first subheading
                    List<ImgSubFirst> imgSubFirstList = imgSubFirstRepo.findByEngId(firstSubEnglish.getFsUid());
                    imgSubFirstList.sort(Comparator.comparing(ImgSubFirst::getImgID));
                    combinedDataSub.setImgDataList(imgSubFirstList);

                    // Fetching audio for the current first subheading
                    List<Mp3Data1> mp3Data1List = mp3Data1Repo.findBydtId(firstSubEnglish.getFsUid());
                    mp3Data1List.sort(Comparator.comparing(Mp3Data1::getId));
                    combinedDataSub.setMp3DataList(mp3Data1List);

                    // Fetching video for the current first subheading
                    List<Mp4Data1> mp4Data1List = mp4Data1Repo.findBydtId(firstSubEnglish.getFsUid());
                    mp4Data1List.sort(Comparator.comparing(Mp4Data1::getId));
                    combinedDataSub.setMp4DataList(mp4Data1List);

                    // Fetching data for SecondSubEnglish
                    List<SecondSubEnglish> secondSubEnglishList = secondSubEnglishRepo.findByfsUid(firstSubEnglish.getFsUid());
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

                        Optional<CommonIdSs> commonIdSs = commonIdSsRepo.findByssEngId(secondSubEnglish.getSsUid());
                        if (commonIdSs.isPresent()){
                            CommonIdSs commonIdSs1 = commonIdSs.get();
                            combinedDataSubSub.setSsCommonId(commonIdSs1.getSsCommonId());
                            combinedDataSubSub.setSsEngId(commonIdSs1.getSsEngId());
                            combinedDataSubSub.setSsMalId(commonIdSs1.getSsMalId());
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

                    combinedDataSub.setCombinedDataSubSubList(combinedDataSubSubList);

                    combinedDataSubList.add(combinedDataSub);
                });

                combinedData1.setCombinedDataSubList(combinedDataSubList);


                List<ImgData> imgData =imgRepo.findByengId(mainTitleEng.getMEngUid());
                imgData.sort(Comparator.comparing(ImgData::getImgID));
                combinedData1.setImgDataList(imgData);

                List<Mp3Data> mp3Data = mp3Repo.findBydtId(mainTitleEng.getMEngUid());
                mp3Data.sort(Comparator.comparing(Mp3Data::getId));
                combinedData1.setMp3DataList(mp3Data);

                List<Mp4Data> mp4Data = mp4DataRepo.findBydtId(mainTitleEng.getMEngUid());
                mp4Data.sort(Comparator.comparing((Mp4Data::getId)));
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

                Optional<CommonIdQRCode> commonIdQRCode = commonIdQRCodeRepo.findByMalId(mainTitleMal.getMMalUid());
                if (commonIdQRCode.isPresent()) {
                    combinedData.setCommonId(commonIdQRCode.get().getCommonId());
                    combinedData.setQrCodeUrl(commonIdQRCode.get().getQrCodeUrl());
                    combinedData.setEngId(commonIdQRCode.get().getEngId());
                    combinedData.setMalId(commonIdQRCode.get().getMalId());
                }

                List<FirstSubMalayalam> firstSubMalayalamList = firstSubMalayalamRepo.findByMainUid(mainId);
                firstSubMalayalamList.sort(Comparator.comparing(FirstSubMalayalam::getId));
                List<CombinedDataSub> combinedDataSubList = new ArrayList<>();
                firstSubMalayalamList.forEach(firstSubMalayalam -> {
                    CombinedDataSub combinedDataSub = new CombinedDataSub();

                    // Set first subheading details
                    combinedDataSub.setTitle(firstSubMalayalam.getTitle());
                    combinedDataSub.setDescription(firstSubMalayalam.getDescription());
                    combinedDataSub.setReferenceUrl(firstSubMalayalam.getRef());
                    combinedDataSub.setuId(firstSubMalayalam.getFsUid());
                    combinedDataSub.setmUid(mainId);

                    CommonIdFs commonIdFs = fsCommonIdRepo.findByfsMalId(firstSubMalayalam.getFsUid());
                    if (commonIdFs!=null){
                        combinedDataSub.setFsCommonId(commonIdFs.getFsCommonId());
                        combinedDataSub.setFsEngId(commonIdFs.getFsEngId());
                        combinedDataSub.setFsMalId(commonIdFs.getFsMalId());
                    }

                    // Fetching images for the current first subheading
                    List<ImgSubFirst> imgSubFirstList = imgSubFirstRepo.findBymalId(firstSubMalayalam.getFsUid());
                    imgSubFirstList.sort(Comparator.comparing(ImgSubFirst::getImgID));
                    combinedDataSub.setImgDataList(imgSubFirstList);

                    // Fetching audio for the current first subheading
                    List<Mp3Data1> mp3Data1List = mp3Data1Repo.findBydtId(firstSubMalayalam.getFsUid());
                    mp3Data1List.sort(Comparator.comparing(Mp3Data1::getId));
                    combinedDataSub.setMp3DataList(mp3Data1List);

                    // Fetching video for the current first subheading
                    List<Mp4Data1> mp4Data1List = mp4Data1Repo.findBydtId(firstSubMalayalam.getFsUid());
                    mp4Data1List.sort(Comparator.comparing(Mp4Data1::getId));
                    combinedDataSub.setMp4DataList(mp4Data1List);

                    // Fetching data for SecondSubMalayalam
                    List<SecondSubMalayalam> secondSubMalayalamList = secondSubMalayalamRepo.findByfsUid(firstSubMalayalam.getFsUid());
                    secondSubMalayalamList.sort(Comparator.comparing(SecondSubMalayalam::getId));
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
                        imgSubSecondList.sort(Comparator.comparing(ImgSubSecond::getImgID));
                        combinedDataSubSub.setImgData2List(imgSubSecondList);
                        // Fetching audio for SecondSubMalayalam
                        List<Mp3Data2> mp3Data2List = mp3Data2Repo.findBydtId(secondSubMalayalam.getSsUid());
                        mp3Data2List.sort(Comparator.comparing(Mp3Data2::getId));
                        combinedDataSubSub.setMp3Data2List(mp3Data2List);
                        // Fetching video for SecondSubMalayalam
                        List<Mp4Data2> mp4Data2List = mp4Data2Repo.findBydtId(secondSubMalayalam.getSsUid());
                        mp4Data2List.sort(Comparator.comparing(Mp4Data2::getId));
                        combinedDataSubSub.setMp4Data2List(mp4Data2List);

                        combinedDataSubSubList.add(combinedDataSubSub);
                    });


                    combinedDataSub.setCombinedDataSubSubList(combinedDataSubSubList);

                    combinedDataSubList.add(combinedDataSub);
                });

                combinedData.setCombinedDataSubList(combinedDataSubList);

                List<ImgData> imgData =imgRepo.findBymalId(mainTitleMal.getMMalUid());
                imgData.sort(Comparator.comparing(ImgData::getImgID));
                combinedData.setImgDataList(imgData);

                List<Mp3Data> mp3Data = mp3Repo.findBydtId(mainTitleMal.getMMalUid());
                mp3Data.sort(Comparator.comparing(Mp3Data::getId));
                combinedData.setMp3DataList(mp3Data);

                List<Mp4Data> mp4Data = mp4DataRepo.findBydtId(mainTitleMal.getMMalUid());
                mp4Data.sort(Comparator.comparing((Mp4Data::getId)));
                combinedData.setMp4DataList(mp4Data);


                combinedDataList.add(combinedData);

            });
            return new ResponseEntity<>(combinedDataList,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<CombinedData>> getCombinedDataByCommonId(Integer dtId, String commonId) {
        try {
            Optional<CommonIdQRCode> commonIdQRCodeOptional = commonIdQRCodeRepo.findByCommonId(commonId);
            if (commonIdQRCodeOptional.isPresent()) {
                CommonIdQRCode commonIdQRCode = commonIdQRCodeOptional.get();

                Optional<DataType> dataTypeOptional = dataTypeRepo.findById(dtId);
                if (dataTypeOptional.isPresent()) {
                    DataType dataType = dataTypeOptional.get();
                    String tData = dataType.getTalk();

                    if ("English".equalsIgnoreCase(tData)) {
                        return getCombinedList(commonIdQRCode.getEngId());
                    } else if ("Malayalam".equalsIgnoreCase(tData)) {
                        return getCombinedListMal(commonIdQRCode.getMalId());
                    } else {
                        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
