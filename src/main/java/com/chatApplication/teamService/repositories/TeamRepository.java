package com.chatApplication.teamService.repositories;

import com.chatApplication.teamService.models.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Teams, Integer> {

}
