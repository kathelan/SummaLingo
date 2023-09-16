package pl.kathelan.backend.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.kathelan.backend.api.dtos.TranslationDTO;
import pl.kathelan.backend.api.dtos.TranslationRequest;
import pl.kathelan.backend.api.dtos.TranslationResponse;
import pl.kathelan.backend.api.entities.Translation;
import pl.kathelan.backend.api.services.TranslationService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TranslationController.class)
class TranslationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TranslationService translationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String BASE_URL = "/api/translations";
    private TranslationRequest translationRequest;
    private TranslationDTO translationDTO;
    private TranslationResponse translationResponse;

    @BeforeEach
    void setUp() {
        translationRequest = new TranslationRequest();
        translationRequest.setSourceText("Cześć");
        translationRequest.setSourceLanguage("PL");
        translationRequest.setTargetLanguage("DE");

        translationDTO = new TranslationDTO();
        translationDTO.setId(1L);
        translationDTO.setSourceText("Cześć");
        translationDTO.setSourceLanguage("PL");
        translationDTO.setTargetLanguage("DE");

        translationResponse = new TranslationResponse();
        translationResponse.setTranslatedText("Hallo");
        translationResponse.setSourceLanguage("PL");
        translationResponse.setTargetLanguage("DE");
    }

    @Test
    void createTranslationTest() throws Exception {
        mockCreateTranslation();

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(translationRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void getTranslationByIdTest() throws Exception {
        Long id = 1L;
        mockGetTranslationById(id);

        mockMvc.perform(get(BASE_URL + "/" + id))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTranslationTest() throws Exception {
        Long translationId = 1L;

        doNothing().when(translationService).deleteTranslation(translationId);

        mockMvc.perform(delete(BASE_URL + "/" + translationId))
                .andExpect(status().isNoContent());

        verify(translationService, times(1)).deleteTranslation(translationId);
    }

    @Test
    void updateTranslationTest() throws Exception {
        long translationId = 1L;
        mockUpdateTranslation();

        mockMvc.perform(put(BASE_URL + "/" + translationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(translationDTO)))
                .andExpect(status().isOk());

        verify(translationService, times(1)).updateTranslation(any(TranslationDTO.class));
    }

    private void mockCreateTranslation() {
        when(translationService.createTranslation(any(TranslationRequest.class))).thenReturn(translationResponse);
    }

    private void mockGetTranslationById(Long id) {
        Translation translation = new Translation();
        translation.setSourceText("Cześć");
        translation.setSourceLanguage(Translation.Language.POLISH);
        translation.setTargetLanguage(Translation.Language.GERMAN);
        when(translationService.getTranslationById(id)).thenReturn(translation);
    }

    private void mockUpdateTranslation() {
        when(translationService.updateTranslation(any(TranslationDTO.class))).thenReturn(translationResponse);
    }
}
