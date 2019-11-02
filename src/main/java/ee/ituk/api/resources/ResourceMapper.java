package ee.ituk.api.resources;

import ee.ituk.api.resources.domain.Resource;
import ee.ituk.api.resources.dto.ResourceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ResourceMapper {

    @Mapping(target = "authorId", source = "author.id")
    ResourceDto resourceToDto(Resource resource);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "author.id", source = "authorId")
    Resource resourceToEntity(ResourceDto resourceDto);

    List<ResourceDto> resourcesToDto(List<Resource> resources);
}
