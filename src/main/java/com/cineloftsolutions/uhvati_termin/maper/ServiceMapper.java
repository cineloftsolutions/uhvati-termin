package com.cineloftsolutions.uhvati_termin.maper;

import com.cineloftsolutions.uhvati_termin.dto.CreateServiceDTO;
import com.cineloftsolutions.uhvati_termin.dto.ReadServiceDTO;
import com.cineloftsolutions.uhvati_termin.entity.Service;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    Service toEntity(CreateServiceDTO dto);
    ReadServiceDTO toDTO(Service entity);
}
