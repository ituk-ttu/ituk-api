package ee.ituk.api.resources;

import ee.ituk.api.resources.domain.Resource;
import ee.ituk.api.resources.dto.ResourceDto;
import ee.ituk.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resource")
@RequiredArgsConstructor
// TODO move PreAuthorize to websecurity conf (REMOVE @PREAUTHRIZE)
@PreAuthorize("isAuthenticated()")
public class ResourceController {

    private final ResourceService resourceService;
    private final ResourceMapper mapper = Mappers.getMapper(ResourceMapper.class);

    @GetMapping
    @ResponseBody
    public List<ResourceDto> findAllResource() {
        return mapper.resourcesToDto(resourceService.findAll());
    }

    @PostMapping
    @ResponseBody
    public ResourceDto saveResource(Authentication authentication, @RequestBody ResourceDto resourceDto) {
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        resourceDto.setAuthorId(userId);
        return mapper.resourceToDto(resourceService.saveResource(mapper.resourceToEntity(resourceDto)));
    }

    @PutMapping
    @ResponseBody
    public ResourceDto updateResource(Authentication authentication, @RequestBody ResourceDto resourceDto) {
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        resourceDto.setAuthorId(userId);
        Resource savedResource = resourceService.updateResource(resourceDto);
        return mapper.resourceToDto(savedResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMeeting(@PathVariable Long id) {
        resourceService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }
}
