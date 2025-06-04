package apiv2.dataprocurment.api.controller.openapi;

import apiv2.dataprocurment.api.controller.ApiPaths;
import apiv2.dataprocurment.api.dto.request.VehicleDataRequest;
import apiv2.dataprocurment.api.dto.response.VehicleDataResponse;
import apiv2.openapicustomizer.OpenApiExampleEntry;
import apiv2.openapicustomizer.OpenApiExampleSet;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
class PutVehicleDataExamples implements OpenApiExampleSet {

    @Override
    public String getPath() {
        return ApiPaths.VEHICLE_DATA;
    }

    @Override
    public HttpMethod getMethodHttp() {
        return HttpMethod.PUT;
    }

    @Override
    public List<OpenApiExampleEntry> getExampleRequestBodies() {
        return List.of(
                new OpenApiExampleEntry() {
                    public String summary() { return "Pojazd z VIN"; }
                    public String description() { return "Podano tylko numer VIN, bez numeru rejestracyjnego."; }
                    public Object exampleModel() {
                        return VehicleDataRequest.builder()
                                .birthDate(LocalDate.of(1990, 5, 20))
                                .nationalId("1234567890")
                                .vinNumber("1HGCM82633A004352")
                                .registrationNumber(null)
                                .build();
                    }
                },

                new OpenApiExampleEntry() {
                    public String summary() { return "Pojazd z rejestracją"; }
                    public String description() { return "Podano tylko numer rejestracyjny, bez numeru VIN."; }
                    public Object exampleModel() {
                        return VehicleDataRequest.builder()
                                .birthDate(LocalDate.of(1985, 12, 1))
                                .nationalId("9876543210")
                                .vinNumber(null)
                                .registrationNumber("WX1234Y")
                                .build();
                    }
                },

                new OpenApiExampleEntry() {
                    public String summary() { return "Dane bez pojazdu"; }
                    public String description() { return "Podano dane osobowe bez VIN i numeru rejestracyjnego (jeśli logika na to pozwala)."; }
                    public Object exampleModel() {
                        return VehicleDataRequest.builder()
                                .birthDate(LocalDate.of(1970, 3, 15))
                                .nationalId("1122334455")
                                .vinNumber(null)
                                .registrationNumber(null)
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
