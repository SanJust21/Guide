package com.GuideAPP_AKS.DELETE;

import com.GuideAPP_AKS.QR.CommonIdQRCode;
import com.GuideAPP_AKS.QR.CommonIdQRCodeRepo;
import com.GuideAPP_AKS.SecondSubHeading.english.SecondSubEnglishRepo;
import com.GuideAPP_AKS.SecondSubHeading.malayalam.SecondSubMalayalamRepo;
import com.GuideAPP_AKS.firstSubHeading.english.FirstSubEnglishRepo;
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
import com.GuideAPP_AKS.mpFileData.mp3.firstSub.Mp3Data1Repo;
import com.GuideAPP_AKS.mpFileData.mp3.mainHeading.Mp3Repo;
import com.GuideAPP_AKS.mpFileData.mp3.secondSub.Mp3Data2Repo;
import com.GuideAPP_AKS.mpFileData.mp4.firstSub.Mp4Data1Repo;
import com.GuideAPP_AKS.mpFileData.mp4.mainHeading.Mp4DataRepo;
import com.GuideAPP_AKS.firstSubHeading.english.FirstSubEnglish;
import com.GuideAPP_AKS.firstSubHeading.malayalam.FirstSubMalayalam;
import com.GuideAPP_AKS.SecondSubHeading.english.SecondSubEnglish;
import com.GuideAPP_AKS.SecondSubHeading.malayalam.SecondSubMalayalam;
import com.GuideAPP_AKS.mpFileData.mp4.secondSub.Mp4Data2Repo;
import com.amazonaws.services.s3.AmazonS3;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MainDeleteService {

    @Autowired
    private ImgRepo imgRepo;
    @Value("${aws.s3.bucketName}")
    private String bucketName;
    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private CommonIdQRCodeRepo commonIdQRCodeRepo;

    @Autowired
    private MainTitleEngRepo mainTitleEngRepo;
    @Autowired
    private MainTitleMalRepo mainTitleMalRepo;

    @Autowired
    private ImgSubFirstRepo imgSubFirstRepo;

    @Autowired
    private ImgSubSecondRepo imgSubSecondRepo;

    @Autowired
    private Mp3Repo mp3Repo;

    @Autowired
    private Mp4DataRepo mp4DataRepo;

    @Autowired
    private FirstSubEnglishRepo firstSubEnglishRepo;

    @Autowired
    private FirstSubMalayalamRepo firstSubMalayalamRepo;

    @Autowired
    private Mp3Data1Repo mp3Data1Repo;

    @Autowired
    private Mp4Data1Repo mp4Data1Repo;

    @Autowired
    private SecondSubEnglishRepo secondSubEnglishRepo;

    @Autowired
    private SecondSubMalayalamRepo secondSubMalayalamRepo;

    @Autowired
    private Mp3Data2Repo mp3Data2Repo;

    @Autowired
    private Mp4Data2Repo mp4Data2Repo;


    @Transactional
    public void deleteByCommonId(String commonId) {
        // Fetch CommonIdQRCode entity
        Optional<CommonIdQRCode> commonIdQRCode = commonIdQRCodeRepo.findByCommonId(commonId);
        if (!commonIdQRCode.isPresent()) {
            throw new EntityNotFoundException("CommonIdQRCode not found for commonId: " + commonId);
        }

        CommonIdQRCode commonIdQRCode1 = commonIdQRCode.get();
        String mEngUid = commonIdQRCode1.getEngId();
        String mMalUid = commonIdQRCode1.getMalId();

        // Delete main title entities related to mEngUid and mMalUid
        mainTitleEngRepo.deleteBymEngUid(mEngUid);
        mainTitleMalRepo.deleteBymMalUid(mMalUid);

        //imgRepo.deleteByCommonId(commonId);
        deleteImagesByCommonId(commonId);

        //imgSubFirstRepo.deleteByCommonId(commonId);
        deleteImagesFirstByCommonId(commonId);

        //imgSubSecondRepo.deleteByCommonId(commonId);
        deleteImagesSecondByCommonId(commonId);

        // Delete MP3 entities related to mEngUid and mMalUid
        mp3Repo.deleteByDtId(mEngUid);
        mp3Repo.deleteByDtId(mMalUid);

        // Delete MP4 entities related to mEngUid and mMalUid
        mp4DataRepo.deleteByDtId(mEngUid);
        mp4DataRepo.deleteByDtId(mMalUid);

         // Delete first subheading entities related to mEngUid and mMalUid
        firstSubEnglishRepo.deleteByMainUid(mEngUid);
        firstSubMalayalamRepo.deleteByMainUid(mMalUid);

        mp3Data1Repo.deleteByMainEngId(mEngUid);
        mp3Data1Repo.deleteByMainMalId(mMalUid);

        // Delete MP4 entities related to mEngUid and mMalUid
        mp4Data1Repo.deleteByMainEngId(mEngUid);
        mp4Data1Repo.deleteByMainMalId(mMalUid);


//        // Find all fsUids from FirstSubEnglish and FirstSubMalayalam entities
//        List<String> fsUids = new ArrayList<>();
//        fsUids.addAll(firstSubEnglishRepo.findAllByMainUid(mEngUid).stream()
//                .map(FirstSubEnglish::getFsUid)
//                .collect(Collectors.toList()));
//        fsUids.addAll(firstSubMalayalamRepo.findAllByMainUid(mMalUid).stream()
//                .map(FirstSubMalayalam::getFsUid)
//                .collect(Collectors.toList()));
//
//
//        // Find all ssUids from SecondSubEnglish and SecondSubMalayalam entities
//        List<String> ssUids = new ArrayList<>();
//        for (String fsUid : fsUids) {
//            ssUids.addAll(secondSubEnglishRepo.findAllByFsUid(fsUid).stream()
//                    .map(SecondSubEnglish::getSsUid)
//                    .collect(Collectors.toList()));
//            ssUids.addAll(secondSubMalayalamRepo.findAllByFsUid(fsUid).stream()
//                    .map(SecondSubMalayalam::getSsUid)
//                    .collect(Collectors.toList()));
//        }
//
//        // Delete second subheading entities related to each fsUid
//        for (String fsUid : fsUids) {
//            secondSubEnglishRepo.deleteByFsUid(fsUid);
//            secondSubMalayalamRepo.deleteByFsUid(fsUid);
//        }
//
//        // Delete mp3Data2 and mp4Data2 entities related to each ssUid
//        for (String ssUid : ssUids) {
//            mp3Data2Repo.deleteByDtId(ssUid);
//            mp4Data2Repo.deleteByDtId(ssUid);
//        }

        // Find all fsUids from FirstSubEnglish and FirstSubMalayalam entities
        List<String> englishFsUids = firstSubEnglishRepo.findAllByMainUid(mEngUid).stream()
                .map(FirstSubEnglish::getFsUid)
                .collect(Collectors.toList());

        List<String> malayalamFsUids = firstSubMalayalamRepo.findAllByMainUid(mMalUid).stream()
                .map(FirstSubMalayalam::getFsUid)
                .collect(Collectors.toList());

        // Find all ssUids from SecondSubEnglish and SecondSubMalayalam entities
        List<String> englishSsUids = new ArrayList<>();
        for (String fsUid : englishFsUids) {
            englishSsUids.addAll(secondSubEnglishRepo.findAllByFsUid(fsUid).stream()
                    .map(SecondSubEnglish::getSsUid)
                    .collect(Collectors.toList()));
        }

        List<String> malayalamSsUids = new ArrayList<>();
        for (String fsUid : malayalamFsUids) {
            malayalamSsUids.addAll(secondSubMalayalamRepo.findAllByFsUid(fsUid).stream()
                    .map(SecondSubMalayalam::getSsUid)
                    .collect(Collectors.toList()));
        }

        // Delete second subheading entities related to each fsUid
        for (String fsUid : englishFsUids) {
            secondSubEnglishRepo.deleteByFsUid(fsUid);
        }

        for (String fsUid : malayalamFsUids) {
            secondSubMalayalamRepo.deleteByFsUid(fsUid);
        }

        // Delete mp3Data2 and mp4Data2 entities related to each ssUid
        for (String ssUid : englishSsUids) {
            mp3Data2Repo.deleteByDtId(ssUid);
            mp4Data2Repo.deleteByDtId(ssUid);
        }

        for (String ssUid : malayalamSsUids) {
            mp3Data2Repo.deleteByDtId(ssUid);
            mp4Data2Repo.deleteByDtId(ssUid);
        }

        // Delete QR code from S3
        String qrCodeName = commonIdQRCode1.getFName();
        if (qrCodeName != null && !qrCodeName.isEmpty()) {
            deleteImageFromS3(qrCodeName);
        }

        // Finally, delete the CommonIdQRCode entity
        commonIdQRCodeRepo.deleteByCommonId(commonId);
    }

    @Transactional
    public ResponseEntity<?> deleteMainString(String uId) {
        try {
            if (uId == null || uId.isEmpty()) {
                return new ResponseEntity<>("ID is required", HttpStatus.BAD_REQUEST);
            } else {
                Optional<MainTitleMal> mainTitleMal = mainTitleMalRepo.findBymMalUid(uId);
                if (mainTitleMal.isPresent()) {
                    MainTitleMal mainTitleMal1 = mainTitleMal.get();
                    mainTitleMalRepo.delete(mainTitleMal1);
                    return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
                } else {
                    MainTitleEng mainTitleEng = mainTitleEngRepo.findBymEngUid(uId);
                    if (mainTitleEng != null && mainTitleEng.getMEngUid().equals(uId)) {
                        mainTitleEngRepo.delete(mainTitleEng);
                        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>("No data found for ID: " + uId, HttpStatus.NOT_FOUND);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public void deleteImagesByCommonId(String commonId) {
        List<ImgData> existingImgDataList = imgRepo.findByCommonId(commonId);
        for (ImgData imgData : existingImgDataList) {
            deleteImageFromS3(imgData.getFName());
            imgRepo.delete(imgData);
        }
    }

    public void deleteImagesFirstByCommonId(String commonId) {
        // Delete images from ImgSubFirst
        List<ImgSubFirst> existingImgSubFirstList = imgSubFirstRepo.findByCommonId(commonId);
        for (ImgSubFirst imgSubFirst : existingImgSubFirstList) {
            deleteImageFromS3(imgSubFirst.getFName());
            imgSubFirstRepo.delete(imgSubFirst);
        }
    }

    public void deleteImagesSecondByCommonId(String commonId) {
        // Delete images from ImgSubSecond
        List<ImgSubSecond> existingImgSubSecondList = imgSubSecondRepo.findByCommonId(commonId);
        for (ImgSubSecond imgSubSecond : existingImgSubSecondList) {
            deleteImageFromS3(imgSubSecond.getFName());
            imgSubSecondRepo.delete(imgSubSecond);
        }
    }


    public void deleteImagesByCommonIdAndIds(String commonId, List<Integer> imgIds) {
        for (Integer imgId : imgIds) {
            Optional<ImgData> imgDataOptional = imgRepo.findByImgIDAndCommonId(imgId, commonId);
            if (imgDataOptional.isPresent()) {
                ImgData imgData = imgDataOptional.get();
                deleteImageFromS3(imgData.getFName());
                imgRepo.delete(imgData);
            }
        }
    }

    private void deleteImageFromS3(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
    }

}
