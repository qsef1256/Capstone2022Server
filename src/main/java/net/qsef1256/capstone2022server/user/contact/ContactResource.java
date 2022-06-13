package net.qsef1256.capstone2022server.user.contact;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import net.qsef1256.capstone2022server.database.DaoCommonJpaImpl;
import org.jetbrains.annotations.NotNull;

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

        contactDao.save(target
                .setName(entity.getName())
                .setPhoneNo(entity.getPhoneNo())
                .setCoronaInfo(entity.getCoronaInfo())
        );
    }

    @DELETE
    public void removeContact(ContactEntity testContact) {
        contactDao.delete(testContact);
    }

}
