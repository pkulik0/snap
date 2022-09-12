package dev.pkulik.snap.generator;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
public class IdSequence {
    @Id
    private String id;

    private long sequence;

    public long getSequence() {
        return sequence;
    }

    public void incrementSequence() {
        sequence += 1;
    }
}
