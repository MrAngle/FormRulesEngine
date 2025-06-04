package apiv2.dataprocurment.api.controller.openapi;

import apiv2.dataprocurment.api.controller.ApiPaths;
import apiv2.dataprocurment.api.dto.request.UpdateVehicleDataRequest;
import apiv2.dataprocurment.api.dto.response.VehicleDataResponse;
import apiv2.openapicustomizer.OpenApiExampleEntry;
import apiv2.openapicustomizer.OpenApiExampleSet;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class GetVehicleDataExamples implements OpenApiExampleSet {

    @Override
    public String getPath() {
        return ApiPaths.VEHICLE_DATA;
    }

    @Override
    public HttpMethod getMethodHttp() {
        return HttpMethod.GET;
    }

    @Override
    public List<OpenApiExampleEntry> getExampleRequestBodies() {
        return List.of(
            new OpenApiExampleEntry() {
                public String summary() { return "Poprawny request"; }
                public String description() { return "VIN i rejestracja obecne"; }
                public Object exampleModel() {
                    return UpdateVehicleDataRequest.builder()
                            .vin("1HGCM82633A004352")
                            .registrationNumber("XYZ123")
                            .identificationNumber("1234567890")
                            .build();
                }
            },

            new OpenApiExampleEntry() {
                public String summary() { return "Poprawny request inny"; }
                public String description() { return "VIN i rejestracja obecne2"; }
                public Object exampleModel() {
                    return UpdateVehicleDataRequest.builder()
                            .vin("1HGCM82633A004352")
                            .registrationNumber("INNY")
                            .identificationNumber("1234567890")
                            .build();
                }
            }
        );
    }

    @Override
    public List<OpenApiExampleEntry> getExampleResponseBodies() {
        return List.of(
            new OpenApiExampleEntry() {
                public String summary() { return "Response"; }
                public String description() { return "VIN i rejestracja obecne"; }
                public Object exampleModel() {
                    return VehicleDataResponse.builder()
                            .vin("1HGCM82633A004352")
                            .registrationNumber("XYZ123")
                            .build();
                }
            },

            new OpenApiExampleEntry() {
                public String summary() { return "Response inny"; }
                public String description() { return "Response inny 2"; }
                public Object exampleModel() {
                    return VehicleDataResponse.builder()
                            .vin("1HGCM82633A004352")
                            .registrationNumber("XYZ123")
                            .build();
                }
            }
        );
    }
}
