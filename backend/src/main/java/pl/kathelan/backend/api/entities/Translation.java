package pl.kathelan.backend.api.entities;

import jakarta.persistence.*;
import lombok.*;
import pl.kathelan.backend.api.entities.commons.CommonEntity;

@Entity
@Table(name = "translations")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Builder
public class Translation extends CommonEntity {
    public enum Language {
        BULGARIAN("BG", "Bulgarian"),
        CZECH("CS", "Czech"),
        DANISH("DA", "Danish"),
        GERMAN("DE", "German"),
        GREEK("EL", "Greek"),
        ENGLISH_GB("EN-GB", "English"),
        ENGLISH_US("EN-US", "English"),
        SPANISH("ES", "Spanish"),
        ESTONIAN("ET", "Estonian"),
        FINNISH("FI", "Finnish"),
        FRENCH("FR", "French"),
        HUNGARIAN("HU", "Hungarian"),
        INDONESIAN("ID", "Indonesian"),
        ITALIAN("IT", "Italian"),
        JAPANESE("JA", "Japanese"),
        KOREAN("KO", "Korean"),
        LITHUANIAN("LT", "Lithuanian"),
        LATVIAN("LV", "Latvian"),
        NORWEGIAN_BOKMAL("NB", "Norwegian Bokmal"),
        DUTCH("NL", "Dutch"),
        POLISH("PL", "Polish"),
        PORTUGUESE_BR("PT-BR", "Portuguese"),
        PORTUGUESE_PT("PT-PT", "Portuguese"),
        ROMANIAN("RO", "Romanian"),
        RUSSIAN("RU", "Russian"),
        SLOVAK("SK", "Slovak"),
        SLOVENIAN("SL", "Slovenian"),
        SWEDISH("SV", "Swedish"),
        TURKISH("TR", "Turkish"),
        UKRAINIAN("UK", "Ukrainian"),
        CHINESE("ZH", "Chinese");

        private final String code;
        private final String description;
        Language(String code, String description) {
            this.code = code;
            this.description = description;
        }
        public String getCode() {
            return code;
        }

        public static Language fromCode(String code) {
            for (Language language : Language.values()) {
                if (language.getCode().equalsIgnoreCase(code)) {
                    return language;
                }
            }
            throw new IllegalArgumentException("Unexpected value: " + code);
        }

        public String getDescription() {
            return description;
        }
    }
    @Column(name = "source_text")
    private String sourceText;
    @Column(name = "source_language")
    @Enumerated(EnumType.STRING)
    private Language sourceLanguage;
    @Enumerated(EnumType.STRING)
    @Column(name = "target_language")
    private Language targetLanguage;
    @Column(name = "translated_text")
    private String translatedText;
}

