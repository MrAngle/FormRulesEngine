package apiv2.sales.api.controller.openapi;

import apiv2.openapicustomizer.OpenApiExampleEntry;
import apiv2.openapicustomizer.OpenApiExampleSet;
import apiv2.sales.api.controller.ApiPaths;
import apiv2.sales.api.dto.request.InitSalesRequest;
import apiv2.sales.api.dto.response.InitSalesResponse;
import apiv2.sales.domain.model.BusinessFlowType;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostInitSalesExamples implements OpenApiExampleSet {

    @Override
    public String getPath() {
        return ApiPaths.SALES_BASE;
    }

    @Override
    public HttpMethod getMethodHttp() {
        return HttpMethod.POST;
    }

    @Override
    public List<OpenApiExampleEntry> getExampleRequestBodies() {
        return List.of(
                new OpenApiExampleEntry() {
                    public String summary() { return "Nowa sprzedaż bez danych wstępnych"; }
                    public String description() { return "Proces sprzedaży rozpoczynany od zera – brak numeru polisy i danych klienta."; }
                    public Object exampleModel() {
                        return InitSalesRequest.builder()
                                .businessFlow(BusinessFlowType.NEW_BUSINESS)
                                .build();
                    }
                },

                new OpenApiExampleEntry() {
                    public String summary() { return "Nowa sprzedaż z istniejącym klientem"; }
                    public String description() { return "Sprzedaż nowej polisy dla już zidentyfikowanego klienta."; }
                    public Object exampleModel() {
                        return InitSalesRequest.builder()
                                .businessFlow(BusinessFlowType.NEW_BUSINESS)
                                .customerIdentifier("CLIENT-2025-XYZ")
                                .build();
                    }
                },

                new OpenApiExampleEntry() {
                    public String summary() { return "Odnowienie istniejącej polisy"; }
                    public String description() { return "Klient posiada już numer polisy i został zidentyfikowany – rozpoczęcie procesu odnowienia."; }
                    public Object exampleModel() {
                        return InitSalesRequest.builder()
                                .businessFlow(BusinessFlowType.RENEWAL)
                                .policyNumber("PL-RENEW-2024-01")
                                .customerIdentifier("CUST-998877")
                                .build();
                    }
                }
        );
    }

    @Override
    public List<OpenApiExampleEntry> getExampleResponseBodies() {
        return List.of(
                new OpenApiExampleEntry() {
                    public String summary() { return "Utworzono sprzedaż – nowa polisa"; }
                    public String description() { return "Odpowiedź po zainicjowaniu sprzedaży typu NEW_BUSINESS."; }
                    public Object exampleModel() {
                        return InitSalesResponse.builder()
                                .salesId("3fa85f64-5717-4562-b3fc-2c963f66afa6")
                                .build();
                    }
                },

                new OpenApiExampleEntry() {
                    public String summary() { return "Utworzono sprzedaż – odnowienie polisy"; }
                    public String description() { return "Odpowiedź po rozpoczęciu procesu odnowienia polisy przez klienta."; }
                    public Object exampleModel() {
                        return InitSalesResponse.builder()
                                .salesId("3fa85f64-5717-4562-b3fc-2c963f66afa6")
                                .build();
                    }
                }
        );
    }
}
