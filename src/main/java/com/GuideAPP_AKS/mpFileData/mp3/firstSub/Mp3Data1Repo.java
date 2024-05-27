package com.GuideAPP_AKS.mpFileData.mp3.firstSub;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Mp3Data1Repo extends JpaRepository<Mp3Data1,Integer> {
    List<Mp3Data1> findBydtId(String fsUid);

    List<Mp3Data1> findByMainEngId(String fsUid);

    void deleteByMainEngId(String mEngUid);

    void deleteByMainMalId(String mMalUid);

    List<Mp3Data1> findByMainEngIdAndMainMalId(String mainEngId, String mainMalId);

    List<Mp3Data1> findByMainMalId(String mainMalId);
}
