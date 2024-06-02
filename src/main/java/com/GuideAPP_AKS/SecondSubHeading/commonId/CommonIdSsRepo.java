package com.GuideAPP_AKS.SecondSubHeading.commonId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonIdSsRepo extends JpaRepository<CommonIdSs,Integer> {
    CommonIdSs findByssCommonId(String id);

    void deleteByssCommonId(String id);
}
