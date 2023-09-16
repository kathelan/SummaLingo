package pl.kathelan.backend.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pl.kathelan.backend.api.dtos.TranslationDTO;
import pl.kathelan.backend.api.dtos.TranslationRequest;
import pl.kathelan.backend.api.dtos.TranslationResponse;
import pl.kathelan.backend.api.entities.Translation;
import pl.kathelan.backend.api.generated.module.InlineObject;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TranslationMapper {

    TranslationMapper MAPPER = Mappers.getMapper(TranslationMapper.class);

    @Mapping(source = "sourceText", target = "text", qualifiedByName = "convertTextToList")
    @Mapping(source = "sourceLanguage", target = "sourceLang", qualifiedByName = "convertToSourceLangEnum")
    @Mapping(source = "targetLanguage", target = "targetLang", qualifiedByName = "convertToTargetLangEnum")
    InlineObject fromTranslationToInlineObject(Translation translation);

    TranslationDTO toTranslationDTO(Translation translation);
    Translation fromTranslationDTO(TranslationDTO translationDTO);
    Translation fromTranslationRequest(TranslationRequest translationRequest);
    TranslationResponse toTranslationResponse(Translation translation);
    Translation fromTranslationResponse(TranslationResponse translationResponse);

    @Named("convertToTargetLangEnum")
    default InlineObject.TargetLangEnum convertToTargetLangEnum(Translation.Language language) {
        return (language == null) ? null : InlineObject.TargetLangEnum.fromValue(language.getCode());
    }

    @Named("convertToSourceLangEnum")
    default InlineObject.SourceLangEnum convertToSourceLangEnum(Translation.Language language) {
        return (language == null) ? null : InlineObject.SourceLangEnum.fromValue(language.getCode());
    }

    @Named("convertTextToList")
    default List<String> convertTextToList(String text) {
        return (text == null) ? Collections.emptyList() : List.of(text);
    }
    default Translation.Language stringToLanguage(String code) {
        return Translation.Language.fromCode(code);
    }

    default String languageToString(Translation.Language language) {
        return language.getCode();
    }
}
