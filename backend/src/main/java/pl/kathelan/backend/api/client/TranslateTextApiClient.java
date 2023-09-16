package pl.kathelan.backend.api.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kathelan.backend.api.generated.api.TranslateTextApi;
import pl.kathelan.backend.api.generated.module.InlineObject;
import pl.kathelan.backend.api.generated.module.InlineResponse200;
import pl.kathelan.backend.api.utils.DecodeUtil;

@Service
@RequiredArgsConstructor
public class TranslateTextApiClient implements TranslateTextApi {

    @Value("${api.translateUrl}")
    private String translateUrl;

    @Value("${api.apiKey}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<InlineResponse200> translateText(InlineObject inlineObject) {
        HttpHeaders headers = new HttpHeaders();
        String decodedApiKey = DecodeUtil.decodeBase64(apiKey);
        headers.set("Authorization","DeepL-Auth-Key "+ decodedApiKey);
        HttpEntity<InlineObject> requestEntity = new HttpEntity<>(inlineObject, headers);
        return restTemplate.exchange(translateUrl, HttpMethod.POST, requestEntity, InlineResponse200.class);
    }
}
