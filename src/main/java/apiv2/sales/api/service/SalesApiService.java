package apiv2.sales.api.service;


import apiv2.sales.domain.SalesPort;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalesApiService {

    private final SalesPort salesPort;
    private final Validator validator;

    public apiv2.sales.api.dto.response.InitSalesResponse initialize(apiv2.sales.api.dto.request.InitSalesRequest request) {
//        Set<ConstraintViolation<InitSalesRequest>> violations = validator.validate(request);
//        if (!violations.isEmpty()) {
//            throw new ConstraintViolationException("Invalid request", violations);
//        }

//        String salesId = salesPort.initSales(
//                request.getBusinessFlow(),
//                request.getBirthDate(),
//                request.getPersonalId(),
//                request.getVin(),
//                request.getRegistrationNumber()
//        );

//        String salesId = salesPort.initSales(Mapper.mapToDomain(request));

        return new apiv2.sales.api.dto.response.InitSalesResponse("test");
    }
}