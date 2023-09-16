package pl.kathelan.backend.api.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import pl.kathelan.backend.api.client.TranslationApiService;
import pl.kathelan.backend.api.dtos.TranslationDTO;
import pl.kathelan.backend.api.dtos.TranslationRequest;
import pl.kathelan.backend.api.dtos.TranslationResponse;
import pl.kathelan.backend.api.entities.Translation;
import pl.kathelan.backend.api.repositories.TranslationRepository;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class TranslationServiceDbTest {

    @Autowired
    private TranslationService translationService;

    @Autowired
    private TranslationRepository translationRepository;

    @MockBean
    private TranslationApiService translationApiService;

    @BeforeEach
    void setup() {
        translationRepository.deleteAll();
    }

    @Test
    void testCreateTranslation() {
        TranslationRequest request = new TranslationRequest();
        request.setSourceText("Cześć");
        request.setSourceLanguage("PL");
        request.setTargetLanguage("DE");

        when(translationApiService.callTranslationApi(any())).thenReturn("Hallo");

        TranslationResponse result = translationService.createTranslation(request);

        assertNotNull(result);
        assertEquals(result.getSourceText(), request.getSourceText());
        assertEquals(result.getTargetLanguage(), request.getTargetLanguage());
        assertEquals(result.getSourceLanguage(), request.getSourceLanguage());
    }

    @Test
    void testGetTranslationById() {
        Translation translation = new Translation();
        translation.setSourceText("Cześć");
        translation.setTranslatedText("Hallo");
        Translation translationSaved = translationRepository.save(translation);

        Translation result = translationService.getTranslationById(translationSaved.getId());
        assertNotNull(result);
        assertEquals("Cześć", result.getSourceText());
        assertEquals(result.getId(), translationSaved.getId());
    }

    @Test
    void testUpdateTranslation() {
        Translation translation = new Translation();
        translation.setSourceText("Cześć");
        translation.setTargetLanguage(Translation.Language.GERMAN);
        translation.setSourceLanguage(Translation.Language.POLISH);
        Translation savedTranslation = translationRepository.save(translation);

        TranslationDTO translationDTO = new TranslationDTO();
        translationDTO.setId(savedTranslation.getId());
        translationDTO.setTargetLanguage("DE");
        translationDTO.setSourceLanguage("PL");
        translationDTO.setSourceText("Hej");

        when(translationApiService.callTranslationApi(any())).thenReturn("Hej");
        TranslationResponse result = translationService.updateTranslation(translationDTO);

        assertNotNull(result);
        assertEquals("Hej", result.getTranslatedText());
    }

    @Test
    void testDeleteTranslation() {
        Translation translation = new Translation();
        translation.setSourceText("Cześć");
        translation.setTranslatedText("Hallo");
        Translation savedTranslation = translationRepository.save(translation);

        translationService.deleteTranslation(savedTranslation.getId());

        assertThrows(EntityNotFoundException.class, () -> translationService.getTranslationById(savedTranslation.getId()));
    }
}
