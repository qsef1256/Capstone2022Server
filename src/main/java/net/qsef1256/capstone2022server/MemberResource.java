package net.qsef1256.capstone2022server;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import net.qsef1256.capstone2022server.data.ContactEntity;
import net.qsef1256.capstone2022server.data.MemberEntity;
import net.qsef1256.capstone2022server.database.DaoCommonJpaImpl;
import net.qsef1256.capstone2022server.service.ModelMapperService;
import org.jetbrains.annotations.NotNull;

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

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    public void patchContact(@NotNull MemberEntity entity) {
        MemberEntity target = memberDao.findById(entity.getId());

        ModelMapperService.get().map(target, entity);

        memberDao.save(target);
    }

    @DELETE
    @Path("{id}")
    public void removeContact(@PathParam("id") UUID id) {
        memberDao.deleteById(id);
    }

}
