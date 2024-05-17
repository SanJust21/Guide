package com.GuideAPP_AKS.img.secondSubHeading;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
@Data
@Entity
@Table(name = "imgSub2")
@CrossOrigin
@Component
public class ImgSubSecond {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imgID")
    private Integer imgID;

    @Column(name = "fName")
    private String fName;

    @Column(name = "fUrl")
    private String fUrl;

    @Column(name = "engId")
    private String engId;

    @Column(name = "malId")
    private String malId;

    @Column(name = "fsEngUid")
    private String fsEngUid;

    @Column(name = "fsMalUid")
    private String fsMalUid;

    public ImgSubSecond() {
    }
    @PrePersist
    @PreUpdate
    public void setDefault(){
        if (fName==null){
            fName="No Data";
        }if (fUrl==null){
            fUrl="No Data";
        }if (engId==null){
            engId="No Data";
        }if (malId==null){
            malId="No Data";
        }
    }

    public ImgSubSecond(String fName, String fUrl, String engId, String malId) {
        this.fName = fName;
        this.fUrl = fUrl;
        this.engId = engId;
        this.malId = malId;
    }
}
