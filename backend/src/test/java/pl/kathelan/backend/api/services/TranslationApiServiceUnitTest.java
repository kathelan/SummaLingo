package pl.kathelan.backend.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.kathelan.backend.api.client.TranslateTextApiClient;
import pl.kathelan.backend.api.client.TranslationApiService;
import pl.kathelan.backend.api.entities.Translation;
import pl.kathelan.backend.api.generated.module.InlineResponse200;
import pl.kathelan.backend.api.generated.module.InlineResponse200Translations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TranslationApiServiceUnitTest {

    @InjectMocks
    private TranslationApiService translationApiService;

    @Mock
    private TranslateTextApiClient translateTextApiClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCallTranslationApi_successfulResponse() {
        Translation translation = new Translation();
        translation.setSourceText("test");
        translation.setSourceLanguage(Translation.Language.POLISH);
        translation.setTargetLanguage(Translation.Language.GERMAN);
        InlineResponse200 responseBody = new InlineResponse200();
        responseBody.setTranslations(List.of(new InlineResponse200Translations().text("testowanie")));

        when(translateTextApiClient.translateText(any())).thenReturn(new ResponseEntity<>(responseBody, HttpStatus.OK));

        String translatedText = translationApiService.callTranslationApi(translation);
        assertEquals("testowanie", translatedText);
    }

    @Test
    void testCallTranslationApi_unsuccessfulResponse() {
        Translation translation = new Translation();
        translation.setSourceText("test");

        when(translateTextApiClient.translateText(any())).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        assertThrows(RuntimeException.class, () -> translationApiService.callTranslationApi(translation));
    }
}

