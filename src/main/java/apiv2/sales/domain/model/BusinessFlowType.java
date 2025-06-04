package apiv2.sales.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessFlowType {
    NEW_BUSINESS(BuisinessFlowConstants.NEW_BUSINESS),
    RENEWAL(BuisinessFlowConstants.RENEWAL),
    AFTERSALES(BuisinessFlowConstants.AFTERSALES);

    private final String name;
}
