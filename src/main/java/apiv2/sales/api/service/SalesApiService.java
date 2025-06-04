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
        // TODO:
        // Additional validation logic can be implemented here if needed
        // Send to domain port for processing
        return new apiv2.sales.api.dto.response.InitSalesResponse("test");
    }
}