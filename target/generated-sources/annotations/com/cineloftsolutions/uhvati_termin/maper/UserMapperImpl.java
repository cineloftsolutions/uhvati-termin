package com.cineloftsolutions.uhvati_termin.maper;

import com.cineloftsolutions.uhvati_termin.dto.RegisterRequestDTO;
import com.cineloftsolutions.uhvati_termin.dto.UserDTO;
import com.cineloftsolutions.uhvati_termin.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-24T15:17:25+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setName( user.getName() );
        userDTO.setEmail( user.getEmail() );

        return userDTO;
    }

    @Override
    public User toEntity(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setId( dto.getId() );
        user.setName( dto.getName() );
        user.setEmail( dto.getEmail() );

        return user;
    }

    @Override
    public User fromRegisterRequest(RegisterRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setName( dto.getName() );
        user.setEmail( dto.getEmail() );
        user.setPassword( dto.getPassword() );

        return user;
    }
}
