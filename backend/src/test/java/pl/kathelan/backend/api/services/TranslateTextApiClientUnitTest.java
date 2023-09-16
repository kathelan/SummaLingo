package pl.kathelan.backend.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import pl.kathelan.backend.api.client.TranslateTextApiClient;
import pl.kathelan.backend.api.generated.module.InlineObject;
import pl.kathelan.backend.api.generated.module.InlineResponse200;
import pl.kathelan.backend.api.utils.DecodeUtil;

import static org.mockito.Mockito.*;

class TranslateTextApiClientUnitTest {


    @Mock
    private RestTemplate restTemplate;

    @Mock
    private InlineObject inlineObject;
    @InjectMocks
    private TranslateTextApiClient translateTextApiClient;

    private final String fakeApiKey = "fakeApiKey";
    private final String fakeUrl = "http://fake-url.com";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        translateTextApiClient = new TranslateTextApiClient(restTemplate);
        ReflectionTestUtils.setField(translateTextApiClient, "apiKey", fakeApiKey);
        ReflectionTestUtils.setField(translateTextApiClient, "translateUrl", fakeUrl);
    }

    @Test
    void translateText() {
        try (MockedStatic<DecodeUtil> mocked = mockStatic(DecodeUtil.class)) {
            mocked.when(() -> DecodeUtil.decodeBase64(fakeApiKey)).thenReturn(fakeApiKey);

            when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(InlineResponse200.class)))
                    .thenReturn(ResponseEntity.ok(new InlineResponse200()));

            translateTextApiClient.translateText(inlineObject);

            verify(restTemplate, times(1)).exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(InlineResponse200.class));
            mocked.verify(() -> DecodeUtil.decodeBase64(fakeApiKey), times(1));
        }
    }

}


