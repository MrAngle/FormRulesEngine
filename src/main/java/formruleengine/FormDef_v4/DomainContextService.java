package formruleengine.FormDef_v4;


import formruleengine.FormDef_v4.domain.ClaimContext;
import formruleengine.FormDef_v4.domain.ClaimDataService;
import org.springframework.stereotype.Service;

@Service
public class DomainContextService {

    private final ClaimDataService claimDataService;

    public DomainContextService(ClaimDataService claimDataService) {
        this.claimDataService = claimDataService;
    }

//    public FormSubmissionDto prefillSubmission(FormSchema schema) {
//        ClaimContext ctx = claimDataService.loadClaimContext();
//        return FormProcessor.prefillFromDomain(schema, ctx);
//    }


    public void enrichSchemaWithContext(FormSchema schema) {
        ClaimContext ctx = claimDataService.loadClaimContext();
        FormProcessor.prefillSchemaWithDomainData(schema, ctx);
    }

}