package com.southwind.phone_store_demo.repository;

import com.southwind.phone_store_demo.entity.BuyerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerAddressRepository extends JpaRepository<BuyerAddress, Integer> {
    public void deleteByAddressId(Integer addressId);
}
