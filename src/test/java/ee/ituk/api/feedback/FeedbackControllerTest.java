package ee.ituk.api.feedback;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.ituk.api.IntegrationTest;
import ee.ituk.api.utils.ObjectMapperUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class FeedbackControllerTest {

    static final String PATH = "/feedback";
    static final ObjectMapper MAPPER = ObjectMapperUtils.newInstance();

    @Autowired
    MockMvc mvc;

    @Autowired
    FeedbackRepository feedbackRepository;

    @Test
    void testInsert() throws Exception {
        FeedbackDto dto = new FeedbackDto();
        dto.setTitle("tere");
        dto.setText("tekst");

        final String contentAsString = mvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        final FeedbackDto response = MAPPER.readValue(contentAsString, FeedbackDto.class);

        assertThat(response)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt")
                .isEqualTo(dto);

        final Feedback feedbackInDatabase = feedbackRepository.findById(response.getId()).orElseThrow();
        assertThat(response).isEqualTo(entityToDto(feedbackInDatabase));
    }

    private static FeedbackDto entityToDto(Feedback entity) {
        if (entity == null) {
            return null;
        }

        FeedbackDto feedbackDto = new FeedbackDto();

        feedbackDto.setId(entity.getId());
        feedbackDto.setTitle(entity.getTitle());
        feedbackDto.setText(entity.getText());
        feedbackDto.setCreatedAt(entity.getCreatedAt());

        return feedbackDto;
    }
}
