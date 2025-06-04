package apiv2.sales.api.validation;


import apiv2.sales.api.dto.request.InitSalesRequest;
import apiv2.sales.domain.model.BusinessFlowType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitSalesRequestValidator implements ConstraintValidator<ValidSalesRequest, InitSalesRequest> {

    @Override
    public boolean isValid(InitSalesRequest request, ConstraintValidatorContext context) {
        if (request == null) return true;

        if (request.businessFlow() == BusinessFlowType.RENEWAL) {
            return StringUtils.isNotBlank(request.policyNumber()) && StringUtils.isNotBlank(request.customerIdentifier());
        }
        return true;
    }
}
