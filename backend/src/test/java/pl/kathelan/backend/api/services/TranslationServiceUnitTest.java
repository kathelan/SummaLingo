package pl.kathelan.backend.api.services;

import org.junit.jupiter.api.Test;
import pl.kathelan.backend.api.dtos.TranslationDTO;
import pl.kathelan.backend.api.entities.Translation;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TranslationServiceUnitTest {

    private final TranslationService translationService = new TranslationService(null, null);
    @Test
    void testTranslationChanged() {
        TranslationDTO translationDTO = new TranslationDTO();
        translationDTO.setSourceText("Cześć");
        translationDTO.setSourceLanguage("PL");
        translationDTO.setTargetLanguage("DE");

        Translation existingTranslation = new Translation();
        existingTranslation.setSourceText("Hej");
        existingTranslation.setSourceLanguage(Translation.Language.POLISH);
        existingTranslation.setTargetLanguage(Translation.Language.GERMAN);

        assertTrue(translationService.translationChanged(translationDTO, existingTranslation));
    }

}
