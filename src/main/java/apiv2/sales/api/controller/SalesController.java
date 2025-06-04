package apiv2.sales.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.SALES_BASE)
@RequiredArgsConstructor
public class SalesController {


    private final apiv2.sales.api.service.SalesApiService salesApiService;

    @PostMapping
    @Operation(summary = "Init sales process")
    public ResponseEntity<apiv2.sales.api.dto.response.InitSalesResponse> initSale(
            @Valid @RequestBody apiv2.sales.api.dto.request.InitSalesRequest request) {
        return ResponseEntity.ok(salesApiService.initialize(request));
    }


}