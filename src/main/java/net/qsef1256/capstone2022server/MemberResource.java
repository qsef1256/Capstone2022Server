package net.qsef1256.capstone2022server;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import net.qsef1256.capstone2022server.data.MemberEntity;
import net.qsef1256.capstone2022server.database.DaoCommonJpaImpl;
import net.qsef1256.capstone2022server.service.ModelMapperService;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Slf4j
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
        memberDao.open();

        memberDao.saveAndClose(entity);
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    public void patchContact(@NotNull MemberEntity entity) {
        log.info("patch received: " + entity);
        memberDao.open();

        MemberEntity target = memberDao.existsById(entity.getUuid())
                ? memberDao.findById(entity.getUuid())
                : new MemberEntity();

        log.info("target: " + target);

        ModelMapperService.get().map(entity, target);

        log.info("target after mapping: " + target);

        memberDao.saveAndClose(target);
    }

    @DELETE
    @Path("{id}")
    public void removeContact(@PathParam("id") UUID id) {
        memberDao.open();
        memberDao.deleteById(id);
        memberDao.close();
    }

}
