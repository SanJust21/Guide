package com.GuideAPP_AKS.SecondSubHeading.commonId;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommonIdSsRepo extends JpaRepository<CommonIdSs,Integer> {
    CommonIdSs findByssCommonId(String id);

    Optional<CommonIdSs> findByssEngId(String ssUid);

    void deleteByssCommonId(String id);

    Optional<CommonIdSs> findByssMalId(String ssUid);

    CommonIdSs findBySsEngId(String ssUid);

    CommonIdSs findBySsMalId(String ssUid);
}