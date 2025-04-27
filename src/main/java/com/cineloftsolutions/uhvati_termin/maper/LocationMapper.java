package com.cineloftsolutions.uhvati_termin.maper;

import com.cineloftsolutions.uhvati_termin.dto.CreateLocationDTO;
import com.cineloftsolutions.uhvati_termin.dto.ReadLocationDTO;
import com.cineloftsolutions.uhvati_termin.entity.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location toEntity(CreateLocationDTO createLocationDTO);
    ReadLocationDTO toReadDTO(Location location);
}
