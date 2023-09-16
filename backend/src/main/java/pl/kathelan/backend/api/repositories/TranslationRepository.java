package pl.kathelan.backend.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kathelan.backend.api.entities.Translation;

public interface TranslationRepository extends JpaRepository<Translation, Long> {

}
