package com.example.reactiveapplication.mapper;

import com.example.reactiveapplication.dto.req.UserCreate;
import com.example.reactiveapplication.dto.resp.UserResp;
import com.example.reactiveapplication.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        implementationName = "UserMapperImpl")
public interface UserMapper {
    UserModel toModel(UserCreate dto);

    UserResp toResp(UserModel save);
}
