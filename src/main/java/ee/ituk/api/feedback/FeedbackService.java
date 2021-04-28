package ee.ituk.api.feedback;

import ee.ituk.api.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private static final FeedbackMapper MAPPER = Mappers.getMapper(FeedbackMapper.class);

    private final FeedbackRepository feedbackRepository;

    public FeedbackDto insert(FeedbackDto feedback) {
        final Feedback entity = MAPPER.dtoToEntity(feedback);
        entity.setCreatedAt(Instant.now());

        final Feedback saved = feedbackRepository.save(entity);

        return MAPPER.entityToDto(saved);
    }

    public List<FeedbackDto> getAll() {
        final List<Feedback> entities = feedbackRepository.findAll();

        return MAPPER.entitiesToDtos(entities);
    }

    @Transactional
    public void delete(Long id) {
        final Feedback existingFeedback = feedbackRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        feedbackRepository.delete(existingFeedback.getId());
    }
}
