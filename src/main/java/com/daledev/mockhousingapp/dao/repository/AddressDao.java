package com.daledev.mockhousingapp.dao.repository;

import com.daledev.mockhousingapp.dao.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Shane.Sturgeon on 06/03/2018.
 */
public interface AddressDao extends JpaRepository<Address, Long> {

}
