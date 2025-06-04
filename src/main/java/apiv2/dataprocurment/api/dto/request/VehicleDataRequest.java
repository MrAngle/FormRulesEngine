package apiv2.dataprocurment.api.dto.request;

import apiv2.dataprocurment.api.validation.ValidVehicleData;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@ValidVehicleData
@Builder
public record VehicleDataRequest(
    LocalDate birthDate,
    @NotNull
    @Size(min = 9, max = 14)
    String nationalId,
    String vinNumber,
    String registrationNumber
) {}
