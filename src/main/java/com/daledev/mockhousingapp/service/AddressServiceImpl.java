package com.daledev.mockhousingapp.service;

import com.daledev.mockhousingapp.dao.domain.Address;
import com.daledev.mockhousingapp.dao.repository.AddressDao;
import com.daledev.mockhousingapp.rest.dto.AddressDto;
import com.daledev.mockhousingapp.rest.dto.CreateAddressRequestDto;
import com.daledev.mockhousingapp.rest.dto.ResultPageDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Shane.Sturgeon
 * @since 06/03/2018.
 */
@Service
public class AddressServiceImpl implements AddressService {
    private static final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    private ModelMapper modelMapper;
    private AddressDao addressDao;
    private AddressLookupService addressLookupService;

    /**
     * @param modelMapper
     * @param addressDao
     * @param addressLookupService
     */
    public AddressServiceImpl(ModelMapper modelMapper, AddressDao addressDao, AddressLookupService addressLookupService) {
        this.modelMapper = modelMapper;
        this.addressDao = addressDao;
        this.addressLookupService = addressLookupService;
    }

    @Override
    public Address getAddress(Number addressId) {
        log.debug("Getting address for ID : {}", addressId);
        return addressId == null ? null : addressDao.getOne(addressId.longValue());
    }

    @Override
    public boolean addressExists(Number addressId) {
        log.debug("Checking address for ID {} exists", addressId);
        return addressId != null && addressDao.exists(addressId.longValue());
    }

    @Override
    public ResultPageDto<AddressDto> getAllAddresses() {
        log.debug("Getting all addresses");
        return convertToAddressResultsPageDto(addressDao.findAll());
    }

    @Override
    public AddressDto addAddress(CreateAddressRequestDto request) {
        log.debug("Creating address : {}", request);
        Address address = new Address();

        address.setLine1(request.getLine1());
        address.setLine2(request.getLine2());
        address.setLine3(request.getLine3());
        address.setPostcode(request.getPostcode().toUpperCase());
        address.setTown(request.getTown());

        addressDao.save(address);
        return modelMapper.map(address, AddressDto.class);
    }

    @Override
    public void deleteAddress(long id) {
        log.debug("Delete address for ID : {}", id);
        addressDao.delete(id);
    }


    @Override
    public void addAddressFromPostcode(String postCode) {
        log.debug("Fetching all addresses for postcode : {}", postCode);
        List<AddressDto> addressByPostCode = addressLookupService.findAddressByPostCode(postCode);
        for (AddressDto address : addressByPostCode) {
            addAddress(new CreateAddressRequestDto(address));
        }
    }

    private ResultPageDto<AddressDto> convertToAddressResultsPageDto(List<Address> addresses) {
        ResultPageDto<AddressDto> list = new ResultPageDto<>();
        list.setPage(addresses.stream().map(address -> modelMapper.map(address, AddressDto.class)).collect(Collectors.toList()));
        list.setTotalPages(1);
        list.setTotal(addresses.size());
        return list;
    }
}
