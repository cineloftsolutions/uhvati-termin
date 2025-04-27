package com.cineloftsolutions.uhvati_termin.maper;

import com.cineloftsolutions.uhvati_termin.dto.ReadUserDTO;
import com.cineloftsolutions.uhvati_termin.dto.RegisterRequestDTO;
import com.cineloftsolutions.uhvati_termin.dto.UserDTO;
import com.cineloftsolutions.uhvati_termin.entity.Role;
import com.cineloftsolutions.uhvati_termin.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO toDTO(User user);
    User toEntity(UserDTO dto);
    User fromRegisterRequest(RegisterRequestDTO dto);
    @Mapping(source = "business.businessId", target = "businessId")
    ReadUserDTO toReadUserDTO(User user);

    default String map(Role role) {
        return role != null ? role.getName() : null;
    }
}
