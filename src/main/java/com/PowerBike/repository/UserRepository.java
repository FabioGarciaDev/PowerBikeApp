package com.PowerBike.repository;

import com.PowerBike.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email) ;

    boolean existsById(long id);

    boolean existsByRecoveryPassword(String recoveryPassword);

}
