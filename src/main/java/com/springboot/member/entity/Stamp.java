package com.springboot.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Stamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long stampId;

    @Column
    private int stampCount;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, name = "LAST_CHANGE_DATE")
    private LocalDateTime modifiedAt = LocalDateTime.now();

}
