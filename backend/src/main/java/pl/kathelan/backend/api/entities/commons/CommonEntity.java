package pl.kathelan.backend.api.entities.commons;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@MappedSuperclass
@Data
@RequiredArgsConstructor
public class CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CreatedDate
    private LocalDate created;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDate modified;

    @LastModifiedBy
    private String modifiedBy;

    @PrePersist
    public void prePersist() {
        this.created = LocalDate.now();
        if (this.createdBy == null || this.createdBy.isEmpty()) {
            this.createdBy = "SYSTEM";
        }
    }
    @PreUpdate
    public void preUpdate() {
        this.modified = LocalDate.now();
        if (this.modifiedBy == null || this.modifiedBy.isEmpty()) {
            this.modifiedBy = "SYSTEM";
        }
    }
}
