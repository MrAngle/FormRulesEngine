package apiv2.sales.domain;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class SalesFacadeService implements SalesPort {
    @Override
    public String initSales(apiv2.sales.domain.model.BusinessFlowType businessFlow, String birthDate, String personalId, String vin, String registrationNumber) {
        return UUID.randomUUID().toString();
    }
}
