package net.qsef1256.capstone2022server.user.contact;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@RequiredArgsConstructor
@Entity
public class ContactEntity {

    @Id
    private UUID id;
    private String name;
    private String phoneNo;

    @Embedded
    private ContactCoronaInfo coronaInfo;

    @Data
    static class ContactCoronaInfo {
        private LocalDateTime confirmationDate;
        private LocalDateTime finalVaccineDate;
        private LocalDateTime quarantineReleaseDate;
        private boolean overseasEntry;
        private boolean closeContact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ContactEntity that = (ContactEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
