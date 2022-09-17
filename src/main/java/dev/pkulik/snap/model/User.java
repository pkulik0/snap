package dev.pkulik.snap.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Document(collection = "users")
@Getter @Setter
public class User {
    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private Long id;

    @NotNull
    @Size(min = 4, max = 20)
    @Indexed(unique = true)
    private String username;

    @NotNull
    @Indexed(unique = true)
    private String email;

    @NotNull
    private String password;
}
