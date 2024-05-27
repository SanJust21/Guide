package com.GuideAPP_AKS.DELETE;

import com.GuideAPP_AKS.UPDATE.MainTitle.MainUpdateService;
import com.GuideAPP_AKS.img.ImgService;
import com.GuideAPP_AKS.img.mainHeading.ImgRepo;
import com.GuideAPP_AKS.mainHeading.mainEng.MainTitleEngRepo;
import com.GuideAPP_AKS.mainHeading.mainMal.MainTitleMalRepo;
import com.GuideAPP_AKS.mpFileData.MediaTypeService;
import com.GuideAPP_AKS.mpType.FileTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/deleteMain")
@CrossOrigin
public class MainDeleteController {
    @Autowired
    private MainUpdateService mainUpdateService;
    @Autowired
    private MainTitleMalRepo mainTitleMalRepo;
    @Autowired
    private MainTitleEngRepo mainTitleEngRepo;
    @Autowired
    private ImgRepo imgRepo;
    @Autowired
    private FileTypeRepo fileTypeRepo;
    @Autowired
    private MediaTypeService mediaTypeService;
    @Autowired
    private MainDeleteService mainDeleteService;

    @DeleteMapping("/delete/{commonId}")
    public ResponseEntity<String> deleteByCommonId(@PathVariable String commonId) {
        mainDeleteService.deleteByCommonId(commonId);
        return ResponseEntity.ok("Deleted all entities associated with commonId: " + commonId);
    }

    @DeleteMapping(path = "/stringDelete/{uId}")
    public ResponseEntity<?> deleteMainData(@PathVariable String uId) {
        return mainDeleteService.deleteMainString(uId);
    }

    @DeleteMapping(path = "/deleteImages")
    public ResponseEntity<?> deleteImages(
            @RequestParam String commonId,
            @RequestParam(required = false) List<Integer> imgIds) {

        try {
            if (commonId == null) {
                return new ResponseEntity<>("Common ID is required", HttpStatus.BAD_REQUEST);
            } else {
                if (imgIds == null || imgIds.isEmpty()) {
                    // Delete all images associated with the commonId
                    mainDeleteService.deleteImagesByCommonId(commonId);
                } else {
                    // Delete specific images associated with the commonId
                    mainDeleteService.deleteImagesByCommonIdAndIds(commonId, imgIds);
                }
                return new ResponseEntity<>("Images deleted successfully", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}