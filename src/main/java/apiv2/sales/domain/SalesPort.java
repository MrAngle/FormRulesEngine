package apiv2.sales.domain;

import apiv2.sales.domain.model.BusinessFlowType;

public interface SalesPort {
    String initSales(BusinessFlowType businessFlow, String birthDate, String personalId, String vin, String registrationNumber);
}
