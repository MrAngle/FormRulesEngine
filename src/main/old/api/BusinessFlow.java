package api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessFlow {
    NEW_BUSINESS(BuisinessFlowConstants.NEW_BUSINESS),
    RENEWAL(BuisinessFlowConstants.RENEWAL),
    AFTERSALES(BuisinessFlowConstants.AFTERSALES);

    private final String name;
}

