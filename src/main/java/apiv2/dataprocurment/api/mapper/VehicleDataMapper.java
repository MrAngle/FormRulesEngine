package apiv2.dataprocurment.api.mapper;

import apiv2.dataprocurment.api.dto.request.UpdateVehicleDataRequest;
import apiv2.dataprocurment.domain.model.VehicleData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VehicleDataMapper {

    VehicleDataMapper INSTANCE = Mappers.getMapper(VehicleDataMapper.class);

    VehicleData toDomain(UpdateVehicleDataRequest request);
}
