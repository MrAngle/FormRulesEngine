package apiv2.dataprocurment.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class VehicleData {
    private LocalDate birthDate;
    private String identificationNumber;
    private String vin;
    private String registrationNumber;
}
