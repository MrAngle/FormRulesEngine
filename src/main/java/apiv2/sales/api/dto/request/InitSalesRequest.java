package apiv2.sales.api.dto.request;

import apiv2.sales.api.validation.ValidSalesRequest;
import apiv2.sales.domain.model.BusinessFlowType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@ValidSalesRequest
@Builder
public record InitSalesRequest(

    @NotNull(message = "Business flow must be provided")
    BusinessFlowType businessFlow,

    String policyNumber,

    String customerIdentifier

) { }