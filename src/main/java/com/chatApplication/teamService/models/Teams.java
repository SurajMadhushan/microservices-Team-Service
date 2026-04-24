package com.chatApplication.teamService.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Teams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer teamId;

    @Column(unique = true)
    String teamName;

    // list of members in the team
    @ElementCollection
    List<Integer> users;

}
