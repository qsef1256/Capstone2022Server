package net.qsef1256.capstone2022server.user.member;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import net.qsef1256.capstone2022server.database.DaoCommonJpaImpl;

@Path("member")
public class MemberResource {

    private static final DaoCommonJpaImpl<MemberEntity, Long> memberDao = new DaoCommonJpaImpl<>(MemberEntity.class);

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public MemberEntity getMember(@PathParam("id") Long memberId) {
        return memberDao.findById(memberId);
    }

}
