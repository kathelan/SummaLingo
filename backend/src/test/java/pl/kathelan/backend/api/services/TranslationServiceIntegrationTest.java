package pl.kathelan.backend.api.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.kathelan.backend.api.client.TranslationApiService;
import pl.kathelan.backend.api.dtos.TranslationRequest;
import pl.kathelan.backend.api.entities.Translation;
import pl.kathelan.backend.api.repositories.TranslationRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class TranslationServiceIntegrationTest {

    @InjectMocks
    private TranslationService translationService;

    @Mock
    private TranslationRepository translationRepository;

    @Mock
    private TranslationApiService translationApiService;

    @Test
    void testCreateTranslation() {
        TranslationRequest translationRequest = new TranslationRequest();
        translationRequest.setSourceText("Cześć");
        translationRequest.setSourceLanguage("PL");
        translationRequest.setTargetLanguage("DE");

        Translation translation = Translation.builder()
                .sourceLanguage(Translation.Language.POLISH)
                .targetLanguage(Translation.Language.GERMAN)
                .build();

        when(translationApiService.callTranslationApi(any())).thenReturn("Hallo");
        when(translationRepository.save(any())).thenReturn(translation);

        translationService.createTranslation(translationRequest);

        verify(translationRepository, times(1)).save(any());
    }

    @Test
    void testGetTranslationByIdFound() {
        when(translationRepository.findById(1L)).thenReturn(Optional.of(new Translation()));

        Translation result = translationService.getTranslationById(1L);

        assertNotNull(result);
    }

    @Test
    void testGetTranslationByIdNotFound() {
        when(translationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> translationService.getTranslationById(1L));
    }

    @Test
    void testDeleteTranslation() {
        when(translationRepository.findById(1L)).thenReturn(Optional.of(new Translation()));

        translationService.deleteTranslation(1L);

        verify(translationRepository, times(1)).delete(any());
    }
}

