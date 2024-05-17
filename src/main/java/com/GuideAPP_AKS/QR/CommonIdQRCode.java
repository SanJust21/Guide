package com.GuideAPP_AKS.QR;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "common_id_qrcode")
public class CommonIdQRCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "common_id")
    private String commonId;

    @Column(name = "malId")
    private String malId;

    @Column(name = "engId")
    private String engId;

    @Lob
    @Column(name = "qr_code")
    private byte[] qrCode;
}