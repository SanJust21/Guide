package com.GuideAPP_AKS.UPDATE.MainTitle;

import com.GuideAPP_AKS.img.mainHeading.ImgData;
import com.GuideAPP_AKS.img.mainHeading.ImgRepo;
import com.GuideAPP_AKS.mainHeading.MainDTO;
import com.GuideAPP_AKS.mainHeading.mainEng.MainTitleEng;
import com.GuideAPP_AKS.mainHeading.mainEng.MainTitleEngRepo;
import com.GuideAPP_AKS.mainHeading.mainMal.MainTitleMal;
import com.GuideAPP_AKS.mainHeading.mainMal.MainTitleMalRepo;
import com.GuideAPP_AKS.mpFileData.mp3.mainHeading.Mp3Data;
import com.GuideAPP_AKS.mpFileData.mp4.mainHeading.Mp4Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class MainUpdateService {
    @Autowired
    private MainTitleEngRepo mainTitleEngRepo;
    @Autowired
    private MainTitleMalRepo mainTitleMalRepo;
//    @Autowired
//    private ImgRepo imgRepo;
//    @Autowired
//    private Mp3Data mp3Data;
//    @Autowired
//    private Mp4Data mp4Data;


    public ResponseEntity<?> updateDataMalyalam(String uId,MainDTO mainDTO) {
        try {
            Optional<MainTitleMal> mainTitleMal = mainTitleMalRepo.findBymMalUid(uId);
            if (mainTitleMal.isPresent()){
                MainTitleMal mainTitleMal1 = mainTitleMal.get();
                mainTitleMal1.setTitle(mainDTO.getTitle());
                mainTitleMal1.setDescription(mainDTO.getDescription());
                mainTitleMal1.setRef(mainDTO.getReferenceURL());
                mainTitleMalRepo.save(mainTitleMal1);
                return new ResponseEntity<>(mainTitleMal1,HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> updateDataEnglish(String uId, MainDTO mainDTO) {
        try {
            MainTitleEng mainTitleEng = mainTitleEngRepo.findBymEngUid(uId);
            mainTitleEng.setTitle(mainDTO.getTitle());
            mainTitleEng.setDescription(mainDTO.getDescription());
            mainTitleEng.setRef(mainDTO.getReferenceURL());
            mainTitleEngRepo.save(mainTitleEng);
            return new ResponseEntity<>(mainTitleEng,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
