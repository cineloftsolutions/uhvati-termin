package com.cineloftsolutions.uhvati_termin.maper;

import com.cineloftsolutions.uhvati_termin.dto.RegisterRequestDTO;
import com.cineloftsolutions.uhvati_termin.dto.UserDTO;
import com.cineloftsolutions.uhvati_termin.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);
    User toEntity(UserDTO dto);
    User fromRegisterRequest(RegisterRequestDTO dto);
}
