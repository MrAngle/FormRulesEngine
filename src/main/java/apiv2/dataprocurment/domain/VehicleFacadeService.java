package apiv2.dataprocurment.domain;

import apiv2.dataprocurment.domain.model.VehicleData;
import org.springframework.stereotype.Service;

@Service
class VehicleFacadeService implements VehiclePort {

    @Override
    public String doSomething(VehicleData vehicleData) {
        return "Test";
    }
}
