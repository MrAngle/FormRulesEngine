package api.reneval;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/renewal/phase")
@Tag(name = "VehicleData - Phase")
public class RenewalVehicleController {

    @Operation(summary = "GET", tags = {"VehicleData"})
    @GetMapping("/vehicle-data")
    public ResponseEntity<String> getVehicleData() {
        return ResponseEntity.ok("GET");
    }

    @Operation(summary = "POST", tags = {"VehicleData"})
    @PostMapping("/vehicle-data")
    public ResponseEntity<String> postVehicleData(@RequestBody String input) {
        return ResponseEntity.ok("POST: " + input);
    }
}
