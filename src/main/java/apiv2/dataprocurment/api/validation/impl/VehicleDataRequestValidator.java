package apiv2.dataprocurment.api.validation.impl;


import apiv2.dataprocurment.api.dto.request.VehicleDataRequest;
import apiv2.dataprocurment.api.validation.ValidVehicleData;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class VehicleDataRequestValidator implements ConstraintValidator<ValidVehicleData, VehicleDataRequest> {

    @Override
    public boolean isValid(VehicleDataRequest request, ConstraintValidatorContext context) {
        if (request == null) return true;

        boolean hasVin = StringUtils.isNotBlank(request.vinNumber());
        boolean hasReg = StringUtils.isNotBlank(request.registrationNumber());

        if (hasVin && hasReg) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Provide either VIN or registration number, not both.")
                    .addPropertyNode("vinNumber")
                    .addConstraintViolation();
            context.buildConstraintViolationWithTemplate("Provide either VIN or registration number, not both.")
                    .addPropertyNode("registrationNumber")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
