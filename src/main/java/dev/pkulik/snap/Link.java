package dev.pkulik.snap;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
public class Link {

    @Id
    public String id;
    public String fullUrl;
    public String shortUrl;
}
