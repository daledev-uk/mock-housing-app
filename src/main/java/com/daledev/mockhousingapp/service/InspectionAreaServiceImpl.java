package com.daledev.mockhousingapp.service;

import com.daledev.mockhousingapp.dao.domain.Address;
import com.daledev.mockhousingapp.dao.repository.AddressDao;
import com.daledev.mockhousingapp.dao.repository.InspectionAreaDao;
import com.daledev.mockhousingapp.rest.dto.CreateInspectionAreaRequestDto;
import com.daledev.mockhousingapp.rest.dto.InspectionAreaDto;
import com.daledev.mockhousingapp.rest.dto.ResultPageDto;
import com.daledev.mockhousingapp.dao.domain.InspectionArea;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Shane.Sturgeon on 08/03/2018.
 */
@Service
public class InspectionAreaServiceImpl implements InspectionAreaService {
    private static final Logger log = LoggerFactory.getLogger(InspectionAreaServiceImpl.class);

    private ModelMapper modelMapper;
    private InspectionAreaDao inspectionAreaDao;
    private AddressDao addressDao;

    /**
     * @param modelMapper
     * @param inspectionAreaDao
     * @param addressDao
     */
    public InspectionAreaServiceImpl(ModelMapper modelMapper, InspectionAreaDao inspectionAreaDao, AddressDao addressDao) {
        this.modelMapper = modelMapper;
        this.inspectionAreaDao = inspectionAreaDao;
        this.addressDao = addressDao;
    }

    @Override
    public InspectionAreaDto addInspectionArea(CreateInspectionAreaRequestDto request) {
        log.debug("Add inspection area");
        InspectionArea inspectionArea = new InspectionArea();
        inspectionArea.setName(request.getName());
        inspectionArea.setAddresses(request.getAddresses().stream().map(aLong -> addressDao.findOne(aLong)).collect(Collectors.toSet()));
        for (Address a : inspectionArea.getAddresses()) {
            a.setInspectionArea(inspectionArea);
        }
        inspectionAreaDao.save(inspectionArea);
        return modelMapper.map(inspectionArea, InspectionAreaDto.class);
    }

    @Override
    public ResultPageDto<InspectionAreaDto> getInspectionAreas() {
        log.debug("Getting all inspection areas");
        List<InspectionArea> all = inspectionAreaDao.findAll();
        return convertInspectionAreaToResultsPage(all);
    }

    private ResultPageDto<InspectionAreaDto> convertInspectionAreaToResultsPage(List<InspectionArea> list) {
        ResultPageDto<InspectionAreaDto> result = new ResultPageDto<>();
        result.setPage(list.stream().map(inspectionArea -> modelMapper.map(inspectionArea, InspectionAreaDto.class)).collect(Collectors.toList()));
        result.setTotal(list.size());
        result.setTotalPages(1);
        return result;
    }
}
