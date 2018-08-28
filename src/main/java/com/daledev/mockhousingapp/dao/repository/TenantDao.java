package com.daledev.mockhousingapp.dao.repository;

import com.daledev.mockhousingapp.dao.domain.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dale.ellis
 * @since 14/12/2017
 */
public interface TenantDao extends JpaRepository<Tenant, Long> {
}
