package com.GuideAPP_AKS.DELETE.mainTitle;

import com.GuideAPP_AKS.UPDATE.MainTitle.MainUpdateService;
import com.GuideAPP_AKS.img.mainHeading.ImgRepo;
import com.GuideAPP_AKS.mainHeading.mainEng.MainTitleEngRepo;
import com.GuideAPP_AKS.mainHeading.mainMal.MainTitleMalRepo;
import com.GuideAPP_AKS.mpFileData.MediaTypeService;
import com.GuideAPP_AKS.mpFileData.mp3.firstSub.Mp3Data1;
import com.GuideAPP_AKS.mpFileData.mp3.firstSub.Mp3Data1Repo;
import com.GuideAPP_AKS.mpFileData.mp4.firstSub.Mp4Data1;
import com.GuideAPP_AKS.mpFileData.mp4.firstSub.Mp4Data1Repo;
import com.GuideAPP_AKS.mpType.FileTypeRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


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

    @Autowired
    private Mp3Data1Repo mp3Data1Repo;

    @Autowired
    private Mp4Data1Repo mp4Data1Repo;


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

    @DeleteMapping(path = "/deleteImagesFirst")
    public ResponseEntity<?> deleteImagesFirst(
            @RequestParam String commonId,
            @RequestParam(required = false) List<Integer> imgIds) {

        try {
            if (commonId == null) {
                return new ResponseEntity<>("Common ID is required", HttpStatus.BAD_REQUEST);
            } else {
                if (imgIds == null || imgIds.isEmpty()) {
                    // Delete all images associated with the commonId
                    mainDeleteService.deleteImagesFirstByCommonId(commonId);
                } else {
                    // Delete specific images associated with the commonId
                    mainDeleteService.deleteImagesFirstByCommonIdAndIds(commonId, imgIds);
                }
                return new ResponseEntity<>("Images deleted successfully", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/deleteImagesSecond")
    public ResponseEntity<?> deleteImagesSecond(
            @RequestParam String commonId,
            @RequestParam(required = false) List<Integer> imgIds) {

        try {
            if (commonId == null) {
                return new ResponseEntity<>("Common ID is required", HttpStatus.BAD_REQUEST);
            } else {
                if (imgIds == null || imgIds.isEmpty()) {
                    // Delete all images associated with the commonId
                    mainDeleteService.deleteImagesSecondByCommonId(commonId);
                } else {
                    // Delete specific images associated with the commonId
                    mainDeleteService.deleteImagesSecondByCommonIdAndIds(commonId, imgIds);
                }
                return new ResponseEntity<>("Images deleted successfully", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/deleteMp3")
    public ResponseEntity<?> deleteMp3(@RequestParam String dtId) {
        try {
            mainDeleteService.deleteMp3ByDtId(dtId);
            return new ResponseEntity<>("MP3 deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/deleteMp4")
    public ResponseEntity<?> deleteMp4(@RequestParam String dtId) {
        try {
            mainDeleteService.deleteMp4ByDtId(dtId);
            return new ResponseEntity<>("MP4 deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/deleteMp3First")
    public ResponseEntity<?> deleteMp3First(@RequestParam String dtId) {
        try {
            Optional<Mp3Data1> mp3Data1Optional = mp3Data1Repo.findByDtId(dtId);
            if (mp3Data1Optional.isPresent()) {
                Mp3Data1 mp3Data1 = mp3Data1Optional.get();
                String mainEngId = mp3Data1.getMainEngId();
                String mainMalId = mp3Data1.getMainMalId();

                if (mainEngId != null) {
                    mainDeleteService.deleteMp3FirstByMainEngId(mainEngId);
                } else if (mainMalId != null) {
                    mainDeleteService.deleteMp3FirstByMainMalId(mainMalId);
                }

                return new ResponseEntity<>("MP3 deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("MP3 data not found", HttpStatus.NOT_FOUND);
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping(path = "/deleteMp4First")
    public ResponseEntity<?> deleteMp4First(@RequestParam String dtId) {
        try {
            Optional<Mp4Data1> mp4Data1Optional = mp4Data1Repo.findByDtId(dtId);
            if (mp4Data1Optional.isPresent()) {
                Mp4Data1 mp4Data1 = mp4Data1Optional.get();
                String mainEngId = mp4Data1.getMainEngId();
                String mainMalId = mp4Data1.getMainMalId();

                if (mainEngId != null) {
                    mainDeleteService.deleteMp4FirstByMainEngId(mainEngId);
                } else if (mainMalId != null) {
                    mainDeleteService.deleteMp4FirstByMainMalId(mainMalId);
                }

                return new ResponseEntity<>("MP3 deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("MP3 data not found", HttpStatus.NOT_FOUND);
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/deleteMp3Second")
    public ResponseEntity<?> deleteMp3Second(@RequestParam String dtId) {
        try {
            mainDeleteService.deleteMp3SecondByDtId(dtId);
            return new ResponseEntity<>("MP3 deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/deleteMp4Second")
    public ResponseEntity<?> deleteMp4Second(@RequestParam String dtId) {
        try {
            mainDeleteService.deleteMp4SecondByDtId(dtId);
            return new ResponseEntity<>("MP4 deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
