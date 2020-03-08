package ee.ituk.api.application;

import ee.ituk.api.application.domain.Application;
import ee.ituk.api.application.dto.ApplicationDto;
import ee.ituk.api.application.dto.ApplicationResponseDto;
import ee.ituk.api.mentor.MentorProfileService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(imports = MentorProfileService.class)
public interface ApplicationMapper {

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
