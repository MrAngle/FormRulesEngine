package apiv2.sales.api.dto.request;

import apiv2.sales.api.controller.swagger.SwaggerDescriptions;
import apiv2.sales.api.validation.ValidIdentificationNumber;
import apiv2.sales.domain.model.BusinessFlowType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InitSalesRequest {
    @NotNull
    private BusinessFlowType businessFlow;

//    @JsonFormat(pattern = "dd-MM-yyyy")
    @Schema (type = "string", example = "01-01-2023", description = "Data as dd-MM-yyyy")
    private LocalDate birthDate;

    @Schema(
            description = SwaggerDescriptions.ID_NUMBER,
            example = "12345678901"
    )
    @NotNull
    @ValidIdentificationNumber
    @Size(min = 9, max = 14)
    private String identificationNumber;

    private String vin;

    private String registrationNumber;
}