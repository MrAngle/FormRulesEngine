package apiv2.dataprocurment.api.service;


import apiv2.dataprocurment.api.dto.request.VehicleDataRequest;
import apiv2.dataprocurment.api.dto.response.VehicleDataResponse;
import apiv2.dataprocurment.api.mapper.VehicleDataMapper;
import apiv2.dataprocurment.domain.VehiclePort;
import apiv2.dataprocurment.domain.model.VehicleData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleDataService {

    private final VehicleDataMapper vehicleDataMapper;
    private final VehiclePort vehicleService;

    public VehicleDataResponse getVehicleData(String salesId) {
        // TODO: implement logic
        return VehicleDataResponse.builder().vin("test").build(); // placeholder
    }

    public void updateVehicleData(String salesId, VehicleDataRequest request) {
        VehicleData domain = vehicleDataMapper.toDomain(request);

        vehicleService.doSomething(domain);
    }

    public void replaceVehicleData(String salesId, VehicleDataRequest request) {
        VehicleData domain = vehicleDataMapper.toDomain(request);

        vehicleService.doSomething(domain);
    }
}
