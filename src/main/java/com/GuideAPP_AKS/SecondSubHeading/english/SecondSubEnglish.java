package com.GuideAPP_AKS.SecondSubHeading.english;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;

@Data
@Entity
@Table(name = "secondSubEng")
@CrossOrigin
public class SecondSubEnglish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", length = 6000)
    private String description;

    @Column(name = "ref")
    private String ref;

    @Column(name = "ssUid")
    private String ssUid;

    @Column(name ="fsUid")
    private String fsUid;

    public SecondSubEnglish() {
    }

    @PrePersist
    @PreUpdate
    public void setDefault(){
        if (description==null){
            description="No Data";
        }if (ref==null){
            ref="No Data";
        }
    }
}
