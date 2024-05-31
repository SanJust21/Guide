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
import com.GuideAPP_AKS.firstSubHeading.english.FirstSubEnglish;
import com.GuideAPP_AKS.firstSubHeading.malayalam.FirstSubMalayalam;
import com.GuideAPP_AKS.SecondSubHeading.english.SecondSubEnglish;
import com.GuideAPP_AKS.SecondSubHeading.malayalam.SecondSubMalayalam;
import com.GuideAPP_AKS.mpFileData.mp4.secondSub.Mp4Data2;
import com.GuideAPP_AKS.mpFileData.mp4.secondSub.Mp4Data2Repo;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
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

        log.info("Fetching fsUids for mEngUid: {}", mEngUid);
        List<FirstSubEnglish> englishFirstSubList = firstSubEnglishRepo.findByMainUid(mEngUid);
        List<String> englishFsUids = englishFirstSubList.stream()
                .map(FirstSubEnglish::getFsUid)
                .collect(Collectors.toList());
        log.info("Fetched English fsUids: {}", englishFsUids);

        log.info("Fetching fsUids for mMalUid: {}", mMalUid);
        List<FirstSubMalayalam> malayalamFirstSubList = firstSubMalayalamRepo.findByMainUid(mMalUid);
        List<String> malayalamFsUids = malayalamFirstSubList.stream()
                .map(FirstSubMalayalam::getFsUid)
                .collect(Collectors.toList());
        log.info("Fetched Malayalam fsUids: {}", malayalamFsUids);

        // Find all ssUids from SecondSubEnglish and SecondSubMalayalam entities
        List<String> englishSsUids = new ArrayList<>();
        for (String fsUid : englishFsUids) {
            englishSsUids.addAll(secondSubEnglishRepo.findAllByFsUid(fsUid).stream()
                    .map(SecondSubEnglish::getSsUid)
                    .collect(Collectors.toList()));
        }
        log.info("English ssUids: {}", englishSsUids);

        List<String> malayalamSsUids = new ArrayList<>();
        for (String fsUid : malayalamFsUids) {
            malayalamSsUids.addAll(secondSubMalayalamRepo.findAllByFsUid(fsUid).stream()
                    .map(SecondSubMalayalam::getSsUid)
                    .collect(Collectors.toList()));
        }
        log.info("Malayalam ssUids: {}", malayalamSsUids);

        // Delete main title entities related to mEngUid and mMalUid
        mainTitleEngRepo.deleteBymEngUid(mEngUid);
        mainTitleMalRepo.deleteBymMalUid(mMalUid);

        //imgRepo.deleteByCommonId(commonId);
        deleteIfPresent(() ->deleteImagesByCommonId(commonId));

        //imgSubFirstRepo.deleteByCommonId(commonId);
        deleteIfPresent(() ->deleteImagesFirstByCommonId(commonId));

        //imgSubSecondRepo.deleteByCommonId(commonId);
        deleteIfPresent(() ->deleteImagesSecondByCommonId(commonId));

        // Delete MP3 entities related to mEngUid and mMalUid
        //mp3Repo.deleteByDtId(mEngUid);
        deleteIfPresent(() ->deleteMp3ByDtId(mEngUid));
        //mp3Repo.deleteByDtId(mMalUid);
        deleteIfPresent(() ->deleteMp3ByDtId(mMalUid));

        // Delete MP4 entities related to mEngUid and mMalUid
        //mp4DataRepo.deleteByDtId(mEngUid);
        deleteIfPresent(() ->deleteMp4ByDtId(mEngUid));
        //mp4DataRepo.deleteByDtId(mMalUid);
        deleteIfPresent(() ->deleteMp4ByDtId(mMalUid));

         // Delete first subheading entities related to mEngUid and mMalUid
        deleteIfPresent(() ->firstSubEnglishRepo.deleteByMainUid(mEngUid));
        deleteIfPresent(() ->firstSubMalayalamRepo.deleteByMainUid(mMalUid));

        //mp3Data1Repo.deleteByMainEngId(mEngUid);
        //mp3Data1Repo.deleteByMainMalId(mMalUid);

        deleteIfPresent(() ->deleteMp3FirstByMainEngId(mEngUid));
        deleteIfPresent(() ->deleteMp3FirstByMainMalId(mMalUid));

        // Delete MP4 entities related to mEngUid and mMalUid
