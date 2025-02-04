package br.com.miaulabs.tcgscanner.model.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@ToString
@NoArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected Long id;

    @JsonIgnore
    LocalDateTime deletedAt;

    @JsonIgnore
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @JsonIgnore
    @CreationTimestamp
    LocalDateTime createdAt;


}
