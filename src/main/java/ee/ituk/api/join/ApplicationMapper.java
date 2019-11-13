package ee.ituk.api.join;

import ee.ituk.api.join.domain.Application;
import ee.ituk.api.join.dto.ApplicationDto;
import ee.ituk.api.join.dto.ApplicationResponseDto;
import ee.ituk.api.mentor.MentorProfileService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(imports = MentorProfileService.class)
public interface ApplicationMapper {

    ApplicationMapper INSTANCE = Mappers.getMapper(ApplicationMapper.class);

    @Mapping(target = "mentorId", source = "application.mentor.id")
    @Mapping(target = "processedById", source = "application.processedBy.id")
    ApplicationDto applicationToDto(Application application);


    @Mapping(target = "processedById", source = "application.processedBy.id")
    ApplicationResponseDto applicationToResponseDto(Application application);

    @Mapping(target = "mentor.id", source = "applicationDto.mentorId")
    @Mapping(target = "processedBy.id", source = "applicationDto.processedById")
    Application applicationToEntity(ApplicationDto applicationDto);

    List<ApplicationResponseDto> applicationsToResponseDto(List<Application> applications);
}
