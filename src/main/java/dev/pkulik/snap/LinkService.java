package dev.pkulik.snap;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class LinkService {
    final LinkRepository linkRepository;

    public List<Link> getLinks() {
        return List.of(
                new Link("0", "hello", "bye")
        );
    }
}
