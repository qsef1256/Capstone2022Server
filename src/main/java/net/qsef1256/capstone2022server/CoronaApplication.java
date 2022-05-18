package net.qsef1256.capstone2022server;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import lombok.extern.slf4j.Slf4j;
import net.qsef1256.capstone2022server.api.corona.CoronaAPI;
import net.qsef1256.capstone2022server.database.JpaManager;
import net.qsef1256.capstone2022server.schedule.DiaScheduler;

@ApplicationPath("rest")
@Slf4j
public class CoronaApplication extends Application {

    public CoronaApplication() {
        log.info("Application setup");

        DiaScheduler.executePerTime(() -> new CoronaAPI().update(), 12, 0, 0); // 정각 12시 업데이트

        JpaManager.getEntityManager();
    }

}
