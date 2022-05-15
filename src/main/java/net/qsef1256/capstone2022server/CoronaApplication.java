package net.qsef1256.capstone2022server;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import net.qsef1256.capstone2022server.database.JpaManager;
import net.qsef1256.capstone2022server.schedule.DiaScheduler;

@ApplicationPath("rest")
public class CoronaApplication extends Application {

    public CoronaApplication() {
        Runtime.getRuntime().addShutdownHook(new Thread(DiaScheduler::shutdown));

        JpaManager.getEntityManager();
    }

}
