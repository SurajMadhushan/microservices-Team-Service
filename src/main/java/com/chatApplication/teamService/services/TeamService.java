package com.chatApplication.teamService.services;

import com.chatApplication.teamService.dto.UserData;
import com.chatApplication.teamService.feign.TeamInterface;
import com.chatApplication.teamService.models.Teams;
import com.chatApplication.teamService.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamInterface teamInterface;

    public ResponseEntity<String> createTeam(String teamName, List<Integer> members) {
        if(teamName == null || teamName.isEmpty()) {
            return new ResponseEntity<>("Team name cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if(members == null || members.isEmpty()) {
            return new ResponseEntity<>("Team must have at least one member", HttpStatus.BAD_REQUEST);
        }

        Teams team = new Teams();
        team.setTeamName(teamName);
        team.setUsers(members);

        // save team to database
        try {
            teamRepository.save(team);
        }
        catch (Exception e) {
            System.out.println("Error saving team: " + e.getMessage());
            if(e.getMessage().contains("Duplicate entry")) {
                return new ResponseEntity<>("Team name already exists", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("Error occurred while creating team", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Team created successfully", HttpStatus.OK);
    }

    public ResponseEntity<List> getAllTeams() {
        List<Teams> teams = teamRepository.findAll();
        if (teams.isEmpty()) {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }


    public ResponseEntity<?> getTeamMembers(Integer teamId) {
        Teams team = teamRepository.findById(teamId).orElse(null);
        if (team == null) {
            return new ResponseEntity<>("Team not found", HttpStatus.NOT_FOUND);
        }

        List<Integer> memberIds = team.getUsers();
        ArrayList<UserData> memberDetails = new ArrayList<>();

        for (Integer memberId : memberIds) {
            ResponseEntity<UserData> response = teamInterface.getUserById(memberId);
            System.out.println("Response for member ID " + memberId + ": " + response);
            UserData userData = response.getBody();
            System.out.println("User data for member ID " + memberId + ": " + userData);

            if (response.getStatusCode() == HttpStatus.OK) {
                memberDetails.add(userData);
            } else {
                System.out.println("Failed to fetch details for member ID: " + memberId);
            }

        }
        return new ResponseEntity<>(memberDetails, HttpStatus.OK);
    }
}
