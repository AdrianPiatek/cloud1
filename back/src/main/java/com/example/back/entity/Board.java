package com.example.back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue
    private Long id;
    @Builder.Default
    @Enumerated
    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Sign> grid = new ArrayList<>(Collections.nCopies(9, Sign.EMPTY));
    @ManyToOne
    private User player1;
    @ManyToOne
    private User player2;
    @OneToMany(fetch = FetchType.EAGER)
    @Builder.Default
    private List<Result> results = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private State state;
}
