package com.GuideAPP_AKS.SecondSubHeading.english;

import com.GuideAPP_AKS.SecondSubHeading.english.SecondSubEnglish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SecondSubEnglishRepo extends JpaRepository<SecondSubEnglish,Integer> {
    Optional<SecondSubEnglish> findByssUid(String englishUId);


    List<SecondSubEnglish> findByFsUidIn(List<String> collect);

    Optional<SecondSubEnglish> findByFsUid(String englishUId);

    void deleteByFsUid(String fsUid);

    List<SecondSubEnglish> findAllByFsUid(String fsUid);

    List<SecondSubEnglish> findAllByOrderByIdAsc();

    Optional<SecondSubEnglish> findBytitle(String title);
}
