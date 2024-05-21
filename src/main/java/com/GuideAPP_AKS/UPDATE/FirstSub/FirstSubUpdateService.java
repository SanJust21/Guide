package com.GuideAPP_AKS.UPDATE.FirstSub;

import com.GuideAPP_AKS.firstSubHeading.english.FirstSubEnglish;
import com.GuideAPP_AKS.firstSubHeading.english.FirstSubEnglishRepo;
import com.GuideAPP_AKS.firstSubHeading.malayalam.FirstSubMalayalam;
import com.GuideAPP_AKS.firstSubHeading.malayalam.FirstSubMalayalamRepo;
import com.GuideAPP_AKS.mainHeading.MainDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FirstSubUpdateService {
    @Autowired
    private FirstSubMalayalamRepo firstSubMalayalamRepo;
    @Autowired
    private FirstSubEnglishRepo firstSubEnglishRepo;
    public ResponseEntity<?> updateFirstSubDataMalyalam(String uId, MainDTO mainDTO) {
        try {
            Optional<FirstSubMalayalam> firstSubMalayalam = firstSubMalayalamRepo.findByfsUid(uId);
            if (firstSubMalayalam.isPresent()){
                FirstSubMalayalam firstSubMalayalam1 = firstSubMalayalam.get();
                if (firstSubMalayalam1.getFsUid().equals(uId)){
                    firstSubMalayalam1.setTitle(mainDTO.getTitle());
                    firstSubMalayalam1.setDescription(mainDTO.getDescription());
                    firstSubMalayalam1.setRef(mainDTO.getReferenceURL());
                    firstSubMalayalamRepo.save(firstSubMalayalam1);
                    return new ResponseEntity<>(firstSubMalayalam1,HttpStatus.OK);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    public ResponseEntity<?> updateFirstSubDataEnglish(String uId, MainDTO mainDTO) {
        try {
            Optional<FirstSubEnglish> firstSubEnglish = firstSubEnglishRepo.findByfsUid(uId);
            if (firstSubEnglish.isPresent()){
                FirstSubEnglish firstSubEnglish1 = firstSubEnglish.get();
                if (firstSubEnglish1.getFsUid().equals(uId)){
                    firstSubEnglish1.setTitle(mainDTO.getTitle());
                    firstSubEnglish1.setDescription(mainDTO.getDescription());
                    firstSubEnglish1.setRef(mainDTO.getReferenceURL());
                    firstSubEnglishRepo.save(firstSubEnglish1);
                    return new ResponseEntity<>(firstSubEnglish1,HttpStatus.OK);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
