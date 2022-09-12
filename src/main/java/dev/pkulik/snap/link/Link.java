package dev.pkulik.snap.link;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "links")
public class Link {

    @Id
    private String shortened;

    private String url;

    public void setUrl(String url) {
        this.url = url;
    }
}
