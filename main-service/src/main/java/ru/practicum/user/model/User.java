package ru.practicum.user.model;

import lombok.*;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;
}
