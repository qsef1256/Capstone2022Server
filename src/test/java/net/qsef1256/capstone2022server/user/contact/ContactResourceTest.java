package net.qsef1256.capstone2022server.user.contact;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactResourceTest {

    private static ContactEntity testContact;

    @BeforeAll
    static void setUp() {
        ContactEntity.ContactCoronaInfo testContactInfo = new ContactEntity.ContactCoronaInfo();
        testContactInfo.setCloseContact("Y");

        testContact = ContactEntity.builder()
                .id(-1L)
                .name("테스트")
                .coronaInfo(testContactInfo)
                .build();
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