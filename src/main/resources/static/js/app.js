(function () {
    var app = angular.module('cxmHousingApp', ['angularUtils.directives.dirPagination']);


    app.controller("tenancies", function ($scope, $http, $location, tenancyDataService) {

        var self = this;

        self.view = "home";

        // Generate page default values
        self.generateData = {};
        self.generateData.total = 1;
        self.generateData.tenants = 2;


        self.showHeader = self.hideInFive;
        self.hideHeader = function () {
            self.showHeader = false;
        };

        self.hideInFive = function () {
            setTimeout(function () {
                return false;
            }, 5000);
        };

        $scope.tenacyData = tenancyDataService.getTenancies(1);
        $scope.getTenacyData = tenancyDataService.getTenancies;

        $scope.sort = function (keyname) {
            $scope.sortKey = keyname;   //set the sortKey to the param passed
            $scope.reverse = !$scope.reverse; //if true make it false and vice versa
        };

        self.isCurrentView = function (view) {
            return self.view === view;
        };

        self.showHome = function () {
            self.view = "home";
        };

        self.showCreateForm = function () {
            self.view = "create";
        };

        self.showGenerateForm = function () {
            self.view = "generate";
        };
        self.showCreateAddressForm = function () {
            self.view = "address";
        };
        self.showInspectionArea = function () {
            self.view = "inspection";
            self.loadInspection();
        };
        self.editTenancy = function (tenancyData) {
            self.view = "edit";
            self.selectedTenancy = tenancyData;
            self.hasAddress = self.selectedTenancy.address !== null;
        };

        self.deleteTenancy = function (tenancyData) {
            $http.delete("tenancy/" + tenancyData.id).then(function (response) {
                $scope.tenacyData = tenancyDataService.getTenancies(1);
            });
        };

        self.deleteTenant = function (tenantData) {
            for (var i = 0; i < self.selectedTenancy.tenants.length; i++) {
                if (self.selectedTenancy.tenants[i] === tenantData) {
                    self.selectedTenancy.tenants.splice(i, 1);
                }
            }

            $http.delete("tenant/" + tenantData.id).then(function (response) {
                console.log("statusCode: " + response.status);
                console.log("headers: " + response.headers);
            });
        };

        self.addAddressToTenancy = function () {
            var request = {};
            request.tenancyId = self.selectedTenancy.id;
            request.addressId = self.selectedAddress;
            var json = JSON.stringify(request);
            $http.post("tenancy/addAddress", json, {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(function (data) {
                $scope.tenacyData = tenancyDataService.getTenancies(1);
                self.view = "home";
            });
        };

        self.getAddress = function (postcode) {
            var addAllUrl = "address/add/postcode/" + postcode;
            $http.post(addAllUrl, {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(function (response) {
                self.loadAddresses();
            });
        };

        self.isPersonValid = function (person) {

            if (person.firstName === undefined || person.firstName.length < 2) {
                return false;
            }
            if (person.lastName === undefined || person.lastName.length < 2) {
                return false;
            }

            if (person.email === undefined) {
                return false;
            }
            return true;
        };
        self.addTenant = function () {
            var newTenantData = {};
            newTenantData.tenancyReference = self.selectedTenancy.id;
            newTenantData.editing = true;
            self.selectedTenancy.tenants.push(newTenantData);
        };

        self.updateTenant = function (tenantData) {

            var json = JSON.stringify(tenantData);

            $http.post("tenant", json, {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(function (response) {
                var data = response.data;
                tenantData.editing = false;

                // Replace row with new data (ID may now be set)
                for (var i = 0; i < self.selectedTenancy.tenants.length; i++) {
                    if (self.selectedTenancy.tenants[i] === tenantData) {
                        self.selectedTenancy.tenants[i] = data;
                    }
                }
            });
        };
        self.addAddress = function () {
            var json = JSON.stringify(self.address);
            $http.post("address/add", json, {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(function (data) {
                self.address.line1 = "";
                self.address.line2 = "";
                self.address.line3 = "";
                self.address.postcode = "";
                self.address.town = "";
            });
        };

        self.loadAddresses = function () {
            $http.get("address/getAddresses"
            ).then(function (data) {
                self.addressList = data.data.page;
            });
        };

        self.loadAddresses();

        self.createInspection = function () {
            var json = JSON.stringify(self.inspectionArea);
            $http.post("inspectionArea/add", json, {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(function (data) {
                self.loadInspection();
                self.view = "inspection";
                self.inspectionArea.name = "";
                self.inspectionArea.addresses.id = "";
            });

        };

        self.loadInspection = function () {
            $http.get("inspectionArea/getAll"
            ).then(function (data) {
                self.inspectionList = data.data.page;
                console.log(self.inspectionList);
            });
        };

        self.create = function () {
            var json = JSON.stringify(self.createData);

            $http.post("tenancy/add", json, {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(function (data) {
                $scope.tenacyData = tenancyDataService.getTenancies(1);
                self.view = "home";
            });
        };

        self.generate = function () {
            var json = JSON.stringify(self.generateData);

            $http.post("tenancy/generate", json, {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(function (data) {
                $scope.tenacyData = tenancyDataService.getTenancies(1);
                self.view = "home";
            });
        }
    });

    app.directive("tenancyTable", function () {
        return {
            restrict: 'E',
            templateUrl: "tenancy-table.html"
        };
    });

    app.directive("createTenancyForm", function () {
        return {
            restrict: 'E',
            templateUrl: "create-tenancy-form.html"
        };
    });

    app.directive("editTenancy", function () {
        return {
            restrict: 'E',
            templateUrl: "edit-tenancy.html"
        };
    });
    app.directive("generateTenancies", function () {
        return {
            restrict: 'E',
            templateUrl: "generate-tenancies.html"
        };
    });
    app.directive("createAddressForm", function () {
        return {
            restrict: 'E',
            templateUrl: "create-address-form.html"
        };
    });
    app.directive("inspectionArea", function () {
        return {
            restrict: 'E',
            templateUrl: "inspection-area.html"
        };
    });

    // app.filter('myFilter', function($rootScope, $http, tenancyDataService){
    //     return function(x, searchParam){
    //         return tenancyDataService.getTenancies(1, searchParam);
    //     };
    // });

    app.factory("tenancyDataService", function ($rootScope, $http) {
        var tenancyDataService = this;
        tenancyDataService.data = [];
        tenancyDataService.data.itemsPerPage = 15;
        tenancyDataService.data.pagenumber = 1;
        tenancyDataService.data.searchCriteria = "";
        tenancyDataService.data.sort = "";
        tenancyDataService.data.sortDir = "";

        tenancyDataService.getTenancies = function (pagenumber, searchParam, sort, sortDir) {
            if (searchParam) {
                tenancyDataService.data.searchCriteria = searchParam;
            }

            if (sort) {
                tenancyDataService.data.sort = sort;
            }

            if (typeof sortDir !== "undefined") {
                tenancyDataService.data.sortDir = sortDir;
            }
            tenancyDataService.loading = true;
            var url = "tenancy/" + tenancyDataService.data.itemsPerPage + "/" + pagenumber + "?a=a";

            if (tenancyDataService.data.sortDir !== "undefined" && tenancyDataService.data.sortDir !== "") {
                url += "&sortDir=" + encodeURIComponent(tenancyDataService.data.sortDir);
            }
            if (tenancyDataService.data.sort) {
                url += "&sort=" + encodeURIComponent(tenancyDataService.data.sort);
            }
            if (tenancyDataService.data.searchCriteria) {
                url += "&searchParam=" + encodeURIComponent(tenancyDataService.data.searchCriteria);
            }


            $http.get(url).then(function (response) {
                var data = response.data;
                tenancyDataService.loading = false;
                tenancyDataService.data.totalItems = data.total;
                tenancyDataService.data.totalPages = data.totalPages;
                tenancyDataService.data.page = data.page;
            });
            return tenancyDataService.data;
        };
        tenancyDataService.data.getData = tenancyDataService.getTenancies;
        return tenancyDataService;
    });

    app.filter('myFilter', function ($http, tenancyDataService) {
        var previousFilter = null;
        return function (x, input) {
            if (previousFilter !== input) {
                previousFilter = input;
                tenancyDataService.getTenancies(1, input);
            }
            return tenancyDataService.data.page;
        };
    });

    app.filter('mySort', function (tenancyDataService) {
        var previousSort = null;
        var previousSortDir = null;
        return function (data, sort, sortDir) {
            if (previousSort !== sort || previousSortDir !== sortDir) {
                previousSort = sort;
                previousSortDir = sortDir;
                tenancyDataService.getTenancies(1, "", sort, sortDir);
            }
            return tenancyDataService.data.page;
        }
    });

})();