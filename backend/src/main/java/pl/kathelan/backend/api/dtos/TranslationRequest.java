package pl.kathelan.backend.api.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TranslationRequest {

    private String sourceText;
    private String sourceLanguage;
    private String targetLanguage;
}
