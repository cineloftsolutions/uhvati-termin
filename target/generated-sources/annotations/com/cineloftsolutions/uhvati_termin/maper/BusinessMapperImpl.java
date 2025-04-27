package com.cineloftsolutions.uhvati_termin.maper;

import com.cineloftsolutions.uhvati_termin.dto.CreateBusinessDTO;
import com.cineloftsolutions.uhvati_termin.dto.ReadBusinessDTO;
import com.cineloftsolutions.uhvati_termin.entity.Business;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-27T12:09:02+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class BusinessMapperImpl implements BusinessMapper {

    @Override
    public Business toEntity(CreateBusinessDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Business business = new Business();

        business.setName( dto.getName() );
        business.setPhone( dto.getPhone() );
        business.setEmail( dto.getEmail() );
        business.setWebsite( dto.getWebsite() );
        business.setDescription( dto.getDescription() );
        business.setLogoUrl( dto.getLogoUrl() );

        return business;
    }

    @Override
    public ReadBusinessDTO toDTO(Business entity) {
        if ( entity == null ) {
            return null;
        }

        ReadBusinessDTO readBusinessDTO = new ReadBusinessDTO();

        readBusinessDTO.setId( entity.getId() );
        readBusinessDTO.setBusinessId( entity.getBusinessId() );
        readBusinessDTO.setName( entity.getName() );
        readBusinessDTO.setPhone( entity.getPhone() );
        readBusinessDTO.setEmail( entity.getEmail() );
        readBusinessDTO.setWebsite( entity.getWebsite() );
        readBusinessDTO.setDescription( entity.getDescription() );
        readBusinessDTO.setLogoUrl( entity.getLogoUrl() );

        return readBusinessDTO;
    }
}
