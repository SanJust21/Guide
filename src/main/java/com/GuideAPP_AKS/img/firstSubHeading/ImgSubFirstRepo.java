package com.GuideAPP_AKS.img.firstSubHeading;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImgSubFirstRepo extends JpaRepository<ImgSubFirst,Integer> {
    List<ImgSubFirst> findByengId(String fsUid);

    List<ImgSubFirst> findBymalId(String fsUid);

    List<ImgSubFirst> findByEngId(String fsUid);

    List<ImgSubFirst> findByCommonId(String commonId);

    void deleteByCommonId(String commonId);

    void deleteAllByengId(String fsEngId);

    Optional<ImgSubFirst> findByImgIDAndCommonId(Integer imgId, String commonId);
}
