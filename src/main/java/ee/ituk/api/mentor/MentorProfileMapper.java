package ee.ituk.api.mentor;

import ee.ituk.api.mentor.domain.MentorProfile;
import ee.ituk.api.mentor.dto.MentorProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MentorProfileMapper {
    MentorProfileMapper INSTANCE = Mappers.getMapper(MentorProfileMapper.class);

    MentorProfileDto mentorprofileToDto(MentorProfile mentorProfile);
    MentorProfile mentorprofileDtoToEntity(MentorProfileDto mentorProfileDto);

    List<MentorProfileDto> mentorprofilesToDto(List<MentorProfile> mentorProfiles);
}
