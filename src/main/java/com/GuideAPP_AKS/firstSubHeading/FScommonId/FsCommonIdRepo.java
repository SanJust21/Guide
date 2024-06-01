package com.GuideAPP_AKS.firstSubHeading.FScommonId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FsCommonIdRepo extends JpaRepository<CommonIdFs,Integer> {
    CommonIdFs findByfsEngId(String englishUId);

    CommonIdFs findByfsMalId(String malUid);

    CommonIdFs findByfsCommonId(String commonId);


    int deleteAllByfsCommonId(String commonId);
}
