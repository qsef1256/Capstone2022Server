package net.qsef1256.capstone2022server.service;

import org.modelmapper.ModelMapper;

public class ModelMapperService {

    private static final ModelMapper mapper;

    static {
        mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
    }

    public static ModelMapper get() {
        return mapper;
    }

}
