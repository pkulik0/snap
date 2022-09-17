package dev.pkulik.snap.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Getter @Setter
@AllArgsConstructor
@Document(collection = "links")
public class Link {
    @Id
    private String shortened;

    @NotNull
    private String url;

    @NotNull
    @JsonIgnore
    private String ownerName;

    @NotNull
    private boolean isPublic;
}
