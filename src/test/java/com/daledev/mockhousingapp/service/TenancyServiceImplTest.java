package com.daledev.mockhousingapp.service;

import com.daledev.mockhousingapp.dao.domain.Address;
import com.daledev.mockhousingapp.dao.domain.Tenancy;
import com.daledev.mockhousingapp.dao.repository.TenancyDao;
import com.daledev.mockhousingapp.rest.dto.AddAddressToTenancyRequestDto;
import com.daledev.mockhousingapp.rest.dto.CreateTenancyRequestDto;
import com.daledev.mockhousingapp.rest.dto.GenerateRequestDto;
import com.daledev.mockhousingapp.rest.dto.TenancyDto;
import com.daledev.mockhousingapp.exception.AddressNotFoundException;
import com.daledev.mockhousingapp.exception.TenanyNotFoundException;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.*;

/**
 * @author dale.ellis
 * @since 24/08/2018
 */
@RunWith(Enclosed.class)
public class TenancyServiceImplTest {

    public static class CreateTenancy extends TenancyTests {
        private Address addressToBeReturned = new Address();

        @Test
        public void givenValidTenancyDetailsWThenTenancyDtoWillBeReturn() {
            // Given
            CreateTenancyRequestDto request = setupRequest(null);
            setupExpectations();

            // When
            TenancyDto response = tenancyService.createTenancy(request);

            // Then
            assertNotNull(response);
            context.assertIsSatisfied();
        }

        @Test
        public void givenValidTenancyDetailsWithoutAddressThenTenancyDtoAddressWillBeNull() {
            // Given
            CreateTenancyRequestDto request = setupRequest(null);
            setupExpectations();

            // When
            TenancyDto response = tenancyService.createTenancy(request);

            // Then
            assertNull(response.getAddress());
            context.assertIsSatisfied();
        }

        @Test
        public void givenValidTenancyDetailsWithAddressThenTenancyDtoAddressWillBePopulated() {
            // Given
            Long addressId = 10L;
            CreateTenancyRequestDto request = setupRequest(addressId);
            setupExpectations(addressId);

            // When
            TenancyDto response = tenancyService.createTenancy(request);

            // Then
            assertNotNull(response.getAddress());
            context.assertIsSatisfied();
        }

        private CreateTenancyRequestDto setupRequest(Long addressId) {
            CreateTenancyRequestDto request = new CreateTenancyRequestDto();
            request.setTitle("Mr");
            request.setFirstName("John");
            request.setLastName("Smith");
            request.setEmail("john.smith@test.com");
            request.setAddressId(addressId);
            return request;
        }

        private void setupExpectations() {
            setupExpectations(null);
        }

        private void setupExpectations(Long addressId) {
            expectSaveTenancyToBeCalled();
            expectGetAddressToBeCalledWithGivenIdAndReturnGivenValue(addressId, addressToBeReturned);
        }
    }

    public static class deleteTenancy extends TenancyTests {
        @Test
        public void givenValidIdThenTenancyWIllBeDeleted() {
            // Given
            Long tenancyId = 1L;
            expectTenancyExistsToBeCalledForGivenIdReturningGivenValue(tenancyId, true);
            expectDeleteTenancyToBeCalledForGivenId(tenancyId);

            // When
            tenancyService.deleteTenancy(tenancyId);

            // Then
            context.assertIsSatisfied();
        }

        @Test(expected = TenanyNotFoundException.class)
        public void givenNonExistentIdThenExceptionThrown() {
            // Given
            Long tenancyId = 1L;
            expectTenancyExistsToBeCalledForGivenIdReturningGivenValue(tenancyId, false);

            // When
            tenancyService.deleteTenancy(tenancyId);
        }
    }

    public static class generateTenancies extends TenancyTests {
        @Test
        public void givenNumberOfTenanciesAndTenantsToGenerateSaveWillBeCalledThatManyTimes() {
            // Given
            int tenanciesToBeCreated = 10;
            int tenantsPerTenancy = 3;
            GenerateRequestDto request = createGenerateRequestDto(tenanciesToBeCreated, tenantsPerTenancy);

            expectGenerateRandomDataMethodsToBeCalled(request);
            expectSaveTenancyToBeInvokedGivenNumberOfTImes(tenanciesToBeCreated);

            // When
            tenancyService.generateTenancies(request);

            // Then
            context.assertIsSatisfied();
        }

        private GenerateRequestDto createGenerateRequestDto(int tenanciesToBeCreated, int tenantsPerTenancy) {
            GenerateRequestDto request = new GenerateRequestDto();
            request.setTotal(tenanciesToBeCreated);
            request.setTenants(tenantsPerTenancy);
            return request;
        }

        void expectGenerateRandomDataMethodsToBeCalled(GenerateRequestDto request) {
            expectGenerateRandomDateToBeCalled();
            expectGenerateRandomTitleToBeCalled(request);
            expectGenerateRandomNameToBeCalled(request);
        }

        void expectGenerateRandomDateToBeCalled() {
            context.checking(new Expectations() {{
                allowing(generatorService).generateRandomDate(with(any(int.class)), with(any(int.class)));
            }});
        }

