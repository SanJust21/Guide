package com.GuideAPP_AKS.img;

import com.GuideAPP_AKS.SecondSubHeading.english.SecondSubEnglish;
import com.GuideAPP_AKS.SecondSubHeading.english.SecondSubEnglishRepo;
import com.GuideAPP_AKS.SecondSubHeading.malayalam.SecondSubMalayalam;
import com.GuideAPP_AKS.SecondSubHeading.malayalam.SecondSubMalayalamRepo;
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
import com.GuideAPP_AKS.mainHeading.mainEng.MainTitleEngRepo;
import com.GuideAPP_AKS.mainHeading.mainMal.MainTitleMalRepo;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ImgService {
    @Autowired
    private ImgRepo imgRepo;
    @Value("${aws.s3.bucketName}")
    private String bucketName;
    @Autowired
    private AmazonS3 s3Client;
    @Autowired
    private ImgSubFirstRepo imgSubFirstRepo;

    @Autowired
    private ImgSubSecondRepo imgSubSecondRepo;

    @Autowired
    private ImgSubFirst imgSubFirst;

    @Autowired
    private ImgSubSecond imgSubSecond;

    @Autowired
    private FirstSubEnglishRepo firstSubEnglishRepo;

    @Autowired
    private FirstSubMalayalamRepo firstSubMalayalamRepo;

    @Autowired
    private SecondSubEnglishRepo secondSubEnglishRepo;

    @Autowired
    private SecondSubMalayalamRepo secondSubMalayalamRepo;

    private File convertMultiPartFileToFile(MultipartFile file){
        File convertedFile = new File(file.getOriginalFilename());
        try(FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());

        }catch (Exception e){
            log.error("Error converting multipartFile to file",e);
        }
        return convertedFile;
    }
    public ImgData uploadJPG(MultipartFile file, String englishUId, String malUid) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName =System.currentTimeMillis()+"_"+file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName,fileName,fileObj));
        fileObj.delete();
        String fileUrl = s3Client.getUrl(bucketName,fileName).toString();
        ImgData imgData = new ImgData(fileName,fileUrl,englishUId,malUid);
        imgRepo.save(imgData);
        return imgData;
    }

    public ImgSubFirst uploadData1(MultipartFile file, String englishUId, String malUid) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName =System.currentTimeMillis()+"_"+file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName,fileName,fileObj));
        fileObj.delete();
        String fileUrl = s3Client.getUrl(bucketName,fileName).toString();

        ImgSubFirst imgSubFirst = new ImgSubFirst(fileName,fileUrl,englishUId,malUid);
        Optional<FirstSubEnglish> firstSubEnglishOptional = firstSubEnglishRepo.findByfsUid(englishUId);
        if (firstSubEnglishOptional.isPresent()){
            FirstSubEnglish firstSubEnglish = firstSubEnglishOptional.get();
            String uId = firstSubEnglish.getMainUid();
            imgSubFirst.setMainEngUid(uId);
        }else {
            log.info(" Eng id is null");
        }
        Optional<FirstSubMalayalam> firstSubMalayalamOptional =firstSubMalayalamRepo.findByfsUid(malUid);
        if (firstSubMalayalamOptional.isPresent()){
            FirstSubMalayalam firstSubMalayalam = firstSubMalayalamOptional.get();
            String uId = firstSubMalayalam.getMainUid();
            imgSubFirst.setMainMalUid(uId);
        }else {
            log.info("Mal id is null");
        }


        imgSubFirstRepo.save(imgSubFirst);
        return imgSubFirst;
    }

    public ImgSubSecond uploadData2(MultipartFile file, String englishUId, String malUid) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName =System.currentTimeMillis()+"_"+file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName,fileName,fileObj));
        fileObj.delete();
        String fileUrl = s3Client.getUrl(bucketName,fileName).toString();
        ImgSubSecond imgSubSecond = new ImgSubSecond(fileName,fileUrl,englishUId,malUid);

        Optional<SecondSubEnglish> secondSubEnglishOptional = secondSubEnglishRepo.findByssUid(englishUId);
        if (secondSubEnglishOptional.isPresent()){
            SecondSubEnglish secondSubEnglish = secondSubEnglishOptional.get();
            String uId = secondSubEnglish.getFsUid();
            imgSubSecond.setFsEngUid(uId);
        }else {
            log.info(" Eng id is null");
        }
        Optional<SecondSubMalayalam> secondSubMalayalamOptional = secondSubMalayalamRepo.findByssUid(malUid);
        if (secondSubMalayalamOptional.isPresent()){
            SecondSubMalayalam secondSubMalayalam = secondSubMalayalamOptional.get();
            String uId = secondSubMalayalam.getFsUid();
            imgSubSecond.setFsMalUid(uId);
        }else {
            log.info("Mal id is null");
        }
        imgSubSecondRepo.save(imgSubSecond);
        return imgSubSecond;
    }

    public ImgData updateMainJPG(MultipartFile file, String englishUId, String malUid) {
        try {
            List<ImgData> existingImgData = imgRepo.findByengId(englishUId);
            if (!file.isEmpty()){
                File fileObj = convertMultiPartFileToFile(file);
                String fileName = System.currentTimeMillis() +"_"+file.getOriginalFilename();
                s3Client.putObject(new PutObjectRequest(bucketName,fileName,fileObj));
                fileObj.delete();
                String fileUrl = s3Client.getUrl(bucketName,fileName).toString() ;
//                for (ImgData imgData : existingImgData){
//                    imgData.setFUrl(fileUrl);
//                    imgData.setFName(fileName);
//                }
//                imgRepo.saveAll(existingImgData);
//                return new ImgData(fileName,fileUrl,englishUId,malUid);

                ImgData imgData = new ImgData(fileName,fileUrl,englishUId,malUid);
                imgRepo.save(imgData);
                return imgData;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ImgData(null,null,null,null);
    }


    public ImgSubFirst updateFirstSubJPG(MultipartFile file, String englishUId, String malUid) {
        try {
            List<ImgSubFirst> existingImgData = imgSubFirstRepo.findByengId(englishUId);
            if (!file.isEmpty()){
                File fileObj = convertMultiPartFileToFile(file);
                String fileName = System.currentTimeMillis() +"_"+file.getOriginalFilename();
                s3Client.putObject(new PutObjectRequest(bucketName,fileName,fileObj));
                fileObj.delete();
                String fileUrl = s3Client.getUrl(bucketName,fileName).toString() ;

                ImgSubFirst imgSubFirst = new ImgSubFirst(fileName,fileUrl,englishUId,malUid);
                Optional<FirstSubEnglish> firstSubEnglishOptional = firstSubEnglishRepo.findByfsUid(englishUId);
                if (firstSubEnglishOptional.isPresent()){
                    FirstSubEnglish firstSubEnglish = firstSubEnglishOptional.get();
                    String uId = firstSubEnglish.getMainUid();
                    imgSubFirst.setMainEngUid(uId);
                }else {
                    log.info(" Eng id is null");
                }
                Optional<FirstSubMalayalam> firstSubMalayalamOptional =firstSubMalayalamRepo.findByfsUid(malUid);
                if (firstSubMalayalamOptional.isPresent()){
                    FirstSubMalayalam firstSubMalayalam = firstSubMalayalamOptional.get();
                    String uId = firstSubMalayalam.getMainUid();
                    imgSubFirst.setMainMalUid(uId);
                }else {
                    log.info("Mal id is null");
                }
                imgSubFirstRepo.save(imgSubFirst);
                return imgSubFirst;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ImgSubFirst("No Data","No Data","No Data","No Data");
    }

    public ImgSubSecond updateSecondSubJPG(MultipartFile file, String englishUId, String malUid) {
        try {
            List<ImgSubSecond> existingImgData = imgSubSecondRepo.findByengId(englishUId);
            if (!file.isEmpty()){
                File fileObj = convertMultiPartFileToFile(file);
                String fileName = System.currentTimeMillis() +"_"+file.getOriginalFilename();
                s3Client.putObject(new PutObjectRequest(bucketName,fileName,fileObj));
                fileObj.delete();
                String fileUrl = s3Client.getUrl(bucketName,fileName).toString() ;

                ImgSubSecond imgSubSecond1 = new ImgSubSecond(fileName,fileUrl,englishUId,malUid);
                Optional<SecondSubEnglish> secondSubEnglishOptional = secondSubEnglishRepo.findByssUid(englishUId);
                if (secondSubEnglishOptional.isPresent()){
                    SecondSubEnglish secondSubEnglish = secondSubEnglishOptional.get();
                    String uId = secondSubEnglish.getFsUid();
                    imgSubSecond1.setFsEngUid(uId);
                }else {
                    log.info(" Eng id is null");
                }
                Optional<SecondSubMalayalam> secondSubMalayalamOptional =secondSubMalayalamRepo.findByssUid(malUid);
                if (secondSubMalayalamOptional.isPresent()){
                    SecondSubMalayalam secondSubMalayalam = secondSubMalayalamOptional.get();
                    String uId = secondSubMalayalam.getFsUid();
                    imgSubSecond.setFsMalUid(uId);
                }else {
                    log.info("Mal id is null");
                }
                imgSubSecondRepo.save(imgSubSecond);
                return imgSubSecond;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ImgSubSecond("No Data","No Data","No Data","No Data");
    }
}
