package ee.ituk.api.door;

import ee.ituk.api.door.domain.Door;
import ee.ituk.api.door.domain.DoorPermission;
import ee.ituk.api.door.domain.DoorPermissionLogEntry;
import ee.ituk.api.door.repository.DoorPermissionLogEntryRepository;
import ee.ituk.api.door.repository.DoorPermissionRepository;
import ee.ituk.api.door.repository.DoorRepository;
import ee.ituk.api.user.UserService;
import ee.ituk.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoorService {

    private final DoorRepository doorRepository;
    private final DoorPermissionRepository permissionRepository;
    private final DoorPermissionLogEntryRepository logEntryRepository;

    private final UserService userService;

    List<Door> getAllDoors() {
        return doorRepository.findAll();
    }

    /**
     * Method used to create door permissions for one user. As result creates log entries for changed door permissions.
     * @param doors list of doors
     * @param userId id of user
     * @return created permissions
     */
    List<DoorPermission> addPermissionsToSingleUser(List<Door> doors, Long userId) {
        User user = userService.findUserById(userId);

        Map<User, List<DoorPermission>> userToDoorPermissions = new HashMap<>();
        List<DoorPermission> permissions = createDoorPermissions(doors, user);

        userToDoorPermissions.put(user, permissions);

        createPermissionLogEntry(true, userToDoorPermissions);
        return permissionRepository.saveAll(permissions);
    }

    /**
     * Method used to create door permissions for multiple users. As results creates log entries for changed door permissions.
     * @param doors list of doors
     * @param userIds ids of users
     * @return created permissions
     */
    List<DoorPermission> batchAddPermission(List<Door> doors, List<Long> userIds) {
        List<User> users = userService.findByIds(userIds);
        List<DoorPermission> permissions = new ArrayList<>();
        Map<User, List<DoorPermission>> userToDoorPermissions = new HashMap<>();

        users.forEach(user -> {
            List<DoorPermission> userPermissions =  createDoorPermissions(doors, user);
            permissions.addAll(userPermissions);
            userToDoorPermissions.put(user, userPermissions);
        });

        createPermissionLogEntry(true, userToDoorPermissions);
        return permissionRepository.saveAll(permissions);
    }

    void deletePermissions(List<Door> doors, Long userId) {
        User user = userService.findUserById(userId);
        List<DoorPermission> userPermissions = permissionRepository.findByUser(user);
        userPermissions = userPermissions.stream()
                .filter(permission -> doors.contains(permission.getDoor()))
                .collect(Collectors.toList());
        if (userPermissions.isEmpty()) {
            createPermissionLogEntry(false, Collections.singletonMap(user, userPermissions));
            permissionRepository.deleteAll(userPermissions);
        }
    }

    /**
     * Method used to delete door permissions for multiple users. As results creates log entries for changed door permissions.
     * @param doors list of doors
     * @param userIds ids of users
     */
    void batchDeletePermission(List<Door> doors, List<Long> userIds) {
        List<User> users = userService.findByIds(userIds);
        Map<User, List<DoorPermission>> userToDoorPermissions = new HashMap<>();

        permissionRepository.findAll().stream()
                .filter(permission -> users.contains(permission.getUser()))
                .filter(permission -> doors.contains(permission.getDoor()))
                .forEach(permission -> {
                    if (userToDoorPermissions.containsKey(permission.getUser())) {
                        userToDoorPermissions.get(permission.getUser()).add(permission);
                    } else {
                        userToDoorPermissions.put(permission.getUser(), new ArrayList<>(Arrays.asList(permission)));
                    }
                });
        createPermissionLogEntry(false, userToDoorPermissions);
        List<DoorPermission> permissions = userToDoorPermissions.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        permissionRepository.deleteInBatch(permissions);
    }


    List<DoorPermission> getUserPermissions(Long userId) {
        User user = userService.findUserById(userId);
        return permissionRepository.findByUser(user);
    }

    private List<DoorPermission> createDoorPermissions(List<Door> doors, User user) {
        return doors.stream()
                .filter(door -> doorRepository.existsByCode(door.getCode()))
                .map(door -> DoorPermission.builder()
                        .door(door)
                        .user(user)
                        .build()
                ).collect(Collectors.toList());
    }

    private void createPermissionLogEntry(boolean added,  Map<User, List<DoorPermission>> userToDoorPermissions) {
        List<DoorPermissionLogEntry> entries = new ArrayList<>();
        userToDoorPermissions.forEach((user, permissions) ->
                entries.add(
                DoorPermissionLogEntry.builder()
                .change(user.getFullName() + " "
                        + (added ? "added" : "removed") + " permissions to doors: " + createDoorNamesString(permissions))
                .updatedAt(LocalDateTime.now())
                .userModified((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .build()));
        logEntryRepository.saveAll(entries);
    }


    private String createDoorNamesString(List<DoorPermission> permissions) {
        return permissions.stream()
                .map(DoorPermission::getDoor)
                .map(Door::getCode)
                .collect(Collectors.joining(" ,"));
    }

    public List<UserDoorsDto> getUserDoorPermissions() {
        Map<User, List<Door>> userToDoorPermissions = new HashMap<>();
        permissionRepository.findAll()
                .forEach(permission -> {
                    if (userToDoorPermissions.containsKey(permission.getUser())) {
                        userToDoorPermissions.get(permission.getUser()).add(permission.getDoor());
                    } else {
                        userToDoorPermissions.put(permission.getUser(), new ArrayList<>(Arrays.asList(permission.getDoor())));
                    }
                });
        return userToDoorPermissions.entrySet().stream()
                .map(e -> new UserDoorsDto(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }
}
