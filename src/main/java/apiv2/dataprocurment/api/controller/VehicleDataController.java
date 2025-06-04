package apiv2.dataprocurment.api.controller;

import apiv2.dataprocurment.api.dto.request.VehicleDataRequest;
import apiv2.dataprocurment.api.dto.response.VehicleDataResponse;
import apiv2.dataprocurment.api.service.VehicleDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.VEHICLE_DATA)
@RequiredArgsConstructor
public class VehicleDataController {

    private final VehicleDataService vehicleDataService;

    @GetMapping
    public ResponseEntity<VehicleDataResponse> get(@PathVariable String salesId) {
        VehicleDataResponse result = vehicleDataService.getVehicleData(salesId);
        return ResponseEntity.ok(result);
    }

    @PatchMapping
    public ResponseEntity<Void> patch(
            @PathVariable String salesId,
            @RequestBody @Valid VehicleDataRequest request) {

        vehicleDataService.updateVehicleData(salesId, request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> put(
            @PathVariable String salesId,
            @RequestBody @Valid VehicleDataRequest request) {

        vehicleDataService.replaceVehicleData(salesId, request);
        return ResponseEntity.noContent().build();
    }

}