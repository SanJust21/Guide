package com.GuideAPP_AKS.mpFileData.mp4.firstSub;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Mp4Data1Repo extends JpaRepository<Mp4Data1,Integer> {
    List<Mp4Data1> findBydtId(String fsUid);
}
