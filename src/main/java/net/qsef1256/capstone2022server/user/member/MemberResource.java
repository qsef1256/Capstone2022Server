package net.qsef1256.capstone2022server.user.member;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import net.qsef1256.capstone2022server.database.DaoCommonJpaImpl;

import java.util.UUID;

@Path("member")
public class MemberResource {

    private static final DaoCommonJpaImpl<MemberEntity, UUID> memberDao = new DaoCommonJpaImpl<>(MemberEntity.class);

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public MemberEntity getMember(@PathParam("id") UUID memberId) {
        return memberDao.findById(memberId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addContact(MemberEntity entity) {
        memberDao.save(entity);
    }

    @DELETE
    public void removeContact(MemberEntity member) {
        memberDao.delete(member);
    }

}
