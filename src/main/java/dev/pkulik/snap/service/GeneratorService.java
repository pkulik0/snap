package dev.pkulik.snap.service;

import dev.pkulik.snap.repository.SequenceRepository;
import dev.pkulik.snap.model.Sequence;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GeneratorService {

    private final SequenceRepository sequenceRepository;

    public long getNextId(String sequenceName) {
        Optional<Sequence> optionalIdSequence = sequenceRepository.findById(sequenceName);

        if(optionalIdSequence.isEmpty()) {
            long id = 0;
            sequenceRepository.insert(new Sequence(sequenceName, id));
            return id;
        }

        Sequence sequence = optionalIdSequence.get();
        sequence.increment();
        sequenceRepository.save(sequence);

        return sequence.get();
    }
}
