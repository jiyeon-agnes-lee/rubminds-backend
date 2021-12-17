package com.rubminds.api.team.web;

import com.rubminds.api.post.dto.PostResponse;
import com.rubminds.api.skill.dto.SkillResponse;
import com.rubminds.api.team.Service.TeamService;
import com.rubminds.api.team.domain.Team;
import com.rubminds.api.team.dto.TeamResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/team")
public class TeamController {
    private final TeamService teamService;

    @GetMapping("/{teamid}")
    public ResponseEntity<TeamResponse.GetTeam> TeamInfo(@PathVariable Long teamid) {
        TeamResponse.GetTeam infoResponse = teamService.getTeamInfo(teamid);
        return ResponseEntity.ok().body(infoResponse);
    }



}

