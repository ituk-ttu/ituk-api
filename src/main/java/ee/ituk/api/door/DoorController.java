package ee.ituk.api.door;

import ee.ituk.api.door.domain.Door;
import ee.ituk.api.door.dto.BatchDoorPermissionRequest;
import ee.ituk.api.door.dto.DoorPermissionDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/door")
@RequiredArgsConstructor
public class DoorController {

    private final DoorService doorService;
    private final DoorPermissionMapper mapper = Mappers.getMapper(DoorPermissionMapper.class);

    @GetMapping
    public List<Door> getAllDoors() {
        return doorService.getAllDoors();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<DoorPermissionDto>> getUserPermissions(@PathVariable Long userId) {
        return ResponseEntity.ok(mapper.permissionToDto(doorService.getUserPermissions(userId)));
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<List<DoorPermissionDto>> addPermissions(@PathVariable Long userId, @RequestBody List<Door> doors) {
        return ResponseEntity.ok(mapper.permissionToDto(doorService.addPermissionsToSingleUser(doors, userId)));
    }

    @PostMapping("/batch/add")
    public ResponseEntity<List<DoorPermissionDto>> batchAddPermissions(@RequestBody BatchDoorPermissionRequest request) {
        return ResponseEntity.ok(mapper.permissionToDto(doorService.batchAddPermission(request.getDoors(), request.getUserIds())));
    }

    @PutMapping("/{userId}/delete")
    public ResponseEntity batchDeletePermission(@PathVariable Long userId, @RequestBody List<Door> doors) {
        doorService.deletePermissions(doors, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/batch/delete")
    public ResponseEntity batchDeletePermission(@RequestBody BatchDoorPermissionRequest request) {
        doorService.batchDeletePermission(request.getDoors(), request.getUserIds());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("users")
    public ResponseEntity<List<UserDoorsDto>> getUsersWithPermissions() {
        return ResponseEntity.ok(doorService.getUserDoorPermissions());
    }
}
