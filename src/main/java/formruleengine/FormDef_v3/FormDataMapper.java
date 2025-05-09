package formruleengine.FormDef_v3;

import formruleengine.FormDef_v3.domain.ClaimForm;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.function.Function;

@UtilityClass
public class FormDataMapper {

    public static final Map<String, Function<ClaimForm, Object>> fieldAccessors = Map.of(
            "owner.hasClaimIn12Months", form -> form != null && form.getOwner() != null ? form.getOwner().getHasClaimIn12Months() : null,
            "owner.hasClaimIn6Months", form -> form != null && form.getOwner() != null ? form.getOwner().getHasClaimIn6Months() : null,
            "vehicle.vehicleType", form -> form != null && form.getVehicle() != null ? form.getVehicle().getVehicleType() : null,
            "vehicle.isFromUK", form -> form != null && form.getVehicle() != null ? form.getVehicle().getIsFromUK() : null,
            "vehicle.specialCaseSteering", form -> form != null && form.getVehicle() != null ? form.getVehicle().getSpecialCaseSteering() : null,
            "vehicle.steeringSide", form -> form != null && form.getVehicle() != null ? form.getVehicle().getSteeringSide() : null
    );

    public static Object getValue(ClaimForm form, String fieldKey) {
        Function<ClaimForm, Object> accessor = fieldAccessors.get(fieldKey);
        if (accessor == null) throw new IllegalArgumentException("Unknown field key: " + fieldKey);
        return accessor.apply(form);
    }
}

