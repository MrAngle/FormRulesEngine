package apiv2.dataprocurment.api.dto.response;

import lombok.Builder;

@Builder
public record VehicleDataResponse(
        String vin,
        String registrationNumber,
        String brand,
        String model,
        int year
) {}