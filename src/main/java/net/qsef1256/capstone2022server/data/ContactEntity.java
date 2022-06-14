package net.qsef1256.capstone2022server.data;

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
    private UUID uuid;
    private String name;
    private String phoneNo;

    @Embedded
    private ContactCoronaInfo coronaInfo;

    @Data
    public static class ContactCoronaInfo {
        private LocalDateTime confirmationDate;
        private LocalDateTime finalVaccineDate;
        private boolean overseasEntry;
        private boolean closeContact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ContactEntity that = (ContactEntity) o;
        return uuid != null && Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
