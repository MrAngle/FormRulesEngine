package api.reneval;

import api.ApiPathsV1;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPathsV1.PHASE_RENEWAL)
@Tag(name = "Renewal Phases")
@RequiredArgsConstructor
public class RenewalStepController {

    @GetMapping("/{phaseName}")
    public ResponseEntity<?> getStepData(@PathVariable String phaseName) {
        // Zwraca dane na dany krok
        return ResponseEntity.ok("RENEWAL GET step: " + phaseName);
    }

    @PostMapping("/{phaseName}")
    public ResponseEntity<?> saveStepData(@PathVariable String phaseName, @RequestBody Object body) {
        // Zapisuje dane z danego kroku
        return ResponseEntity.ok("RENEWAL POST step: " + phaseName);
    }

//    @Operation(summary = "Pobierz dane pojazdu", tags = {"Renewal Phases - VehicleData"})
//    @GetMapping("/vehicle-data")
//    public ResponseEntity<?> getStepData() {
//        // Zwraca dane na dany krok
//        return ResponseEntity.ok("GET step: ");
//    }
//
//    @Operation(summary = "Pobierz dane pojazdu", tags = {"Renewal Phases - VehicleData"})
//    @PostMapping("/vehicle-data")
//    public ResponseEntity<?> saveStepData(@RequestBody Object body) {
//        // Zapisuje dane z danego kroku
//        return ResponseEntity.ok("POST step: ");
//    }

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