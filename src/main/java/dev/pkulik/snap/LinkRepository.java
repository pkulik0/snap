package dev.pkulik.snap;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LinkRepository extends MongoRepository<Link, String> {
    Optional<Link> findLinkByShortened(String shortened);

    Optional<Link> findLinkByUrl(String url);
}
