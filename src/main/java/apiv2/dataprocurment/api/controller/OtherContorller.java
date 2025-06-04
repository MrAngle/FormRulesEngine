package apiv2.dataprocurment.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.OTHER_DATA)
@RequiredArgsConstructor
public class OtherContorller {

    @GetMapping
    public ResponseEntity<Object> get(@PathVariable String salesId) {
//        Object result = vehicleDataService.getVehicleData(salesId);
        return ResponseEntity.ok(null);
    }

}
