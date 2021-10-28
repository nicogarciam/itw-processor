package fit.iterway.processor.mappers;

import fit.iterway.processor.dtos.DeviceSpeedResumeDTO;
import fit.iterway.processor.model.DeviceSpeedResume;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceSpeedResumeMapper {
    DeviceSpeedResume toEntity(DeviceSpeedResumeDTO source);
    DeviceSpeedResumeDTO toDto(DeviceSpeedResume target);
}
