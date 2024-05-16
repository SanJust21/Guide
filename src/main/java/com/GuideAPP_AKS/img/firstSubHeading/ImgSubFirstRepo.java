package com.GuideAPP_AKS.img.firstSubHeading;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImgSubFirstRepo extends JpaRepository<ImgSubFirst,Integer> {
    List<ImgSubFirst> findByengId(String fsUid);

    List<ImgSubFirst> findBymalId(String fsUid);

    List<ImgSubFirst> findByEngId(String fsUid);
}
