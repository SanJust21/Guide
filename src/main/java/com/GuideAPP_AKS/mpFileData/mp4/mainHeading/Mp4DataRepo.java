package com.GuideAPP_AKS.mpFileData.mp4.mainHeading;

import com.GuideAPP_AKS.mpFileData.mp4.mainHeading.Mp4Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Mp4DataRepo extends JpaRepository<Mp4Data,Integer> {
    List<Mp4Data> findBydtId(String s);
}
