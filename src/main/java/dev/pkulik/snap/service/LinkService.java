package dev.pkulik.snap.service;

import dev.pkulik.snap.model.Link;
import dev.pkulik.snap.repository.LinkRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;

@AllArgsConstructor
@Service
public class LinkService {
    private final LinkRepository linkRepository;

    public Collection<Link> getOwned() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return linkRepository.findLinksByOwnerName(username);
    }

    public Optional<Link> getByShortened(String shortened) {
        Optional<Link> optionalLink = linkRepository.findLinkByShortened(shortened);
        if(optionalLink.isEmpty()) return Optional.empty();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Link link = optionalLink.get();

        if(!link.isPublic() && !link.getOwnerName().equals(username))
            return Optional.empty();

        return optionalLink;
    }

    public void deleteByShortened(String shortened) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        linkRepository.deleteLinkByShortenedAndOwnerName(shortened, username);
    }

    public Optional<Link> updateByShortened(String shortened, Link newLink) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Link> optionalLink =  linkRepository.findLinkByShortenedAndOwnerName(shortened, username);
        if(optionalLink.isEmpty()) return Optional.empty();

        Link link = optionalLink.get();
        link.setUrl(newLink.getUrl());
        link.setPublic(newLink.isPublic());

        linkRepository.save(link);
        return Optional.of(link);
    }

    public Optional<Link> create(Link link) {
        String url = link.getUrl();

        if(!url.startsWith("https://") && !url.startsWith("http://"))
            return Optional.empty();

        if(!url.contains(".")) return Optional.empty();

        Optional<Link> existingLink = linkRepository.findLinkByUrl(url);
        if(existingLink.isPresent()) return existingLink;

        Random rand = new Random();
        String shortened;
        do {
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < 5; i++) {
                boolean upper = rand.nextBoolean();
                builder.append((char) rand.nextInt(upper ? 'A' : 'a', upper ? 'Z' : 'z'));
            }
            shortened = builder.toString();
        } while(linkRepository.findLinkByShortened(shortened).isPresent());


        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Link dbEntry = linkRepository.insert(new Link(shortened, url, username, link.isPublic()));
        return Optional.of(dbEntry);
    }
}
