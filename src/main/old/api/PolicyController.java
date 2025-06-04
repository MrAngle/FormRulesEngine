package api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/{context}/policies")
@RequiredArgsConstructor
@Tag(name = "Policies")
public class PolicyController {

//    private final PolicyStrategyRegistry strategyRegistry;

    @GetMapping("/{policyId}")
    public ResponseEntity<PolicyDto> getPolicy(
            @PathVariable String context,
            @PathVariable String policyId) {

        System.out.println("context" + context + " policyId " + policyId);
//        LOG_DEBUG_MESSAGE("Request to load policy for context: " + context);
//
//        PolicyLoadingStrategy strategy = strategyRegistry.getStrategy(context);
//        PolicyDto policy = strategy.loadPolicy(policyId);

        return ResponseEntity.ok(PolicyDto.builder().policyName("testowa").build());
    }
}