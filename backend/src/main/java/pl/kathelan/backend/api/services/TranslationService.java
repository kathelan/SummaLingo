package pl.kathelan.backend.api.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kathelan.backend.api.client.TranslationApiService;
import pl.kathelan.backend.api.dtos.TranslationDTO;
import pl.kathelan.backend.api.dtos.TranslationRequest;
import pl.kathelan.backend.api.dtos.TranslationResponse;
import pl.kathelan.backend.api.entities.Translation;
import pl.kathelan.backend.api.mappers.TranslationMapper;
import pl.kathelan.backend.api.repositories.TranslationRepository;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class TranslationService {

    private final TranslationRepository translationRepository;
    private final TranslationApiService translationApiService;

    //TODO update method with some validation when translationRequest fields will be missing
    public TranslationResponse createTranslation(TranslationRequest translationRequest) {
        log.info("Attempting to create a new translation from: {}", translationRequest);

        Translation translation = TranslationMapper.MAPPER.fromTranslationRequest(translationRequest);
        String translatedText = translationApiService.callTranslationApi(translation);
        translation.setTranslatedText(translatedText);
        Translation savedTranslation = translationRepository.save(translation);

        log.info("Successfully created translation: {}", savedTranslation);
        return TranslationMapper.MAPPER.toTranslationResponse(savedTranslation);
    }

    public Translation getTranslationById(Long id) {
        log.info("Fetching translation by ID: {}", id);
        return translationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Translation not found for ID: " + id));
    }
//TODO update method with some validation when translationDTO fields will be missing
    public TranslationResponse updateTranslation(TranslationDTO translationDTO) {
        log.info("Attempting to update translation with ID: {}", translationDTO.getId());

        Translation existingTranslation = getTranslationById(translationDTO.getId());

        if (translationChanged(translationDTO, existingTranslation)) {
            updateTranslatedText(translationDTO, existingTranslation);
        }
        Translation updated = translationRepository.save(existingTranslation);

        log.info("Successfully updated translation: {}", updated);
        return TranslationMapper.MAPPER.toTranslationResponse(updated);
    }

    boolean translationChanged(TranslationDTO translationDTO, Translation existingTranslation) {
        boolean sourceTextChanged = !Objects.equals(translationDTO.getSourceText(), existingTranslation.getSourceText());
        boolean sourceLangChanged = !Objects.equals(translationDTO.getSourceLanguage(), existingTranslation.getSourceLanguage().getCode());
        boolean targetLangChanged = !Objects.equals(translationDTO.getTargetLanguage(), existingTranslation.getTargetLanguage().getCode());

        return sourceTextChanged || sourceLangChanged || targetLangChanged;
    }

    void updateTranslatedText(TranslationDTO translationDTO, Translation existingTranslation) {
        String translatedText = translationApiService.callTranslationApi(TranslationMapper.MAPPER.fromTranslationDTO(translationDTO));
        existingTranslation.setTranslatedText(translatedText);
    }

    public void deleteTranslation(Long id) {
        log.info("Attempting to delete translation with ID: {}", id);
        Translation existingTranslation = getTranslationById(id);
        translationRepository.delete(existingTranslation);
        log.info("Successfully deleted translation with ID: {}", id);
    }
}
