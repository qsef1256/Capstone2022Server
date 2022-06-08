package net.qsef1256.capstone2022server.data;

import jakarta.persistence.Entity;

@Entity
public class ContactEntity extends UserEntity {

    private String overseasEntry;
    private String closeContact;

}
