package apiv2.dataprocurment.api.controller;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiPaths {
    public static final String BASE = "/api/v1";
    public static final String SALES_BASE = BASE + "/sales/{salesId}";

    public static final String VEHICLE_DATA = SALES_BASE + "/vehicle-data";
    public static final String OTHER_DATA = SALES_BASE + "/other";
}