        void expectGenerateRandomTitleToBeCalled(GenerateRequestDto request) {
            int totalCalls = request.getTotal() * request.getTenants();
            context.checking(new Expectations() {{
                exactly(totalCalls).of(generatorService).generateTitle();
            }});
        }

        void expectGenerateRandomNameToBeCalled(GenerateRequestDto request) {
            int totalCalls = request.getTotal() * request.getTenants() * 2; // First and last name
            context.checking(new Expectations() {{
                exactly(totalCalls).of(generatorService).generateName();
            }});
        }

        void expectSaveTenancyToBeInvokedGivenNumberOfTImes(int numberOfInvokes) {
            context.checking(new Expectations() {{
                exactly(numberOfInvokes).of(tenancyDao).save(with(any(Tenancy.class)));
            }});
        }
    }

    public static class setAddressForTenancy extends TenancyTests {
        private AddAddressToTenancyRequestDto request;
        private Tenancy tenancy;
        private Address address;

        @Before
        public void setup() {
            request = createRequest(10L, 1L);

            tenancy = new Tenancy();
            address = new Address();

            givenTenanyDaoFindOneWillReturnTenancy();
            givenAddressServiceGetAddressWillReturnAddress();
        }

        @Test
        public void givenValidTenancyAndAddressIdsThenSaveSuccessful() {
            // Given
            givenValidateTenancyExistsForGivenIdThenGivenValueReturned(request, true);
            givenValidateAddressExistsForGivenIdThenGivenValueReturned(request, true);

            // When
            tenancyService.setAddressForTenancy(request);

            // Then
            assertEquals(address, tenancy.getAddress());
            context.assertIsSatisfied();
        }

        @Test(expected = TenanyNotFoundException.class)
        public void givenInValidTenancyExceptionExpected() {
            // Given
            givenValidateTenancyExistsForGivenIdThenGivenValueReturned(request, false);
            givenValidateAddressExistsForGivenIdThenGivenValueReturned(request, true);

            // When
            tenancyService.setAddressForTenancy(request);
        }

        @Test(expected = AddressNotFoundException.class)
        public void givenInValidAddressExceptionExpected() {
            // Given
            givenValidateTenancyExistsForGivenIdThenGivenValueReturned(request, true);
            givenValidateAddressExistsForGivenIdThenGivenValueReturned(request, false);

            // When
            tenancyService.setAddressForTenancy(request);
        }

        private AddAddressToTenancyRequestDto createRequest(Long tenancyId, Long addressId) {
            AddAddressToTenancyRequestDto request = new AddAddressToTenancyRequestDto();
            request.setTenancyId(tenancyId);
            request.setAddressId(addressId);
            return request;
        }

        private void givenValidateTenancyExistsForGivenIdThenGivenValueReturned(AddAddressToTenancyRequestDto request, boolean result) {
            context.checking(new Expectations() {{
                oneOf(tenancyDao).exists(request.getTenancyId());
                will(returnValue(result));
            }});
        }

        private void givenValidateAddressExistsForGivenIdThenGivenValueReturned(AddAddressToTenancyRequestDto request, boolean result) {
            context.checking(new Expectations() {{
                oneOf(addressService).addressExists(request.getAddressId());
                will(returnValue(result));
            }});
        }

        private void givenTenanyDaoFindOneWillReturnTenancy() {
            context.checking(new Expectations() {{
                oneOf(tenancyDao).findOne(request.getTenancyId());
                will(returnValue(tenancy));
            }});
        }

        private void givenAddressServiceGetAddressWillReturnAddress() {
            context.checking(new Expectations() {{
                oneOf(addressService).getAddress(request.getAddressId());
                will(returnValue(address));
            }});
        }
    }

    public abstract static class TenancyTests {
        Mockery context = new Mockery();

        TenancyServiceImpl tenancyService;

        GeneratorService generatorService;
        TenancyDao tenancyDao;
        AddressService addressService;

        @Before
        public void setUp() throws Exception {
            generatorService = context.mock(GeneratorService.class);
            tenancyDao = context.mock(TenancyDao.class);
            addressService = context.mock(AddressService.class);
            tenancyService = new TenancyServiceImpl(generatorService, tenancyDao, addressService, new ModelMapper());
        }

        void expectTenancyExistsToBeCalledForGivenIdReturningGivenValue(Long tenancyId, boolean exists) {
            context.checking(new Expectations() {{
                oneOf(tenancyDao).exists(tenancyId);
                will(returnValue(exists));
            }});
        }

        void expectGetAddressToBeCalledWithGivenIdAndReturnGivenValue(Long addressId, Address address) {
            if (addressId != null && address != null) {
                address.setId(addressId);
                address.setLine1("Line 1");
            }

            context.checking(new Expectations() {{
                oneOf(addressService).getAddress(addressId);
                will(returnValue(address));
            }});
        }

        void expectSaveTenancyToBeCalled() {
            context.checking(new Expectations() {{
                oneOf(tenancyDao).save(with(any(Tenancy.class)));
            }});
        }

        void expectDeleteTenancyToBeCalledForGivenId(Long tenancyId) {
            context.checking(new Expectations() {{
                oneOf(tenancyDao).delete(tenancyId);
            }});
        }
    }
}