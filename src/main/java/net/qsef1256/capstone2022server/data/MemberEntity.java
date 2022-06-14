package net.qsef1256.capstone2022server.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@ToString
public class MemberEntity {

    @Id
    private UUID uuid;

    private int vaccineCount;
    private String hospitalName;
    private String hospitalContact;
    private LocalDate vaccineDate;
    private LocalDate confirmDate;
    private boolean kitPositive; // 자가진단키트 양성
    private boolean fastPositive; // 신속항원검사 양성
    private boolean pcrPositive; // PCR 양성

}
