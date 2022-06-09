package net.qsef1256.capstone2022server.user.contact;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import net.qsef1256.capstone2022server.database.DaoCommonJpaImpl;

@Path("contact")
public class ContactResource {

    private static final DaoCommonJpaImpl<ContactEntity, Long> contactDao = new DaoCommonJpaImpl<>(ContactEntity.class);

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ContactEntity getContact(@PathParam("id") Long contactId) {
        return contactDao.findById(contactId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addContact(ContactEntity entity) {
        contactDao.save(entity);
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    public void patchContact(ContactEntity entity) {
        ContactEntity target = contactDao.findById(entity.getId());

        contactDao.save(target.toBuilder()
                .name(entity.getName())
                .phoneNo(entity.getPhoneNo())
                .coronaInfo(entity.getCoronaInfo())
                .build());
    }

    @DELETE
    public void removeContact(ContactEntity testContact) {
        contactDao.delete(testContact);
    }

}
