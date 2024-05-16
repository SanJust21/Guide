package com.GuideAPP_AKS.img.mainHeading;

import com.GuideAPP_AKS.img.mainHeading.ImgData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImgRepo extends JpaRepository<ImgData,Integer> {


    List<ImgData> findBymalId(String mMalUid);

    List<ImgData> findByengId(String mEngUid);
}
