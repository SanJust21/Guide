package com.GuideAPP_AKS.firstSubHeading.malayalam;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FirstSubMalayalamRepo extends JpaRepository<FirstSubMalayalam,Integer> {
    //Optional<FirstSubMalayalam> findBymMalUid(String uId);

    Optional<FirstSubMalayalam> findByFsUid(String uId);

    Optional<FirstSubMalayalam> findByfsUid(String malUid);

    List<FirstSubMalayalam> findByMainUid(String mainId);

    void deleteByMainUid(String mMalUid);

    List<FirstSubMalayalam> findAllByMainUid(String mMalUid);

    List<FirstSubMalayalam> findAllByOrderByIdAsc();

    Optional<FirstSubMalayalam> findBytitle(String title);

    void deleteAllByfsUid(String fsMalId);
}
