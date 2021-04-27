package ee.ituk.api.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ee.ituk.api.IntegrationTest;
import ee.ituk.api.application.domain.Application;
import ee.ituk.api.application.dto.ApplicationDto;
import ee.ituk.api.application.service.ApplicationService;
import ee.ituk.api.common.exception.ApiException;
import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.mentor.MentorProfileRepository;
import ee.ituk.api.mentor.MentorProfileService;
import ee.ituk.api.mentor.domain.MentorProfile;
import ee.ituk.api.mentor.dto.MentorProfileDto;
import ee.ituk.api.user.UserService;
import ee.ituk.api.user.domain.User;
import ee.ituk.api.utils.ObjectMapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ee.ituk.api.application.dto.ApplicationStatus.ACCEPTED;
import static ee.ituk.api.application.dto.ApplicationStatus.WAITING;
import static ee.ituk.api.common.validation.ValidationUtil.EMAIL_INCORRECT;
import static ee.ituk.api.common.validation.ValidationUtil.NAME_MISSING;
import static ee.ituk.api.common.validation.ValidationUtil.PERSONAL_CODE_INCORRECT;
import static ee.ituk.api.common.validation.ValidationUtil.STUDENT_CODE_INCORRECT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class ApplicationControllerTest {

    static final String APPLICATION_API_BASE = "/application/";
    static final String MENTOR_API_BASE = "/mentor/";

    static final ObjectMapper MAPPER = ObjectMapperUtils.newInstance();
    static final TypeReference<List<MentorProfileDto>> MENTOR_PROFILE_LIST_TYPEREF = new TypeReference<>() {
    };

    @Autowired
    MockMvc mvc;

    @Autowired
    ApplicationService applicationService;

    @Autowired
    UserService userService;

    @Autowired
    MentorProfileService mentorProfileService;

    @Autowired
    MentorProfileRepository mentorProfileRepository;

    ApplicationDto application;

    @BeforeEach
    void setUp() {
        application = new ApplicationDto();
        application.setFirstName(UUID.randomUUID().toString());
        application.setLastName(UUID.randomUUID().toString());
        application.setPersonalCode(UUID.randomUUID().toString());
        application.setEmail(UUID.randomUUID().toString());
        application.setStudentCode(UUID.randomUUID().toString());
        application.setCurriculum(UUID.randomUUID().toString());
        application.setStatus(WAITING.name());
    }

    @Test
    void testApplicationCreateFail() throws Exception {
        application.setFirstName(null);
        application.setLastName(null);
        final ApiException resolvedException = ((ApiException) mvc.perform(post(APPLICATION_API_BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsBytes(application)))
                .andExpect(status().is4xxClientError())
                .andReturn().getResolvedException());

        assertThat(resolvedException.getMessages()).extracting(ErrorMessage::getCode).containsExactlyInAnyOrder(
                STUDENT_CODE_INCORRECT,
                PERSONAL_CODE_INCORRECT,
                EMAIL_INCORRECT,
                NAME_MISSING
        );
        assertThat(resolvedException.getError()).isEqualTo("Validation failed");
    }

    @Test
    void testApplicationCreateSuccess() throws Exception {
        application.setFirstName("Toomas");
        application.setLastName("Uba");
        application.setStudentCode("202020iabb");
        application.setPersonalCode("39705066047");
        application.setEmail("email@ituk.ee");

        final String contentAsString = mvc.perform(post(APPLICATION_API_BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsBytes(application)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        final ApplicationDto applicationDto = MAPPER.readValue(contentAsString, ApplicationDto.class);
        assertThat(applicationDto.getId()).isNotNull();
        assertThat(applicationDto.getMentorSelectionCode()).isNotNull();
        assertThat(applicationDto.getStudentCode()).isEqualTo("202020IABB");
    }

    @Test
    void testApplicationCreateSelectMentor() throws Exception {
        application.setFirstName("Toomas");
        application.setLastName("Uba");
        application.setStudentCode("202020IABB");
        application.setPersonalCode("39705066047");
        application.setEmail("email@ituk.ee");

        String contentAsString = mvc.perform(post(APPLICATION_API_BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsBytes(application)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ApplicationDto applicationDto = MAPPER.readValue(contentAsString, ApplicationDto.class);


        // create user from the application
        mvc.perform(put(APPLICATION_API_BASE + "{applicationId}/status", applicationDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsBytes(Map.of("status", ACCEPTED.name()))))
                .andExpect(status().isNoContent());

        // make mentor
        User userById = userService.findUserById(1);
        userById.setMentor(true);
        userService.createUser(userById);

        MentorProfile mentorProfile = mentorProfileService.getByUserId(userById.getId());
        mentorProfile.setEnabled(true);
        mentorProfileRepository.save(mentorProfile);


        application.setFirstName("Joonas");
        application.setLastName("Uba");
        application.setStudentCode("202021IABB");
        application.setPersonalCode("39705066047");
        application.setEmail("eemail@ituk.ee");

        contentAsString = mvc.perform(post(APPLICATION_API_BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsBytes(application)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        applicationDto = MAPPER.readValue(contentAsString, ApplicationDto.class);

        mvc.perform(get(APPLICATION_API_BASE + "apply/{applicationId}/{selectionCode}", applicationDto.getId(), applicationDto.getMentorSelectionCode()))
                .andExpect(status().isOk());

        contentAsString = mvc.perform(get(MENTOR_API_BASE + "active"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        final List<MentorProfileDto> mentorProfiles = MAPPER.readValue(contentAsString, MENTOR_PROFILE_LIST_TYPEREF);
        assertThat(mentorProfiles).hasSize(1);

        mvc.perform(put(APPLICATION_API_BASE + "mentor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(Map.of(
                        "applicationId", applicationDto.getId(),
                        "mentorId", mentorProfiles.get(0).getId()
                ))))
                .andExpect(status().isNoContent());

        final Application createdApplicationWithId = applicationService.findApplicationById(applicationDto.getId());

        assertThat(createdApplicationWithId.getMentor().getId()).isEqualTo(mentorProfiles.get(0).getUser().getId());
    }
}
