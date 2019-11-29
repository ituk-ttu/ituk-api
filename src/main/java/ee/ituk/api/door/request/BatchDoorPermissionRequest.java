package ee.ituk.api.door.request;

import ee.ituk.api.door.domain.Door;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BatchDoorPermissionRequest {

    private List<Door> doors;
    private List<Long> userIds;
}
