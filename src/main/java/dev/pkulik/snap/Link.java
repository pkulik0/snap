package dev.pkulik.snap;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "links")
public class Link {

    @Id
    String shortened;

    String url;

    public void setUrl(String url) {
        this.url = url;
    }
}
