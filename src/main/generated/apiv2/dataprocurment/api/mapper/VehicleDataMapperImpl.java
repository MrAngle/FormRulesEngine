package apiv2.dataprocurment.api.mapper;

import apiv2.dataprocurment.api.dto.request.VehicleDataRequest;
import apiv2.dataprocurment.domain.model.VehicleData;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-04T19:32:50+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class VehicleDataMapperImpl implements VehicleDataMapper {

    @Override
    public VehicleData toDomain(VehicleDataRequest request) {
        if ( request == null ) {
            return null;
        }

        VehicleData vehicleData = new VehicleData();

        vehicleData.setBirthDate( request.birthDate() );
        vehicleData.setRegistrationNumber( request.registrationNumber() );

        return vehicleData;
    }
}
