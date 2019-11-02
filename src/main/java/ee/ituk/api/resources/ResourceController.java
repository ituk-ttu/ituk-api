package ee.ituk.api.resources;

import ee.ituk.api.resources.dto.ResourceDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resource")
@RequiredArgsConstructor
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
    public ResourceDto saveResource(ResourceDto resourceDto) {
        return mapper.resourceToDto(resourceService.saveResource(mapper.resourceToEntity(resourceDto)));
    }
}
