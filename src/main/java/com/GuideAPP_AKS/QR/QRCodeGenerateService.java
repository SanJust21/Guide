package com.GuideAPP_AKS.QR;

import com.GuideAPP_AKS.mainHeading.mainMal.MainTitleMal;
import com.GuideAPP_AKS.mainHeading.mainEng.MainTitleEng;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.GuideAPP_AKS.mainHeading.mainEng.MainTitleEngRepo;
import com.GuideAPP_AKS.mainHeading.mainMal.MainTitleMalRepo;
import com.google.zxing.WriterException;
import com.GuideAPP_AKS.util.AlphaNumeric;

import java.io.ByteArrayOutputStream;
import java.util.UUID;
import java.util.Optional;

import java.io.IOException;

@Service
public class QRCodeGenerateService {

    @Autowired
    private MainTitleMalRepo mainTitleMalRepo;

    @Autowired
    private MainTitleEngRepo mainTitleEngRepo;

    @Autowired
    private CommonIdQRCodeRepo commonIdQRCodeRepo;

    @Autowired
    private AlphaNumeric alphaNumeric;

    public byte[] generateQRCodeAndSave(String mMalUid, String mEngUid) throws WriterException, IOException {
        MainTitleMal mainTitleMal = mainTitleMalRepo.findBymMalUid(mMalUid).orElse(null);
        Optional<MainTitleEng> mainTitleEng = Optional.ofNullable(mainTitleEngRepo.findBymEngUid(mEngUid));

        if (mainTitleMal == null || mainTitleEng == null) {
            throw new IllegalArgumentException("Data not found in the repositories");
        }

        String commonId = alphaNumeric.generateRandomNumber();
        String qrContent = "CommonId: " + commonId;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 250, 250);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] qrCode = pngOutputStream.toByteArray();

        CommonIdQRCode commonIdQRCode = new CommonIdQRCode();
        commonIdQRCode.setCommonId(commonId);
        commonIdQRCode.setMalId(mMalUid);
        commonIdQRCode.setEngId(mEngUid);
        commonIdQRCode.setQrCode(qrCode);

        commonIdQRCodeRepo.save(commonIdQRCode);

        return qrCode;
    }

}
