package api;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ApiPathsV1 {
    public static final String URI_BASE = "/old/api/v1";

    public static final String URI_RENEWAL = URI_BASE + "/" + BuisinessFlowConstants.RENEWAL;
    public static final String URI_NEW_BUSINESS = URI_BASE + "/" + BuisinessFlowConstants.NEW_BUSINESS;
    public static final String URI_AFTERSALES = URI_BASE + "/" + BuisinessFlowConstants.AFTERSALES;

    public static final String PHASE_RENEWAL = URI_RENEWAL + "/phase";
    public static final String PHASE_NEW_BUSINESS = URI_NEW_BUSINESS + "/phase";
    public static final String PHASE_AFTERSALE = URI_AFTERSALES + "/phase";
}
