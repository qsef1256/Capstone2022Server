package net.qsef1256.capstone2022server.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
class UserCoronaInfo {

    private LocalDateTime confirmationDate;
    private LocalDateTime finalVaccineDate;
    private LocalDateTime quarantineReleaseDate;

}
