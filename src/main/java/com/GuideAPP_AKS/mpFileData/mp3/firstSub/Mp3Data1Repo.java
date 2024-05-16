package com.GuideAPP_AKS.mpFileData.mp3.firstSub;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Mp3Data1Repo extends JpaRepository<Mp3Data1,Integer> {
    List<Mp3Data1> findBydtId(String fsUid);
}
