package apiv2.dataprocurment.api.dto.request;

import apiv2.sales.api.controller.swagger.SwaggerDescriptions;
import apiv2.sales.api.validation.ValidIdentificationNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UpdateVehicleDataRequest(

        LocalDate birthDate,

        @Schema(
                description = SwaggerDescriptions.ID_NUMBER,
            example = "12345678901"
        )
        @NotNull
        @ValidIdentificationNumber
        @Size(min = 9, max = 14)
        String identificationNumber,

        String vin,

        String registrationNumber
) {}
