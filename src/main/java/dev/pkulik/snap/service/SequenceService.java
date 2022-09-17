package dev.pkulik.snap.service;

import dev.pkulik.snap.repository.SequenceRepository;
import dev.pkulik.snap.model.Sequence;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SequenceService {
    private final SequenceRepository sequenceRepository;

    public long getNextId(String sequenceName) {
        Optional<Sequence> optionalSequence = sequenceRepository.findById(sequenceName);

        if(optionalSequence.isEmpty()) {
            long id = 0;
            sequenceRepository.insert(new Sequence(sequenceName, id));
            return id;
        }

        Sequence sequence = optionalSequence.get();
        sequence.increment();
        sequenceRepository.save(sequence);

        return sequence.get();
    }
}
