package pl.kathelan.backend.api.utils;

import lombok.experimental.UtilityClass;

import java.util.Base64;

@UtilityClass
public class DecodeUtil {

    /**
     * Dekoduje dane zakodowane w formacie Base64.
     *
     * @param encodedData Dane zakodowane w formacie Base64.
     * @return Odkodowane dane.
     */
    public static String decodeBase64(String encodedData) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedData);
        return new String(decodedBytes);
    }
}
