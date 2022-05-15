package net.qsef1256.capstone2022server;

import net.qsef1256.capstone2022server.api.corona.CoronaAPI;
import net.qsef1256.capstone2022server.api.corona.CoronaEntity;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("corona")
public class CoronaResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public CoronaEntity getCorona() {
        return CoronaAPI.getData();
    }

}
