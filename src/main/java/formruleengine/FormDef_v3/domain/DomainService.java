package formruleengine.FormDef_v3.domain;

public class DomainService {
    public static ClaimForm getClaimForm() {
        return ClaimForm.builder()
                        .vehicle(ClaimForm.Vehicle.builder()
                                .vehicleType("Car")
                                .build())
                .build();
    }
}
