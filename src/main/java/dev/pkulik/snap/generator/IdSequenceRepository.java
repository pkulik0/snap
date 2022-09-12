package dev.pkulik.snap.generator;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IdSequenceRepository extends MongoRepository<IdSequence, String> {
}
