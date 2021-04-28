package ee.ituk.api.feedback;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface FeedbackMapper {

    Feedback dtoToEntity(FeedbackDto dto);
    List<FeedbackDto> entitiesToDtos(List<Feedback> entities);

    FeedbackDto entityToDto(Feedback entity);
}
