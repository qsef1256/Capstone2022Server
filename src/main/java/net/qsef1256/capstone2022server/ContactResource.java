package net.qsef1256.capstone2022server;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import net.qsef1256.capstone2022server.data.ContactEntity;
import net.qsef1256.capstone2022server.database.DaoCommonJpaImpl;
import net.qsef1256.capstone2022server.service.ModelMapperService;
import net.qsef1256.capstone2022server.util.GsonUtil;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@Path("contact")
public class ContactResource {

    private static final DaoCommonJpaImpl<ContactEntity, UUID> contactDao = new DaoCommonJpaImpl<>(ContactEntity.class);

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ContactEntity getContact(@PathParam("id") UUID contactId) {
        return contactDao.findById(contactId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addContact(ContactEntity entity) {
        contactDao.save(entity);
    }

    // TODO: test patch
    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    public void patchContact(@NotNull ContactEntity entity) {
        ContactEntity target = contactDao.findById(entity.getId());

        ModelMapperService.get().map(target, entity);

        contactDao.save(target);
    }

    @DELETE
    @Path("{id}")
    public void removeContact(@PathParam("id") UUID id) {
        contactDao.deleteById(id);
    }

}
