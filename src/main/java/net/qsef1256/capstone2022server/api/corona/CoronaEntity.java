package net.qsef1256.capstone2022server.api.corona;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import net.qsef1256.capstone2022server.util.gson.GsonExclude;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@Table(name = "corona_data")
public class CoronaEntity {

    @Id
    @JsonIgnore
    @GsonExclude
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private long deathCnt;
    private long decideCnt;
    private LocalDateTime updateTime;

    protected CoronaEntity() {
    }

    public CoronaEntity(long deathCnt, long decideCnt, LocalDateTime updateTime) {
        this.deathCnt = deathCnt;
        this.decideCnt = decideCnt;
        this.updateTime = updateTime;
    }

}
