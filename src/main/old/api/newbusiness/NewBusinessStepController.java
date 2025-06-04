package api.newbusiness;

import api.ApiPathsV1;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPathsV1.PHASE_NEW_BUSINESS)
@Tag(name = "New Business Phases")
@RequiredArgsConstructor
public class NewBusinessStepController {

    @Tag(name = "VehicleData")
    @GetMapping("/vehicle-data")
    public ResponseEntity<?> getStepData() {
        // Zwraca dane na dany krok
        return ResponseEntity.ok("GET step: ");
    }

    @Tag(name = "VehicleData")
    @PostMapping("/vehicle-data")
    public ResponseEntity<?> saveStepData(@RequestBody Object body) {
        // Zapisuje dane z danego kroku
        return ResponseEntity.ok("POST step: ");
    }

    @GetMapping("/user-data")
    public ResponseEntity<?> getUserData() {
        // Zwraca dane na dany krok
        return ResponseEntity.ok("GET step: ");
    }

    @PostMapping("/user-data")
    public ResponseEntity<?> saveUserData(@RequestBody Object body) {
        // Zapisuje dane z danego kroku
        return ResponseEntity.ok("POST step: ");
    }
}
