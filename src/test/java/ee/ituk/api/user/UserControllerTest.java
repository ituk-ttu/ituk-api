package ee.ituk.api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.ituk.api.IntegrationTest;
import ee.ituk.api.common.exception.ApiException;
import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.user.dto.UserDto;
import ee.ituk.api.user.dto.UserStatusDto;
import ee.ituk.api.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static ee.ituk.api.common.validation.ValidationUtil.EMAIL_INCORRECT;
import static ee.ituk.api.common.validation.ValidationUtil.NAME_MISSING;
import static ee.ituk.api.common.validation.ValidationUtil.PERSONAL_CODE_INCORRECT;
import static ee.ituk.api.common.validation.ValidationUtil.STUDENT_CODE_INCORRECT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class UserControllerTest {

    static final String PATH = "/user";
    static final ObjectMapper MAPPER = ObjectMapperUtils.newInstance();

    final MockMvc mvc;

    @Test
    void testCreate() throws Exception {
        UserStatusDto userStatusDto = new UserStatusDto();
        userStatusDto.setStatusName("Noorliige");
        userStatusDto.setDescription("asd");

        UserDto userDto = new UserDto();
        userDto.setFirstName("first name");
        userDto.setLastName("last name");
        userDto.setEmail("email@ituk.ee");
        userDto.setCardNumber("sadasd");
        userDto.setStudentCode("202020IABB");
        userDto.setPersonalCode("39705066047");
        userDto.setStatus(userStatusDto);
        userDto.setCurriculum("asd");
        userDto.setIban("asd");
        userDto.setRole("MEMBER");
        userDto.setCreatedAt(LocalDateTime.now());
        userDto.setUpdatedAt(LocalDateTime.now());

        final String contentAsString = mvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        UserDto response = MAPPER.readValue(contentAsString, UserDto.class);

        assertThat(response).usingRecursiveComparison()
                .ignoringFields("id", "archived", "isMentor", "status")
                .isEqualTo(userDto);
    }

    @Test
    void testCreateFailures() throws Exception {
        UserStatusDto userStatusDto = new UserStatusDto();
        userStatusDto.setStatusName("Noorliige");
        userStatusDto.setDescription("asd");

        UserDto userDto = new UserDto();
        userDto.setFirstName(null);
        userDto.setLastName(null);
        userDto.setEmail(UUID.randomUUID().toString());
        userDto.setCardNumber("sadasd");
        userDto.setStudentCode(UUID.randomUUID().toString());
        userDto.setPersonalCode(UUID.randomUUID().toString());
        userDto.setStatus(userStatusDto);
        userDto.setCurriculum("asd");
        userDto.setIban("asd");
        userDto.setRole("MEMBER");
        userDto.setCreatedAt(LocalDateTime.now());
        userDto.setUpdatedAt(LocalDateTime.now());

        final ApiException resolvedException = ((ApiException) mvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(userDto)))
                .andExpect(status().is4xxClientError())
                .andReturn().getResolvedException());

        assertThat(resolvedException.getMessages()).extracting(ErrorMessage::getCode).containsExactlyInAnyOrder(
                NAME_MISSING,
                STUDENT_CODE_INCORRECT,
                EMAIL_INCORRECT,
                PERSONAL_CODE_INCORRECT
        );
        assertThat(resolvedException.getError()).isEqualTo("Validation failed");
    }
}
