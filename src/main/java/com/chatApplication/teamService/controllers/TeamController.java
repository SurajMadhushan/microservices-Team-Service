package com.chatApplication.teamService.controllers;

import com.chatApplication.teamService.dto.CreateTeamRequest;
import com.chatApplication.teamService.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    TeamService teamService;

    // create a team
    @PostMapping("/create")
    public ResponseEntity<String> CreateTeam(@RequestBody CreateTeamRequest createTeamRequest) {
        return teamService.createTeam(createTeamRequest.getTeamName(), createTeamRequest.getMembers());
    }

    // get all teams
    @GetMapping("/getAll")
    public ResponseEntity<List> getAllTeams() {
        return teamService.getAllTeams();
    }

    // get team member details by id
    @GetMapping("/{teamId}/members")
    public ResponseEntity<?> getTeamMembers(@PathVariable Integer teamId) {
        return teamService.getTeamMembers(teamId);
    }
}
