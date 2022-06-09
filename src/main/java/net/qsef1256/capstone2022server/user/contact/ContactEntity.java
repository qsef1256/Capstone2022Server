package net.qsef1256.capstone2022server.user.contact;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ContactEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String phoneNo;

    @Embedded
    private ContactCoronaInfo coronaInfo;

    @Data
    static class ContactCoronaInfo {
        private LocalDateTime confirmationDate;
        private LocalDateTime finalVaccineDate;
        private LocalDateTime quarantineReleaseDate;
        private String overseasEntry;
        private String closeContact;
    }

}
