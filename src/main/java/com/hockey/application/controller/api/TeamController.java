package com.hockey.application.controller.api;

import com.hockey.core.interfaces.services.TeamService;
import com.hockey.infrastructure.dto.GameDTO;
import com.hockey.infrastructure.dto.TeamRosterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.hockey.application.config.Constants.GAME_COUNT;

@Controller
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

//    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/teams")
    public ModelAndView defaultStandings() {
        ModelAndView modelAndView = new ModelAndView("teams/index");

        modelAndView.addObject("teams",  teamService.getAllTeams());

        return modelAndView;
    }

    //    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/teams/{id}")
    public ModelAndView concreteStandings(@PathVariable(name = "id") String id) {
        ModelAndView modelAndView = new ModelAndView("teams/detail");

        long teamId = Long.parseLong(id);
        modelAndView.addObject("baseInfo", teamService.getBaseInfo(teamId));
        modelAndView.addObject("lastGames", teamService.getTeamLastGames(teamId, 5));
        List<GameDTO> gameInPeriod = teamService.getGameInPeriod(teamId, 10);
        System.out.println(gameInPeriod);

        modelAndView.addObject("comingGames", gameInPeriod);
        modelAndView.addObject("roster", teamService.getRoster(teamId));

        return modelAndView;
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/teams/{id}/roster")
    public List<TeamRosterDTO> teamRoster(@PathVariable(name = "id") Long id) {
        return teamService.getRoster(id);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/teams/{id}/last-game")
    public List<GameDTO> getLastGames(@PathVariable(name = "id") Long teamId) {
        return teamService.getTeamLastGames(teamId, GAME_COUNT);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/teams/{id}/game-in-period")
    public List<GameDTO> getLastGames(@PathVariable(name = "id") Long teamId, @RequestParam(name = "dayOffset") Integer dayOffset) {
        return teamService.getGameInPeriod(teamId, dayOffset);
    }
}