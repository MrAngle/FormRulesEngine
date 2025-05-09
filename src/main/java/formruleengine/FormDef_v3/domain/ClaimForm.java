package formruleengine.FormDef_v3.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClaimForm {
    public Owner owner = Owner.builder().build();
    public Vehicle vehicle = Vehicle.builder().build();

    @Getter
    @Setter
    @Builder
    public static class Owner {
        public Boolean hasClaimIn12Months;
        public Boolean hasClaimIn6Months;
    }

    @Getter
    @Setter
    @Builder
    public static class Vehicle {
        public String vehicleType;
        public Boolean isFromUK;
        public Boolean specialCaseSteering;
        public String steeringSide;
    }
}