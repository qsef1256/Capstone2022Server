package net.qsef1256.capstone2022server.user.contact;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ContactResourceTest {

    private static ContactEntity testContact;

    @BeforeAll
    static void setUp() {
        ContactEntity.ContactCoronaInfo testContactInfo = new ContactEntity.ContactCoronaInfo();
        testContactInfo.setCloseContact(true);

        testContact = new ContactEntity()
                .setId(UUID.randomUUID())
                .setName("테스트")
                .setCoronaInfo(testContactInfo);
    }

    @AfterEach
    static void clean() {
        new ContactResource().removeContact(testContact);
    }

    @Test
    void patchContact() {
        new ContactResource().patchContact(testContact);
    }

}