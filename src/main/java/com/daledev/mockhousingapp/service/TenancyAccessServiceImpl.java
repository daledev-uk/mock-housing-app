package com.daledev.mockhousingapp.service;

import com.daledev.mockhousingapp.dao.domain.Tenancy;
import com.daledev.mockhousingapp.dao.repository.TenancyDao;
import com.daledev.mockhousingapp.dao.repository.TenantDao;
import com.daledev.mockhousingapp.rest.dto.ResultPageDto;
import com.daledev.mockhousingapp.rest.dto.TenancyDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author dale.ellis
 * @since 15/12/2017
 */
@Service
@Transactional
public class TenancyAccessServiceImpl implements TenancyAccessService {
    private static final Logger log = LoggerFactory.getLogger(InspectionAreaServiceImpl.class);

    private TenancyDao tenancyDao;
    private ModelMapper modelMapper;

    /**
     * @param tenancyDao
     * @param tenantDao
     * @param modelMapper
     */
    public TenancyAccessServiceImpl(TenancyDao tenancyDao, TenantDao tenantDao, ModelMapper modelMapper) {
        this.tenancyDao = tenancyDao;
        this.modelMapper = modelMapper;
    }

    /**
     * @param tenancyReference
     * @return
     */
    @Override
    public Tenancy getTenancy(long tenancyReference) {
        log.debug("Getting tenancy for reference : {}", tenancyReference);
        return tenancyDao.getOne(tenancyReference);
    }

    @Override
    public ResultPageDto<TenancyDto> findTenancies(int itemsPerPage, int pageNumber, String sortDir, String sort, String searchParam) {
        log.debug("Searching for tenancies with criteria : {}", searchParam);
        int pageIndex = pageNumber - 1;
        PageRequest pageRequest = createPageRequest(itemsPerPage, sortDir, sort, pageIndex);

        if (isNoCriteriaProvided(searchParam)) {
            return convertToResultPageDto(tenancyDao.findAll(pageRequest));
        } else {
            ExampleMatcher matcher = ExampleMatcher.matchingAny()
                    .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                    .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains())
                    .withMatcher("startDate", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("endDate", ExampleMatcher.GenericPropertyMatchers.exact());

            Tenancy searchObject = buildSearchObject(searchParam);
            Example<Tenancy> example = Example.of(searchObject, matcher);
            return convertToResultPageDto(tenancyDao.findAll(example, pageRequest));
        }
    }

    private PageRequest createPageRequest(int itemsPerPage, String sortDir, String sort, int pageIndex) {
        if (sort == null) {
            return createPageRequestWithoutSorting(itemsPerPage, pageIndex);
        } else {
            return createPageRequestWithSorting(itemsPerPage, sortDir, sort, pageIndex);
        }
    }

    private PageRequest createPageRequestWithoutSorting(int itemsPerPage, int pageIndex) {
        return new PageRequest(pageIndex, itemsPerPage);
    }

    private PageRequest createPageRequestWithSorting(int itemsPerPage, String sortDir, String sort, int pageIndex) {
        Sort.Direction sortDirection = sortDir.equals("true") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return new PageRequest(pageIndex, itemsPerPage, sortDirection, sort);
    }

    private boolean isNoCriteriaProvided(String searchParam) {
        return searchParam == null || Objects.equals(searchParam, "");
    }

    private Tenancy buildSearchObject(String searchParam) {
        Tenancy searchObject = new Tenancy();
        setIdFromSearchText(searchParam, searchObject);
        searchObject.setName(searchParam);
        setDatesFromSearchText(searchParam, searchObject);
        searchObject.setEmail(searchParam);
        return searchObject;
    }

    private void setIdFromSearchText(String searchParam, Tenancy searchObject) {
        try {
            searchObject.setId(Long.parseLong(searchParam));
        } catch (NumberFormatException pex) {
            // Do nothing, this is not a valid number
        }
    }

    private void setDatesFromSearchText(String searchParam, Tenancy searchObject) {
        String dateRegularExpression = "^\\d{1,2}/\\d{1,2}/\\d{4}$";
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if (searchParam.matches(dateRegularExpression)) {
            try {
                Date inputDate = dateFormat.parse(searchParam);
                searchObject.setStartDate(inputDate);
                searchObject.setEndDate(inputDate);
            } catch (ParseException e) {
                // do nothing, not a valid date
            }
        }
    }

    private ResultPageDto<TenancyDto> convertToResultPageDto(Page<Tenancy> all) {
        ResultPageDto<TenancyDto> results = new ResultPageDto<>();
        results.setPage(all.getContent().stream().map(tenancy -> modelMapper.map(tenancy, TenancyDto.class)).collect(Collectors.toList()));
        results.setTotal(all.getTotalElements());
        results.setTotalPages(all.getTotalPages());
        return results;
    }

}
