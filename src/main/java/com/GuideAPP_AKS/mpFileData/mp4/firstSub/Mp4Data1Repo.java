package com.GuideAPP_AKS.mpFileData.mp4.firstSub;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Mp4Data1Repo extends JpaRepository<Mp4Data1,Integer> {
    List<Mp4Data1> findBydtId(String fsUid);

    List<Mp4Data1> findByMainEngId(String fsUid);

    void deleteByMainEngId(String mEngUid);

    void deleteByMainMalId(String mMalUid);

    List<Mp4Data1> findByMainEngIdAndMainMalId(String mainEngId, String mainMalId);

    List<Mp4Data1> findByMainMalId(String mainMalId);

    Optional<Mp4Data1> findByDtId(String dtId);

    void deleteAllBydtId(String fsEngId);
}
