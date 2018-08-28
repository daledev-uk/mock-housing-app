package com.daledev.mockhousingapp.dao.repository;


import com.daledev.mockhousingapp.dao.domain.InspectionArea;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Shane.Sturgeon on 08/03/2018.
 */
public interface InspectionAreaDao extends JpaRepository<InspectionArea, Long> {
}
