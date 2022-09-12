package dev.pkulik.snap.generator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GeneratorService {

    private final IdSequenceRepository idSequenceRepository;

    public long getNextId(String sequenceName) {
        Optional<IdSequence> optionalIdSequence = idSequenceRepository.findById(sequenceName);

        if(optionalIdSequence.isEmpty()) {
            long id = 0;
            idSequenceRepository.insert(new IdSequence(sequenceName, id));
            return id;
        }

        IdSequence idSequence = optionalIdSequence.get();
        idSequence.incrementSequence();
        idSequenceRepository.save(idSequence);

        return idSequence.getSequence();
    }
}
