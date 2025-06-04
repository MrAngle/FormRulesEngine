//package apiv2.dataprocurment.api.service;
//
//
//import api.sales.api.dto.request.InitSalesRequest;
//import api.sales.api.dto.response.InitSalesResponse;
//import jakarta.validation.Validator;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class DataProcurmentApiService {
//
////    private final SalesPort salesPort;
//    private final Validator validator;
//
//    public InitSalesResponse initialize(InitSalesRequest request) {
////        Set<ConstraintViolation<InitSalesRequest>> violations = validator.validate(request);
////        if (!violations.isEmpty()) {
////            throw new ConstraintViolationException("Invalid request", violations);
////        }
//
////        String salesId = salesPort.initSales(
////                request.getBusinessFlow(),
////                request.getBirthDate(),
////                request.getPersonalId(),
////                request.getVin(),
////                request.getRegistrationNumber()
////        );
//
////        String salesId = salesPort.initSales(Mapper.mapToDomain(request));
//
//        return new InitSalesResponse("test");
//    }
//}