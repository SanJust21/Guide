package com.GuideAPP_AKS.img.mainHeading;

import com.GuideAPP_AKS.img.mainHeading.ImgData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ImgRepo extends JpaRepository<ImgData,Integer> {


    List<ImgData> findBymalId(String mMalUid);

    List<ImgData> findByengId(String mEngUid);

    List<ImgData> findByCommonId(String commonId);

    Optional<ImgData> findByImgIDAndCommonId(Integer imgId, String commonId);

    void deleteByCommonId(String commonId);
}
