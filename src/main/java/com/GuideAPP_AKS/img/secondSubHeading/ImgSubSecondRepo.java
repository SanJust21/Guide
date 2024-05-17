package com.GuideAPP_AKS.img.secondSubHeading;

import com.GuideAPP_AKS.img.firstSubHeading.ImgSubFirst;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImgSubSecondRepo extends JpaRepository<ImgSubSecond,Integer> {
    List<ImgSubSecond> findByengId(String fsUid);

    List<ImgSubSecond> findByFsEngUid(String ssUid);

    List<ImgSubSecond> findByFsMalUid(String ssUid);

    List<ImgSubSecond> findBymalId(String ssUid);
}
