package com.GuideAPP_AKS.firstSubHeading.malayalam;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FirstSubMalayalamRepo extends JpaRepository<FirstSubMalayalam,Integer> {
    //Optional<FirstSubMalayalam> findBymMalUid(String uId);

    Optional<FirstSubMalayalam> findByFsUid(String uId);

    Optional<FirstSubMalayalam> findByfsUid(String malUid);
}
