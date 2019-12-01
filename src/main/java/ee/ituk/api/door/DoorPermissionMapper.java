package ee.ituk.api.door;

import ee.ituk.api.door.domain.DoorPermission;
import ee.ituk.api.door.dto.DoorPermissionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface DoorPermissionMapper {

    @Mapping(source = "user.id", target="userId")
    DoorPermissionDto permissionToDto(DoorPermission permission);

    List<DoorPermissionDto> permissionToDto(List<DoorPermission> permissions);
}
