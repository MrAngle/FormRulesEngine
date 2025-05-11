package formruleengine.FormDef_v4.domain;

import org.springframework.stereotype.Service;

@Service
public class ClaimDataService {

    public ClaimContext loadClaimContext() {
        ClaimContext context = new ClaimContext();
        context.setNationalId("92010112345");
        context.setBirthDate("1990-01-01");
        context.setNoVehicle(false);
        context.setVinNumber("WBA3A9C59EF586739");
        context.setRegistrationNumber("");
        return context;
    }
}
