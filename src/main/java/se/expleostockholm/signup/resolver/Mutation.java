package se.expleostockholm.signup.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import se.expleostockholm.signup.domain.*;
import se.expleostockholm.signup.service.*;

@Component
@AllArgsConstructor
public class Mutation implements GraphQLMutationResolver {

    private final InvitationService invitationService;

    public Response updateInvitation(Invitation invitationUpdateInput) throws Exception {
        Invitation updatedInvitation = invitationService.updateInvitation(invitationUpdateInput);

        return Response.builder()
                .message("Invitation was successfully updated")
                .id(updatedInvitation.getId())
                .build();
    }
}
