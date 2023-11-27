package com.PowerBike.repository;

import com.PowerBike.entity.OrderEntity;
import com.PowerBike.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {

    List<OrderEntity> findByClient(UserEntity client);

}
