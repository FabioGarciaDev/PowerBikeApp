package com.PowerBike.repository;

import com.PowerBike.entity.OrderDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends CrudRepository<OrderDetails, Long> {

}
