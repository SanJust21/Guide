package com.GuideAPP_AKS.mainHeading.mainEng;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MainTitleEngRepo extends JpaRepository<MainTitleEng,Integer> {
    MainTitleEng findBymEngUid(String engUId);
}
