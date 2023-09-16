package pl.kathelan.backend.api.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.kathelan.backend.api.entities.Translation;
import pl.kathelan.backend.api.generated.module.InlineObject;
import pl.kathelan.backend.api.generated.module.InlineResponse200;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TranslationApiService {

    private final TranslateTextApiClient translateTextApiClient;

    public String callTranslationApi(Translation translation) {
        InlineObject inlineObject = new InlineObject();
        inlineObject.setText(List.of(translation.getSourceText()));
        inlineObject.setSourceLang(InlineObject.SourceLangEnum.fromValue(translation.getSourceLanguage().getCode()));
        inlineObject.setTargetLang(InlineObject.TargetLangEnum.fromValue(translation.getTargetLanguage().getCode()));

        ResponseEntity<InlineResponse200> response = translateTextApiClient.translateText(inlineObject);

        if (response.getStatusCode().is2xxSuccessful() && !response.getBody().getTranslations().isEmpty()) {
            return response.getBody().getTranslations().get(0).getText();
        }

        log.error("Failed to translate text: {}", response);
        throw new RuntimeException("Error while translating the text.");
    }
}
