package com.daledev.mockhousingapp.service;

import com.daledev.mockhousingapp.dao.domain.Address;
import com.daledev.mockhousingapp.dao.domain.Tenancy;
import com.daledev.mockhousingapp.dao.repository.TenancyDao;
import com.daledev.mockhousingapp.rest.dto.AddAddressToTenancyRequestDto;
import com.daledev.mockhousingapp.rest.dto.CreateTenancyRequestDto;
import com.daledev.mockhousingapp.rest.dto.GenerateRequestDto;
import com.daledev.mockhousingapp.rest.dto.TenancyDto;
import com.daledev.mockhousingapp.dao.domain.Tenant;
import com.daledev.mockhousingapp.exception.AddressNotFoundException;
import com.daledev.mockhousingapp.exception.TenanyNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author dale.ellis
 * @since 15/12/2017
 */
@Service
@Transactional
public class TenancyServiceImpl implements TenancyService {
    private static final Logger log = LoggerFactory.getLogger(TenancyServiceImpl.class);

    private GeneratorService generatorService;
    private TenancyDao tenancyDao;
    private AddressService addressService;
    private ModelMapper modelMapper;

    /**
     * @param generatorService
     * @param tenancyDao
     * @param addressService
     * @param modelMapper
     */
    public TenancyServiceImpl(GeneratorService generatorService, TenancyDao tenancyDao, AddressService addressService, ModelMapper modelMapper) {
        this.generatorService = generatorService;
        this.tenancyDao = tenancyDao;
        this.addressService = addressService;
        this.modelMapper = modelMapper;
    }

    @Override
    public TenancyDto createTenancy(CreateTenancyRequestDto tenancyDto) {
        log.debug("Creating tenancy");
        Tenancy tenancy = new Tenancy();
        tenancy.setStartDate(tenancyDto.getStartDate());
        tenancy.setEndDate(tenancyDto.getEndDate());

        Tenant tenant = new Tenant();
        tenant.setTitle(tenancyDto.getTitle());
        tenant.setFirstName(tenancyDto.getFirstName());
        tenant.setLastName(tenancyDto.getLastName());
        tenant.setEmail(tenancyDto.getEmail());
        tenant.setLeadTenant(true);
        tenancy.getTenants().add(tenant);
        tenant.setTenancy(tenancy);
        tenancy.setAddress(addressService.getAddress(tenancyDto.getAddressId()));

        tenancy.updateNameAndEmailToLeadTenants();
        tenancyDao.save(tenancy);

        return modelMapper.map(tenancy, TenancyDto.class);
    }

    @Override
    public void deleteTenancy(Long tenancyReference) {
        log.debug("Deleting tenancy with reference : {}", tenancyReference);
        validateTenancyExists(tenancyReference);
        tenancyDao.delete(tenancyReference);
    }

    @Override
    public void generateTenancies(GenerateRequestDto request) {
        log.debug("Generating {} tenancies, each with {} tenants", request.getTotal(), request.getTenants());
        for (int i = 0; i < request.getTotal(); i++) {
            generateTenancy(request.getTenants());
        }
    }

    @Override
    public void setAddressForTenancy(AddAddressToTenancyRequestDto request) {
        validateTenancyExists(request.getTenancyId());
        validateAddressExists(request.getAddressId());

        Tenancy tenancy = tenancyDao.findOne(request.getTenancyId());
        Address address = addressService.getAddress(request.getAddressId());
        tenancy.setAddress(address);
    }

    private void validateTenancyExists(long tenancyId) {
        if (!tenancyDao.exists(tenancyId)) {
            throw new TenanyNotFoundException(tenancyId);
        }
    }

    private void validateAddressExists(long addressId) {
        if (!addressService.addressExists(addressId)) {
            throw new AddressNotFoundException(addressId);
        }
    }

    private void generateTenancy(int numberOfTenants) {
        Tenancy tenancy = new Tenancy();
        tenancy.setStartDate(generatorService.generateRandomDate(1950, 2010));
        tenancy.setEndDate(generatorService.generateRandomDate(2010, 2017));

        generateTenantsForTenancy(numberOfTenants, tenancy);

        tenancy.updateNameAndEmailToLeadTenants();
        tenancyDao.save(tenancy);
    }

    private void generateTenantsForTenancy(int numberOfTenants, Tenancy tenancy) {
        for (int i = 0; i < numberOfTenants; i++) {
            Tenant tenant = generateTenant(tenancy);
            tenancy.getTenants().add(tenant);
            tenant.setTenancy(tenancy);
        }
    }

    private Tenant generateTenant(Tenancy tenancy) {
        Tenant tenant = new Tenant();
        tenant.setTitle(generatorService.generateTitle());
        tenant.setFirstName(generatorService.generateName());
        tenant.setLastName(generatorService.generateName());
        tenant.setEmail(tenant.getFirstName() + "." + tenant.getLastName() + "@imnotreal.com");
        tenant.setLeadTenant(tenancy.getTenants().isEmpty());
        return tenant;
    }

}
