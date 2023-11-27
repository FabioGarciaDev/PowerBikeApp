package com.PowerBike.repository;

import com.PowerBike.entity.OrderEntity;
import com.PowerBike.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByClient(UserEntity client);

}
