birthDate {
  visible {
    WHEN identityMethod == 'Data urodzenia' AND predicate:isNewUser != true THEN true;
    ELSE false;
  }

  validators {
    required {
      WHEN identityMethod == 'Data urodzenia' THEN add;
      ELSE remove;
    }

    regex {
      WHEN identityMethod == 'Data urodzenia' THEN replace regexDate;
      ELSE remove;
    }
  }
}

nationalId {
  visible {
    WHEN identityMethod == 'PESEL/NIP/REGON' THEN true;
    ELSE false;
  }

  validators {
    required {
      WHEN identityMethod == 'PESEL/NIP/REGON' THEN add;
      ELSE remove;
    }

    regex {
      WHEN identityMethod == 'PESEL/NIP/REGON' THEN replace regexPeselNipRegon;
      ELSE remove;
    }
  }
}

vinNumber {
  validators {
    required {
      WHEN vinNumber == '' AND registrationNumber == '' THEN add;
      ELSE remove;
    }

    regex {
      WHEN true THEN replace regexVin;
    }
  }
}

registrationNumber {
  validators {
    required {
      WHEN vinNumber == '' AND registrationNumber == '' THEN add;
      ELSE remove;
    }

    regex {
      WHEN true THEN replace regexPlate;
    }
  }
}

group_vehicle {
  visible {
    WHEN noVehicle == true THEN false;
    ELSE true;
  }

  required {
    WHEN noVehicle == true THEN false;
    ELSE true;
  }
}
