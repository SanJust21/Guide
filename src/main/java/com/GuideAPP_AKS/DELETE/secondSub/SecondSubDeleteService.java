package com.GuideAPP_AKS.DELETE.secondSub;

import com.GuideAPP_AKS.SecondSubHeading.commonId.CommonIdSs;
import com.GuideAPP_AKS.SecondSubHeading.commonId.CommonIdSsRepo;
import com.GuideAPP_AKS.SecondSubHeading.english.SecondSubEnglish;
import com.GuideAPP_AKS.SecondSubHeading.english.SecondSubEnglishRepo;
import com.GuideAPP_AKS.SecondSubHeading.malayalam.SecondSubMalayalam;
import com.GuideAPP_AKS.SecondSubHeading.malayalam.SecondSubMalayalamRepo;
import com.GuideAPP_AKS.img.secondSubHeading.ImgSubSecond;
import com.GuideAPP_AKS.img.secondSubHeading.ImgSubSecondRepo;
import com.GuideAPP_AKS.mpFileData.mp3.secondSub.Mp3Data2;
import com.GuideAPP_AKS.mpFileData.mp3.secondSub.Mp3Data2Repo;
import com.GuideAPP_AKS.mpFileData.mp4.secondSub.Mp4Data2;
import com.GuideAPP_AKS.mpFileData.mp4.secondSub.Mp4Data2Repo;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SecondSubDeleteService {
    @Value("${aws.s3.bucketName}")
    private String bucketName;
    @Autowired
    private AmazonS3 s3Client;
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
    private CommonIdSsRepo commonIdSsRepo;

    @Transactional
    public int commonIdSecond(String id) {
        CommonIdSs commonIdSs = commonIdSsRepo.findByssCommonId(id);
        if (commonIdSs !=null){
            String englishId = commonIdSs.getSsEngId();
            String malId = commonIdSs.getSsMalId();

            Optional<SecondSubEnglish> secondSubEnglish = secondSubEnglishRepo.findByssUid(englishId);
            if (secondSubEnglish.isPresent()){
                SecondSubEnglish secondSubEnglish1 = secondSubEnglish.get();
                if (secondSubEnglish1.getSsUid().equals(englishId)){
                    secondSubEnglishRepo.deleteAllByssUid(englishId);
                }
                List<ImgSubSecond> imgSubSeconds = imgSubSecondRepo.findByengId(englishId);
                if (!imgSubSeconds.isEmpty()){
                    List<String> fileNamesToDelete = imgSubSeconds.stream().map(ImgSubSecond::getFName)
                            .collect(Collectors.toList());
                    deleteDataFromS3(fileNamesToDelete);
                    imgSubSecondRepo.deleteAllByengId(englishId);

                }
                List<Mp3Data2> mp3Data2s = mp3Data2Repo.findBydtId(englishId);
                if (!mp3Data2s.isEmpty()){
                    List<String> fileNamesToDelete = mp3Data2s.stream().map(Mp3Data2::getFName)
                            .collect(Collectors.toList());
                    deleteDataFromS3(fileNamesToDelete);
                    mp3Data2Repo.deleteAllBydtId(englishId);
                }
                List<Mp4Data2> mp4Data2s = mp4Data2Repo.findBydtId(englishId);
                if (!mp4Data2s.isEmpty()){
                    List<String> fileNamesToDelete = mp4Data2s.stream().map(Mp4Data2::getFName)
                            .collect(Collectors.toList());
                    deleteDataFromS3(fileNamesToDelete);
                    mp4Data2Repo.deleteAllBydtId(englishId);
                }
            }
            Optional<SecondSubMalayalam> secondSubMalayalam = secondSubMalayalamRepo.findByssUid(malId);
            if (secondSubMalayalam.isPresent()){
                SecondSubMalayalam secondSubMalayalam1 = secondSubMalayalam.get();
                if (secondSubMalayalam1.getSsUid().equals(malId)){
                    secondSubMalayalamRepo.deleteAllByssUid(malId);
                }
                List<Mp3Data2> mp3Data2s = mp3Data2Repo.findBydtId(malId);
                if (!mp3Data2s.isEmpty()){
                    List<String> fileNamesToDelete = mp3Data2s.stream().map(Mp3Data2::getFName)
                            .collect(Collectors.toList());
                    deleteDataFromS3(fileNamesToDelete);
                    mp3Data2Repo.deleteAllBydtId(malId);
                }
                List<Mp4Data2> mp4Data2s = mp4Data2Repo.findBydtId(malId);
                if (!mp4Data2s.isEmpty()){
                    List<String> fileNamesToDelete = mp4Data2s.stream().map(Mp4Data2::getFName)
                                    .collect(Collectors.toList());
                    deleteDataFromS3(fileNamesToDelete);
                    mp4Data2Repo.deleteAllBydtId(malId);
                }
            }
            commonIdSsRepo.deleteByssCommonId(id);
            return 1;
        }
        return 0;
    }

    private void deleteDataFromS3(List<String> fileNamesToDelete) {
        for (String fileName : fileNamesToDelete){
            try {
                s3Client.deleteObject(new DeleteObjectRequest(bucketName,fileName));

            }catch (AmazonServiceException e){
                e.printStackTrace();
            }
        }
    }


}
