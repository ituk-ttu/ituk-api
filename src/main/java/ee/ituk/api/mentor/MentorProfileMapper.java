package ee.ituk.api.mentor;

import ee.ituk.api.mentor.domain.MentorProfile;
import ee.ituk.api.mentor.dto.MentorProfileDto;
import ee.ituk.api.user.UserMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = UserMapper.class)
public interface MentorProfileMapper {

    MentorProfileDto mentorProfileToDto(MentorProfile mentorProfile);

    MentorProfile mentorProfileDtoToEntity(MentorProfileDto mentorProfileDto);

    List<MentorProfileDto> mentorProfilesToDto(List<MentorProfile> mentorProfiles);
}
