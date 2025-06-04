package apiv2.dataprocurment.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UpdateVehicleDataRequest(

        LocalDate birthDate,

        @NotNull
        @Size(min = 9, max = 14)
        String identificationNumber,

        String vin,

        String registrationNumber
) {}
