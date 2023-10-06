package com.PowerBike.repository;

import com.PowerBike.entity.MedicalRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends CrudRepository<MedicalRecord,Long> {

    /*@Query("SELECT mr FROM MedicalRecord mr WHERE mr.user = ?1")
    MedicalRecord findMedicalRegisterByUserId(Long userId);*/

}
