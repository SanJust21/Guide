package com.GuideAPP_AKS.QR;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/qrcode")
@CrossOrigin
public class QRCodeController {

    @Autowired
    private QRCodeGenerateService qrCodeGenerateService;

    @Autowired
    private CommonIdQRCodeRepo commonIdQRCodeRepo;

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
}







