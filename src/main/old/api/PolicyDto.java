package api;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
class PolicyDto {
    private String policyId;
    private String policyName;
    private String description;
    private String context;
    private String version;
    private String status;
    private String createdBy;
    private String createdAt;
    private String updatedBy;
    private String updatedAt;
}
