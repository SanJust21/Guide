package com.GuideAPP_AKS.QR;

import com.GuideAPP_AKS.mainHeading.CombinedData;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.GuideAPP_AKS.mainHeading.MainTitleService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/qrcode")
@CrossOrigin
public class QRCodeController {

    @Autowired
    private QRCodeGenerateService qrCodeGenerateService;

    @Autowired
    private CommonIdQRCodeRepo commonIdQRCodeRepo;

    @Autowired
    private MainTitleService mainTitleService;

    @GetMapping("/generate")
    public ResponseEntity<byte[]> generateQRCode(@RequestParam String mMalUid, @RequestParam String mEngUid) {
        try {

            if (commonIdQRCodeRepo.existsByMalIdAndEngId(mMalUid, mEngUid)) {

                CommonIdQRCode existingQRCode = commonIdQRCodeRepo.findByMalIdAndEngId(mMalUid, mEngUid).orElse(null);
                if (existingQRCode != null) {
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                            .body(existingQRCode.getQrCode());
                }
            }

            byte[] qrCode = qrCodeGenerateService.generateQRCodeAndSave(mMalUid, mEngUid);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return ResponseEntity.ok().headers(headers).body(qrCode);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/getScanDetails")
    public ResponseEntity<List<CombinedData>> getAllMainTitleData(@RequestParam Integer dtId, @RequestParam String commonId) {
        return mainTitleService.getCombinedDataByCommonId(dtId, commonId);
    }

}







