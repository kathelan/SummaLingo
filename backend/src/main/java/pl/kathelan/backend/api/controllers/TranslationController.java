package pl.kathelan.backend.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kathelan.backend.api.dtos.TranslationDTO;
import pl.kathelan.backend.api.dtos.TranslationRequest;
import pl.kathelan.backend.api.dtos.TranslationResponse;
import pl.kathelan.backend.api.entities.Translation;
import pl.kathelan.backend.api.mappers.TranslationMapper;
import pl.kathelan.backend.api.services.TranslationService;

@RestController
@Tag(name = "Translations", description = "Translation endpoint for translating text")
@RequestMapping("/api/translations")
@RequiredArgsConstructor
public class TranslationController {

    private final TranslationService translationService;

    @PostMapping
    public ResponseEntity<TranslationResponse> createTranslation(@RequestBody TranslationRequest translationRequest) {
        TranslationResponse createdTranslation = translationService.createTranslation(translationRequest);
        return new ResponseEntity<>(createdTranslation, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TranslationResponse> getTranslationById(@PathVariable Long id) {
        Translation translation = translationService.getTranslationById(id);
        return new ResponseEntity<>(TranslationMapper.MAPPER.toTranslationResponse(translation), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TranslationResponse> updateTranslation(@RequestBody TranslationDTO updatedTranslationDTO) {
        TranslationResponse translationDTO = translationService.updateTranslation(updatedTranslationDTO);
        return new ResponseEntity<>(translationDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTranslation(@PathVariable Long id) {
        translationService.deleteTranslation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


