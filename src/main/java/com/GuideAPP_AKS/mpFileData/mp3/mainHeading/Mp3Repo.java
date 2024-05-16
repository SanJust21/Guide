package com.GuideAPP_AKS.mpFileData.mp3.mainHeading;

import com.GuideAPP_AKS.mpFileData.mp3.mainHeading.Mp3Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Mp3Repo extends JpaRepository<Mp3Data,Integer> {
    List<Mp3Data> findBydtId(String s);
}
