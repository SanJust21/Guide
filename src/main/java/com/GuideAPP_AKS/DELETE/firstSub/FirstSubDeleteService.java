package com.GuideAPP_AKS.DELETE.firstSub;
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
import com.GuideAPP_AKS.img.secondSubHeading.ImgSubSecond;
import com.GuideAPP_AKS.img.secondSubHeading.ImgSubSecondRepo;
import com.GuideAPP_AKS.mpFileData.mp3.firstSub.Mp3Data1;
import com.GuideAPP_AKS.mpFileData.mp3.firstSub.Mp3Data1Repo;
import com.GuideAPP_AKS.mpFileData.mp3.secondSub.Mp3Data2;
import com.GuideAPP_AKS.mpFileData.mp3.secondSub.Mp3Data2Repo;
import com.GuideAPP_AKS.mpFileData.mp4.firstSub.Mp4Data1;
import com.GuideAPP_AKS.mpFileData.mp4.firstSub.Mp4Data1Repo;
import com.GuideAPP_AKS.mpFileData.mp4.secondSub.Mp4Data2;
import com.GuideAPP_AKS.mpFileData.mp4.secondSub.Mp4Data2Repo;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FirstSubDeleteService {
    @Value("${aws.s3.bucketName}")
    private String bucketName;
    @Autowired
    private AmazonS3 s3Client;
    @Autowired
    private FirstSubEnglishRepo firstSubEnglishRepo;
    @Autowired
    private FirstSubMalayalamRepo firstSubMalayalamRepo;
    @Autowired
    private ImgSubFirst imgSubFirst;
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
    private FsCommonIdRepo fsCommonIdRepo;
    @Transactional
    public int deleteAllByCommonId1(String commonId) {
        try {
            CommonIdFs commonIdFs =fsCommonIdRepo.findByfsCommonId(commonId);
            String cid = commonIdFs.getFsCommonId();
            if (!cid.isEmpty());{
                log.info("inside cid");
                String fsEngId = commonIdFs.getFsEngId();
                String fsMalId = commonIdFs.getFsMalId();

                Optional<FirstSubEnglish> firstSubEnglish = firstSubEnglishRepo.findByfsUid(fsEngId);
                if (firstSubEnglish.isPresent()){
                    log.info("inside firstSubEnglish present");
                    FirstSubEnglish firstSubEnglish1 = firstSubEnglish.get();
                    if (firstSubEnglish1.getFsUid().equals(fsEngId)){
                        log.info("firstSubEnglish equals");
                        firstSubEnglishRepo.deleteAllByfsUid(fsEngId);
                        List<ImgSubFirst> imgSubFirst1 = imgSubFirstRepo.findByengId(fsEngId);
                        if (!imgSubFirst1.isEmpty()){
                            log.info("Inside jpg");
                            List<String> fileNamesToDelete = imgSubFirst1.stream().map(ImgSubFirst::getFName)
                                    .collect(Collectors.toList());
                            deleteDataFromS3(fileNamesToDelete);
                            imgSubFirstRepo.deleteAllByengId(fsEngId);
                        }
                        List<Mp3Data1> mp3Data1 = mp3Data1Repo.findBydtId(fsEngId);
                        if (!mp3Data1.isEmpty()){
                            log.info("Inside mp3");
                            List<String> fileNamesToDelete = mp3Data1.stream().map(Mp3Data1::getFName)
                                    .collect(Collectors.toList());
                            deleteDataFromS3(fileNamesToDelete);
                            mp3Data1Repo.deleteAllBydtId(fsEngId);
                        }
                        List<Mp4Data1> mp4Data1s = mp4Data1Repo.findBydtId(fsEngId);
                        if (!mp4Data1s.isEmpty()){
                            log.info("Inside mp4");
                            List<String> fileNamesToDelete = mp4Data1s.stream().map(Mp4Data1::getFName)
                                    .collect(Collectors.toList());
                            deleteDataFromS3(fileNamesToDelete);
                            mp4Data1Repo.deleteAllBydtId(fsEngId);
                        }
                        Optional<SecondSubEnglish> secondSubEnglish = secondSubEnglishRepo.findByFsUid(fsMalId);
                        if (secondSubEnglish.isPresent()){
                            SecondSubEnglish secondSubEnglish1 = secondSubEnglish.get();
                            if (secondSubEnglish1 !=null && secondSubEnglish1.getFsUid().equals(fsEngId)){
                                log.info("Inside secondSubEnglish");
                                String sEngId = secondSubEnglish1.getSsUid();
                                secondSubEnglishRepo.deleteAllByfsUid(fsEngId);
                                List<ImgSubSecond> imgSubSeconds = imgSubSecondRepo.findByfsEngUid(sEngId);
                                if (!imgSubSeconds.isEmpty()){
                                    log.info("Inside secondSubEnglish jpg");
                                    List<String> fileNamesToDelete = imgSubSeconds.stream().map(ImgSubSecond::getFName)
                                            .collect(Collectors.toList());
                                    deleteDataFromS3(fileNamesToDelete);
                                    imgSubSecondRepo.deleteAllByfsEngUid(fsEngId);
                                }
                                List<Mp3Data2> mp3Data2s =mp3Data2Repo.findBydtId(sEngId);
                                if (!mp3Data2s.isEmpty()){
                                    log.info("Inside secondSubEnglish mp3");
                                    List<String> fileNamesToDelete = mp3Data2s.stream().map(Mp3Data2::getFName)
                                            .collect(Collectors.toList());
                                    deleteDataFromS3(fileNamesToDelete);
                                    mp3Data2Repo.deleteAllBydtId(sEngId);
                                }
                                List<Mp4Data2> mp4Data2s = mp4Data2Repo.findBydtId(sEngId);
                                if (!mp4Data2s.isEmpty()){
                                    List<String> fileNamesToDelete = mp4Data2s.stream().map(Mp4Data2::getFName)
                                            .collect(Collectors.toList());
                                    deleteDataFromS3(fileNamesToDelete);
                                    log.info("Inside secondSubEnglish mp4");
                                    mp4Data2Repo.deleteAllBydtId(sEngId);
                                }
                            }else {
                                log.info("No data");
                            }
                        }
                        //SecondSubEnglish secondSubEnglish = secondSubEnglishRepo.findByfsUid(fsEngId);
                        //log.info("details of 2nd Sub : "+secondSubEnglish);

                    }
                }else {
                    log.info("invalid fsEngId");
                }

                Optional<FirstSubMalayalam> firstSubMalayalam = firstSubMalayalamRepo.findByfsUid(fsMalId);
                if (firstSubMalayalam.isPresent()){
                    log.info("Inside firstSubMalayalam");
                    FirstSubMalayalam firstSubMalayalam1 = firstSubMalayalam.get();
                    if (firstSubMalayalam1.getFsUid().equals(fsMalId)){
                        log.info("Inside firstSubMalayalam equals");
                        firstSubMalayalamRepo.deleteAllByfsUid(fsMalId);

                        List<Mp3Data1> mp3Data1 = mp3Data1Repo.findBydtId(fsMalId);
                        if (!mp3Data1.isEmpty()){
                            log.info("inside firstSub mp3");
                            List<String> fileNamesToDelete = mp3Data1.stream().map(Mp3Data1::getFName)
                                    .collect(Collectors.toList());
                            deleteDataFromS3(fileNamesToDelete);
                            mp3Data1Repo.deleteAllBydtId(fsMalId);
                        }
                        List<Mp4Data1> mp4Data1s = mp4Data1Repo.findBydtId(fsMalId);
                        if (!mp4Data1s.isEmpty()){
                            List<String> fileNamesToDelete = mp4Data1s.stream().map(Mp4Data1::getFName)
                                    .collect(Collectors.toList());
                            deleteDataFromS3(fileNamesToDelete);
                            log.info("inside firstSub mp4");
                            mp4Data1Repo.deleteAllBydtId(fsMalId);
                        }

                        Optional<SecondSubMalayalam> secondSubMalayalam = secondSubMalayalamRepo.findByFsUid(fsMalId);
                        if (secondSubMalayalam.isPresent()){
                            log.info("inside secondSubMalayalam");
                            SecondSubMalayalam secondSubMalayalam1 = secondSubMalayalam.get();
                            if (secondSubMalayalam1 !=null && secondSubMalayalam1.getFsUid().equals(fsMalId)){
                                log.info("inside secondSubMalayalam equals");
                                String sMalI = secondSubMalayalam1.getSsUid();
                                secondSubMalayalamRepo.deleteByFsUid(fsMalId);

                                List<Mp3Data2> mp3Data2s =mp3Data2Repo.findBydtId(sMalI);
                                if (!mp3Data2s.isEmpty()){
                                    log.info("inside secondSubMalayalam mp3");
                                    List<String> fileNamesToDelete = mp3Data2s.stream().map(Mp3Data2::getFName)
                                            .collect(Collectors.toList());
                                    deleteDataFromS3(fileNamesToDelete);
                                    mp3Data2Repo.deleteAllBydtId(sMalI);
                                }
                                List<Mp4Data2> mp4Data2s = mp4Data2Repo.findBydtId(sMalI);
                                if (!mp4Data2s.isEmpty()){
                                    log.info("inside secondSubMalayalam mp4");
                                    List<String> fileNamesToDelete = mp4Data2s.stream().map(Mp4Data2::getFName)
                                            .collect(Collectors.toList());
                                    deleteDataFromS3(fileNamesToDelete);
                                    mp4Data2Repo.deleteAllBydtId(sMalI);
                                }
                            }
                        }
                    }
                }
                return fsCommonIdRepo.deleteAllByfsCommonId(commonId);
            }
        }catch (Exception e){
            e.printStackTrace();
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