//        mp4Data1Repo.deleteByMainEngId(mEngUid);
//        mp4Data1Repo.deleteByMainMalId(mMalUid);
        deleteIfPresent(() ->deleteMp4FirstByMainEngId(mEngUid));
        deleteIfPresent(() ->deleteMp4FirstByMainMalId(mMalUid));


        // Delete second subheading entities related to each fsUid
        for (String fsUid : englishFsUids) {
            deleteIfPresent(() ->secondSubEnglishRepo.deleteByFsUid(fsUid));
        }

        for (String fsUid : malayalamFsUids) {
            deleteIfPresent(() ->secondSubMalayalamRepo.deleteByFsUid(fsUid));
        }

        // Delete mp3Data2 and mp4Data2 entities related to each ssUid
        for (String ssUid : englishSsUids) {
            //mp3Data2Repo.deleteByDtId(ssUid);
            deleteIfPresent(() ->deleteMp3SecondByDtId(ssUid));
            //mp4Data2Repo.deleteByDtId(ssUid);
            deleteIfPresent(() ->deleteMp4SecondByDtId(ssUid));
        }

        for (String ssUid : malayalamSsUids) {
            //mp3Data2Repo.deleteByDtId(ssUid);
            deleteIfPresent(() ->deleteMp3SecondByDtId(ssUid));
            //mp4Data2Repo.deleteByDtId(ssUid);
            deleteIfPresent(() ->deleteMp4SecondByDtId(ssUid));
        }

        // Delete QR code from S3
        String qrCodeName = commonIdQRCode1.getFName();
        if (qrCodeName != null && !qrCodeName.isEmpty()) {
            deleteImageFromS3(qrCodeName);
        }

        // Finally, delete the CommonIdQRCode entity
        commonIdQRCodeRepo.deleteByCommonId(commonId);
    }

    private void deleteIfPresent(Runnable deleteFunction) {
        try {
            deleteFunction.run();
        } catch (EntityNotFoundException ex) {
            log.warn("Entity not found during deletion: {}", ex.getMessage());
        }
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

    public void deleteImagesFirstByCommonIdAndIds(String commonId, List<Integer> imgIds) {
        for (Integer imgId : imgIds) {
            Optional<ImgSubFirst> imgDataOptional = imgSubFirstRepo.findByImgIDAndCommonId(imgId, commonId);
            if (imgDataOptional.isPresent()) {
                ImgSubFirst imgData = imgDataOptional.get();
                deleteImageFromS3(imgData.getFName());
                imgSubFirstRepo.delete(imgData);
            }
        }
    }

    public void deleteImagesSecondByCommonIdAndIds(String commonId, List<Integer> imgIds) {
        for (Integer imgId : imgIds) {
            Optional<ImgSubSecond> imgDataOptional = imgSubSecondRepo.findByImgIDAndCommonId(imgId, commonId);
            if (imgDataOptional.isPresent()) {
                ImgSubSecond imgData = imgDataOptional.get();
                deleteImageFromS3(imgData.getFName());
                imgSubSecondRepo.delete(imgData);
            }
        }
    }

    private void deleteImageFromS3(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
    }

    public void deleteMp3ByDtId(String dtId) {
        // Delete MP3 details from the database and S3 bucket
        List<Mp3Data> existingMp3List = mp3Repo.findBydtId(dtId);
        if (!existingMp3List.isEmpty()) {
            for (Mp3Data mp3 : existingMp3List) {
                deleteFileFromS3(mp3.getFName());
                mp3Repo.delete(mp3);
            }
        } else {
            throw new EntityNotFoundException("MP3 file not found for dtId: " + dtId);
        }
    }

    public void deleteMp4ByDtId(String dtId) {
        // Delete MP4 details from the database and S3 bucket
        List<Mp4Data> existingMp4List = mp4DataRepo.findBydtId(dtId);
        if (!existingMp4List.isEmpty()) {
            for (Mp4Data mp4 : existingMp4List) {
                deleteFileFromS3(mp4.getFName());
                mp4DataRepo.delete(mp4);
            }
        } else {
            throw new EntityNotFoundException("MP4 file not found for dtId: " + dtId);
        }
    }


    public void deleteMp3FirstByMainEngId(String mainEngId) {
        List<Mp3Data1> existingMp3List = mp3Data1Repo.findByMainEngId(mainEngId);
        for (Mp3Data1 mp3 : existingMp3List) {
            deleteFileFromS3(mp3.getFName());
            mp3Data1Repo.delete(mp3);
        }
    }

    public void deleteMp3FirstByMainMalId(String mainMalId) {
        List<Mp3Data1> existingMp3List = mp3Data1Repo.findByMainMalId(mainMalId);
        for (Mp3Data1 mp3 : existingMp3List) {
            deleteFileFromS3(mp3.getFName());
            mp3Data1Repo.delete(mp3);
        }
    }

    public void deleteMp4FirstByMainEngId(String mainEngId) {
        // Find and delete MP4 details from the database and S3 bucket
        List<Mp4Data1> existingMp4List = mp4Data1Repo.findByMainEngId(mainEngId);
        for (Mp4Data1 mp4 : existingMp4List) {
            deleteFileFromS3(mp4.getFName());
            mp4Data1Repo.delete(mp4);
        }
    }

    public void deleteMp4FirstByMainMalId(String mainMalId) {
        // Find and delete MP4 details from the database and S3 bucket
        List<Mp4Data1> existingMp4List = mp4Data1Repo.findByMainMalId(mainMalId);
        for (Mp4Data1 mp4 : existingMp4List) {
            deleteFileFromS3(mp4.getFName());
            mp4Data1Repo.delete(mp4);
        }
    }

    public void deleteMp3SecondByDtId(String dtId) {
        // Delete MP3 details from the database and S3 bucket
        List<Mp3Data2> existingMp3List = mp3Data2Repo.findBydtId(dtId);
        for (Mp3Data2 mp3 : existingMp3List) {
            deleteFileFromS3(mp3.getFName());
            mp3Data2Repo.delete(mp3);
        }
    }

    public void deleteMp4SecondByDtId(String dtId) {
        // Delete MP4 details from the database and S3 bucket
        List<Mp4Data2> existingMp4List = mp4Data2Repo.findBydtId(dtId);
        for (Mp4Data2 mp4 : existingMp4List) {
            deleteFileFromS3(mp4.getFName());
            mp4Data2Repo.delete(mp4);
        }
    }

    public void deleteFileFromS3(String fileName) {
        try {
            s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        } catch (AmazonServiceException e) {
            // Handle Amazon Service Exception
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Handle SDK Client Exception
            e.printStackTrace();
        }
    }


}
