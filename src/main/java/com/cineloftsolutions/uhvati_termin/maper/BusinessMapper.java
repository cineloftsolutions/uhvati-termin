package com.cineloftsolutions.uhvati_termin.maper;

import com.cineloftsolutions.uhvati_termin.dto.CreateBusinessDTO;
import com.cineloftsolutions.uhvati_termin.dto.ReadBusinessDTO;
import com.cineloftsolutions.uhvati_termin.entity.Business;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusinessMapper {
    Business toEntity(CreateBusinessDTO dto);
    ReadBusinessDTO toDTO(Business entity);
}
