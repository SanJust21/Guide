package com.GuideAPP_AKS.firstSubHeading.english;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FirstSubEnglishRepo extends JpaRepository<FirstSubEnglish,Integer> {
    //FirstSubEnglish findBymEngUid(String uId);

    FirstSubEnglish findByFsUid(String uId);

    List<FirstSubEnglish> findByMainUid(String mainId);

    Optional<FirstSubEnglish> findByfsUid(String englishUId);

    void deleteByMainUid(String mEngUid);

    List<FirstSubEnglish> findAllByMainUid(String mEngUid);

    void deleteByfsUid(String fsId);


    Optional<FirstSubEnglish> findBytitle(String title);
}
