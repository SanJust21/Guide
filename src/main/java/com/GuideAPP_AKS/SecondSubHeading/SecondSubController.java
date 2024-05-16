package com.GuideAPP_AKS.SecondSubHeading;

import com.GuideAPP_AKS.Language.DataTypeRepo;
import com.GuideAPP_AKS.SecondSubHeading.english.SecondSubEnglishRepo;
import com.GuideAPP_AKS.SecondSubHeading.malayalam.SecondSubMalayalamRepo;
import com.GuideAPP_AKS.firstSubHeading.english.FirstSubEnglish;
import com.GuideAPP_AKS.firstSubHeading.english.FirstSubEnglishRepo;
import com.GuideAPP_AKS.firstSubHeading.malayalam.FirstSubMalayalam;
import com.GuideAPP_AKS.firstSubHeading.malayalam.FirstSubMalayalamRepo;
import com.GuideAPP_AKS.mainHeading.MainDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/DataEntry3")
@CrossOrigin
public class SecondSubController {
    @Autowired
    private SecondSubService secondSubService;
    @Autowired
    private SecondSubEnglishRepo secondSubEnglishRepo;
    @Autowired
    private SecondSubMalayalamRepo secondSubMalayalamRepo;
    @Autowired
    private FirstSubMalayalamRepo firstSubMalayalamRepo;
    @Autowired
    private FirstSubEnglishRepo firstSubEnglishRepo;
    @Autowired
    private DataTypeRepo dataTypeRepo;

    @PostMapping(path = "/secondSub")
    public ResponseEntity<?> addSecondSubData(@RequestParam String uId,
                                             @RequestBody MainDTO mainDTO){
        try {

            if (uId == null || uId.isEmpty()) {
                return new ResponseEntity<>("ID is required", HttpStatus.BAD_REQUEST);
            }

            Optional<FirstSubMalayalam> malOptional = firstSubMalayalamRepo.findByFsUid(uId);

            FirstSubEnglish firstSubEng = firstSubEnglishRepo.findByFsUid(uId);
            if (firstSubEng!=null){
                String engId = firstSubEng.getFsUid();
                if (engId.equals(uId)){
                    return secondSubService.addSubDataEnglish(uId,mainDTO);
                }
            }else {
                if (malOptional.isPresent()){
                    FirstSubMalayalam firstSubMal = malOptional.get();
                    String malId = firstSubMal.getFsUid();
                    if (uId.equals(malId)){
                        return secondSubService.addSubDataMalayalam(uId, mainDTO);
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @GetMapping(path = "/getSecondSubComplete")
//    public ResponseEntity<List<CombinedDataSub>>getAllMainTitleData(@RequestParam Integer dtId){
//        try {
//            Optional<DataType> dataTypeOptional = dataTypeRepo.findById(dtId);
//            if (dataTypeOptional.isPresent()){
//                DataType dataType = dataTypeOptional.get();
//                String tData = dataType.getTalk();
//                if (tData != null && "English".equalsIgnoreCase(tData)){
//                    return secondSubService.getCombinedListEng();
//                } else if (tData != null && "Malayalam".equalsIgnoreCase(tData)) {
//                    return secondSubService.getCombinedListMal();
//                }else {
//                    return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
//                }
//            }else {
//                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
