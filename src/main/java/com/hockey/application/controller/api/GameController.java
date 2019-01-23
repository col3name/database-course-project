package com.hockey.application.controller.api;

import com.hockey.core.entity.league.GameType;
import com.hockey.core.interfaces.services.GameService;
import com.hockey.infrastructure.dto.GameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class GameController {
    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("games/{gameId}")
    public ModelAndView index(@PathVariable(name = "gameId") Long gameId) {

        ModelAndView modelAndView = new ModelAndView("games/index");
        Map<GameType, List<GameDTO>> gamesMap = gameService.getGamesBeforeGameStartGroupByTeam(gameId);
        System.out.println(gamesMap);
        addGameObject(gameId, modelAndView);
        modelAndView.addObject("homeTeamGames", getGames(gamesMap, GameType.HOME));
        modelAndView.addObject("guestTeamGames", getGames(gamesMap, GameType.GUEST));

        return modelAndView;
    }

    @GetMapping("games/{gameId}/stats")
    public ModelAndView stats(@PathVariable(name = "gameId") Long gameId) {

        ModelAndView modelAndView = new ModelAndView("games/index");
//        Map<GameType, List<GameDTO>> gamesMap = gameService.getRosterByTeam(gameId);
//        System.out.println(gamesMap);
        addGameObject(gameId, modelAndView);
//        modelAndView.addObject("statistic", getGames(gamesMap, GameType.HOME));
//        modelAndView.addObject("guestTeamGames", getGames(gamesMap, GameType.GUEST));

        return modelAndView;
    }

    private void addGameObject(Long gameId, ModelAndView modelAndView) {
        try {
            GameDTO game = gameService.getById(gameId);
            modelAndView.addObject("gameDTO", game);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("games/{gameId}/protocol")
    public ModelAndView preview(@PathVariable(name = "gameId") Long gameId) {

        ModelAndView modelAndView = new ModelAndView("games/protocol");

        try {
            modelAndView.addObject("game", gameService.getById(gameId));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return modelAndView;
    }

    private List<GameDTO> getGames(Map<GameType, List<GameDTO>> gamesMap, GameType guest) {
        if (gamesMap.containsKey(guest)) {
            return gamesMap.get(guest);
        }
        return Collections.emptyList();
    }
}
