package dev.pkulik.snap;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@AllArgsConstructor
@Service
public class LinkService {
    final LinkRepository linkRepository;

    public List<Link> getAllLinks() {
        return linkRepository.findAll();
    }

    public Optional<Link> getByShortened(String shortened) {
        return linkRepository.findLinkByShortened(shortened);
    }

    public Optional<Link> deleteByShortened(String shortened) {
        return linkRepository.deleteLinkByShortened(shortened);
    }

    public Optional<Link> updateByShortened(String shortened, String newUrl) {
        Optional<Link> optionalLink =  linkRepository.findLinkByShortened(shortened);
        if(optionalLink.isEmpty()) return Optional.empty();

        Link link = optionalLink.get();
        link.setUrl(newUrl);

        linkRepository.save(link);
        return Optional.of(link);
    }

    public Optional<Link> createLink(String url) {
        if(!url.startsWith("https://") && !url.startsWith("http://")) {
            return Optional.empty();
        }

        if(!url.contains("."))
            return Optional.empty();

        Optional<Link> existingLink = linkRepository.findLinkByUrl(url);
        if(existingLink.isPresent()) return existingLink;

        Random rand = new Random();
        String shortened;
        do {
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < 8; i++) {
                boolean upper = rand.nextBoolean();
                builder.append((char) rand.nextInt(upper ? 'A' : 'a', upper ? 'Z' : 'z'));
            }
            shortened = builder.toString();
        } while(linkRepository.findLinkByShortened(shortened).isPresent());

        Link link = linkRepository.insert(new Link(shortened, url));
        return Optional.of(link);
    }
}
