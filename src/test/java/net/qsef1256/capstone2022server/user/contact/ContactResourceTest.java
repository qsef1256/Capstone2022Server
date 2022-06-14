package net.qsef1256.capstone2022server.user.contact;

import net.qsef1256.capstone2022server.data.ContactEntity;
import net.qsef1256.capstone2022server.ContactResource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ContactResourceTest {

    private static ContactEntity testContact;

    private static final UUID testUUID = UUID.randomUUID();

    @BeforeAll
    static void setUp() {
        ContactEntity.ContactCoronaInfo testContactInfo = new ContactEntity.ContactCoronaInfo();
        testContactInfo.setCloseContact(true);
        testContactInfo.setOverseasEntry(true);

        testContact = new ContactEntity()
                .setId(testUUID)
                .setName("test")
                .setCoronaInfo(testContactInfo);
    }

    @AfterEach
    void clean() {
        new ContactResource().removeContact(testUUID);
    }

    @Test
    void patchContact() {
        ContactResource resource = new ContactResource();
        resource.addContact(testContact);
        ContactEntity afterContact = new ContactEntity()
                .setId(testUUID)
                .setName("aftertest")
                .setCoronaInfo(new ContactEntity.ContactCoronaInfo().setCloseContact(false));

        resource.patchContact(afterContact);

        assertEquals(resource.getContact(testUUID), afterContact);
        assertTrue(resource.getContact(testUUID).getCoronaInfo().isOverseasEntry());
    }

}
