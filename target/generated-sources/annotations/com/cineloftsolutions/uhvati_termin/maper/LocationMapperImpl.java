package com.cineloftsolutions.uhvati_termin.maper;

import com.cineloftsolutions.uhvati_termin.dto.CreateLocationDTO;
import com.cineloftsolutions.uhvati_termin.dto.ReadLocationDTO;
import com.cineloftsolutions.uhvati_termin.entity.Location;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-27T12:09:02+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class LocationMapperImpl implements LocationMapper {

    @Override
    public Location toEntity(CreateLocationDTO createLocationDTO) {
        if ( createLocationDTO == null ) {
            return null;
        }

        Location location = new Location();

        location.setName( createLocationDTO.getName() );
        location.setAddress( createLocationDTO.getAddress() );
        location.setCity( createLocationDTO.getCity() );

        return location;
    }

    @Override
    public ReadLocationDTO toReadDTO(Location location) {
        if ( location == null ) {
            return null;
        }

        ReadLocationDTO readLocationDTO = new ReadLocationDTO();

        readLocationDTO.setId( location.getId() );
        readLocationDTO.setName( location.getName() );
        readLocationDTO.setAddress( location.getAddress() );
        readLocationDTO.setCity( location.getCity() );

        return readLocationDTO;
    }
}
