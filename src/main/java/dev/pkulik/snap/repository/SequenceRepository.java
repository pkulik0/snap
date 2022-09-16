package dev.pkulik.snap.repository;

import dev.pkulik.snap.model.Sequence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SequenceRepository extends MongoRepository<Sequence, String> {
}
