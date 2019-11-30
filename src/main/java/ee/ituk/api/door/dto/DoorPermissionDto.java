package ee.ituk.api.door.dto;

import ee.ituk.api.door.domain.Door;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoorPermissionDto {

    private Long id;
    private Door door;
    private Long userId;
}
