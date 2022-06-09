package net.qsef1256.capstone2022server.user.member;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
public class MemberEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String phoneNo;
    private String address;

    @Embedded
    private MemberCoronaInfo coronaInfo;

    @Data
    static class MemberCoronaInfo {
        private LocalDateTime confirmationDate;
        private LocalDateTime finalVaccineDate;
        private LocalDateTime quarantineReleaseDate;
    }

}
