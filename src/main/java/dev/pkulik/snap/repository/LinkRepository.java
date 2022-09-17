package dev.pkulik.snap.repository;

import dev.pkulik.snap.model.Link;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.Optional;

public interface LinkRepository extends MongoRepository<Link, String> {
    Optional<Link> findLinkByShortened(String shortened);

    Optional<Link> findLinkByUrl(String url);

    Collection<Link> findLinksByOwnerName(String ownerName);

    Optional<Link> findLinkByShortenedAndOwnerName(String shortened, String ownerName);

    void deleteLinkByShortenedAndOwnerName(String shortened, String ownerName);
}
