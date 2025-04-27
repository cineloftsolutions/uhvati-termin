package com.cineloftsolutions.uhvati_termin.maper;

import com.cineloftsolutions.uhvati_termin.dto.CreateServiceDTO;
import com.cineloftsolutions.uhvati_termin.dto.ReadServiceDTO;
import com.cineloftsolutions.uhvati_termin.entity.Service;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-27T12:09:02+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class ServiceMapperImpl implements ServiceMapper {

    @Override
    public Service toEntity(CreateServiceDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Service service = new Service();

        service.setName( dto.getName() );
        service.setDescription( dto.getDescription() );
        service.setPrice( dto.getPrice() );
        service.setDurationMinutes( dto.getDurationMinutes() );

        return service;
    }

    @Override
    public ReadServiceDTO toDTO(Service entity) {
        if ( entity == null ) {
            return null;
        }

        ReadServiceDTO readServiceDTO = new ReadServiceDTO();

        readServiceDTO.setId( entity.getId() );
        readServiceDTO.setName( entity.getName() );
        readServiceDTO.setDescription( entity.getDescription() );
        readServiceDTO.setPrice( entity.getPrice() );
        readServiceDTO.setDurationMinutes( entity.getDurationMinutes() );

        return readServiceDTO;
    }
}
