package dev.pkulik.snap.model;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
public class Sequence {
    @Id
    private String id;

    private long sequence;

    public long get() {
        return sequence;
    }

    public void increment() {
        sequence += 1;
    }
}
