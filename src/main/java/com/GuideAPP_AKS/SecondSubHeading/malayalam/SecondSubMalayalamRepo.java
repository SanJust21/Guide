package com.GuideAPP_AKS.SecondSubHeading.malayalam;

import com.GuideAPP_AKS.SecondSubHeading.malayalam.SecondSubMalayalam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SecondSubMalayalamRepo extends JpaRepository<SecondSubMalayalam,Integer> {
    Optional<SecondSubMalayalam> findByssUid(String malUid);

    List<SecondSubMalayalam> findByFsUidIn(List<String> collect);

    Optional<SecondSubMalayalam> findByFsUid(String malUid);

    void deleteByFsUid(String fsUid);

    List<SecondSubMalayalam> findAllByFsUid(String fsUid);

    List<SecondSubMalayalam> findAllByOrderByIdAsc();

    Optional<SecondSubMalayalam> findBytitle(String title);

    List<SecondSubMalayalam> findByfsUid(String fsUid);
}
