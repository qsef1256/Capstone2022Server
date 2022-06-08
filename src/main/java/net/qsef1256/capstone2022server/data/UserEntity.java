package net.qsef1256.capstone2022server.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class UserEntity {

    @Id
    @GeneratedValue()
    private Long id;
    private String name;
    private String phoneNo;
    private String address;
    @Embedded
    private UserCoronaInfo coronaInfo;

}

