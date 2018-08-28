package com.daledev.mockhousingapp.service;

import com.daledev.mockhousingapp.rest.dto.AddressDto;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dale.ellis
 * @since 22/08/2018
 */
@Service
public class GetAddressIOAddressLookupService implements AddressLookupService {
    private static final Logger log = LoggerFactory.getLogger(GetAddressIOAddressLookupService.class);
    private static final String GET_BY_POSTCODE_URL = "https://api.getaddress.io/find/%s?api-key=%s";

    @Value("${address-lookup.api-key}")
    private String apiKey;

    @Override
    public List<AddressDto> findAddressByPostCode(String postCode) {
        RestTemplate restTemplate = new RestTemplate();

        String requestUrl = String.format(GET_BY_POSTCODE_URL, postCode, apiKey);
        log.debug("fetching all addresses for post code, via REST API : {}", requestUrl);
        String response = restTemplate.getForObject(requestUrl, String.class);

        return getAddressesFromResponseJson(postCode, response);
    }

    private List<AddressDto> getAddressesFromResponseJson(String postCode, String response) {
        log.trace("response : {}", response);
        List<AddressDto> retrievedAddresses = new ArrayList<>();

        try {
            JSONObject jsonObj = new JSONObject(response);
            JSONArray addresses = jsonObj.getJSONArray("addresses");
            for (int i = 0; i < addresses.length(); i++) {
                String commaSeparatedAddress = addresses.getString(i);
                AddressDto addressDto = convertToAddressDto(commaSeparatedAddress, postCode);
                retrievedAddresses.add(addressDto);
            }
        } catch (JSONException e) {
            log.error("Failed to extract address objects from response", e);
        }
        return retrievedAddresses;
    }

    private AddressDto convertToAddressDto(String commaSeparatedAddress, String postCode) {
        log.trace("Converting address CSV into address : {}", commaSeparatedAddress);
        String[] splitAddress = commaSeparatedAddress.split(",");

        AddressDto addressDto = new AddressDto();
        addressDto.setLine1(splitAddress[0]);
        addressDto.setLine2(splitAddress[1]);
        addressDto.setLine3(splitAddress[2]);
        addressDto.setTown(splitAddress[5]);
        addressDto.setPostcode(postCode.toUpperCase());

        return addressDto;
    }
}
