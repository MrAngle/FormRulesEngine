WHEN identityMethod == 'Data urodzenia' THEN
    birthDate.visible = true,
    birthDate.required = true,
    nationalId.visible = false,
    nationalId.required = false;

WHEN identityMethod == 'PESEL/NIP/REGON' THEN
    @macro(enable, field:nationalId),
    @macro(disable, field:birthDate)

WHEN noVehicle == true THEN
    group_vehicle.visible = false,
    group_vehicle.required = false;

WHEN vinNumber == '' AND registrationNumber == '' THEN
    vinNumber.required = true,
    registrationNumber.required = true;

WHEN vinNumber != '' OR registrationNumber != '' THEN
    vinNumber.required = false,
    registrationNumber.required = false;

WHEN predicate:isNewUser == true THEN
    birthDate.visible = false;