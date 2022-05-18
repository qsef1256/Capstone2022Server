package net.qsef1256.capstone2022server;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import net.qsef1256.capstone2022server.database.JpaManager;
import net.qsef1256.capstone2022server.schedule.DiaScheduler;

public class ContextStopListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JpaManager.shutdown();
        DiaScheduler.shutdown();
    }

}
