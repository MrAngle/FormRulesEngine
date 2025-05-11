package formruleengine.FormDef_v4.domain;

import lombok.Data;

@Data
public class ClaimContext {
    private String nationalId;
    private String birthDate;
    private boolean noVehicle;
    private String vinNumber;
    private String registrationNumber;
    // brak identityMethod — frontend tylko
}
