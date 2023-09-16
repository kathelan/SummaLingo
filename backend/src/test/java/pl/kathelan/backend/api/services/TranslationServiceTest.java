package pl.kathelan.backend.api.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kathelan.backend.api.client.TranslationApiService;
import pl.kathelan.backend.api.dtos.TranslationDTO;
import pl.kathelan.backend.api.entities.Translation;
import pl.kathelan.backend.api.repositories.TranslationRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TranslationServiceTest {

    @InjectMocks
    private TranslationService translationService;

    @Mock
    private TranslationRepository translationRepository;

    @Mock
    private TranslationApiService translationApiService;

    @Test
    void testUpdateTranslatedText() {
        TranslationDTO translationDTO = new TranslationDTO();
        translationDTO.setSourceText("Cześć");
        translationDTO.setSourceLanguage("PL");
        translationDTO.setTargetLanguage("DE");

        Translation existingTranslation = new Translation();
        existingTranslation.setSourceText("Hej");
        existingTranslation.setSourceLanguage(Translation.Language.POLISH);
        existingTranslation.setTargetLanguage(Translation.Language.GERMAN);

        when(translationApiService.callTranslationApi(any(Translation.class))).thenReturn("Hallo");

        translationService.updateTranslatedText(translationDTO, existingTranslation);

        assertEquals("Hallo", existingTranslation.getTranslatedText());
    }
}


