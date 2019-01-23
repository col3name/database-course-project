package com.hockey.application.controller.api;

import com.hockey.core.entity.player.Player;
import com.hockey.core.exception.InvalidEntityException;
import com.hockey.core.interfaces.services.PlayerService;
import com.hockey.infrastructure.dto.PlayerBaseDTO;
import com.hockey.infrastructure.dto.PlayerDetailDTO;
import com.hockey.infrastructure.dto.PlayerTeamStandingsDto;
import com.hockey.infrastructure.repositories.PlayerRepositoryImpl;
import com.hockey.services.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.hockey.application.config.Constants.COUNT_PLAYER_ON_PAGE;
import static com.hockey.application.config.Constants.START_PAGE;

@Controller
public class PlayerController {

    public static final String VIEWS_PLAYER_CREATE_OR_UPDATE_FORM = "players/new-or-update-form";
    private final PlayerService service;

    @Autowired
    public PlayerController(PlayerService service) throws SQLException {
        this.service = new PlayerServiceImpl(new PlayerRepositoryImpl());
        System.out.println(service);
    }

    @GetMapping("/players")
    public ModelAndView index(@RequestParam(name = "page", required = false, defaultValue = START_PAGE) Integer page,
                              @RequestParam(name = "size", required = false, defaultValue = COUNT_PLAYER_ON_PAGE) Integer size) {
        System.out.println("page: " + page);
        System.out.println("size: " + size);

        List<PlayerBaseDTO> paginate = service.paginate(page, size);
        System.out.println(new Date() + " " + paginate.size());
        paginate.forEach(player -> System.out.println(player.getFirstName()));

        ModelAndView modelAndView = new ModelAndView("players/index");
        modelAndView.addObject("paginate", service.paginate(page, size));

        return modelAndView;
    }

    @GetMapping("/players/{id}")
    public ModelAndView show(@PathVariable String id) {
        Long playerId = Long.parseLong(id);
        ModelAndView modelAndView = new ModelAndView("players/detail");
        try {
            PlayerDetailDTO playerInfo = service.getDetailPlayerInfo(playerId);
            System.out.println(playerInfo);
            modelAndView.addObject("playerInfo", playerInfo);
            List<PlayerTeamStandingsDto> attributeValue = service.playerTeamStandings(playerId);
            System.out.println(attributeValue.toString());
            modelAndView.addObject("teamStandings", attributeValue);
        } catch (InvalidEntityException e) {
            e.printStackTrace();
        }

        return modelAndView;
    }

    @GetMapping("/players/{id}/edit")
    public ModelAndView initUpdateOwnerForm(@PathVariable("id") final String id) {
        Player player = null;
        try {
            long id1 = Long.parseLong(id);
            player = this.service.findById(id1);
            player.setId(id1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ModelAndView modelAndView = new ModelAndView("players/newOrUpdateForm");
        modelAndView.addObject("player", player);
        modelAndView.addObject("action", "edit");

        return modelAndView;
    }

    @PostMapping("/players")
    public Player create(@RequestParam Map<String, String> body) throws SQLException {

        return service.createPlayer(body);
    }

    @PostMapping("/players/{id}/edit")
    public ModelAndView processUpdateOwnerForm(@Valid Player player, BindingResult result, @PathVariable("id") String id) {
        if (result.hasErrors()) {
            System.out.println(result);
            return new ModelAndView(VIEWS_PLAYER_CREATE_OR_UPDATE_FORM);
        } else {
            player.setId(Long.valueOf(id));
            service.save(player);
            return new ModelAndView("redirect:/players/{id}");
        }
    }

    @PutMapping("/players/{id}")
    public boolean updatePlayer(@PathVariable String id, @RequestParam Map<String, String> body) {
        Long playerID = Long.parseLong(id);

        return service.updatePlayer(playerID, body);
    }

    @DeleteMapping("/players/{id}")
    public boolean deletePlayer(@PathVariable String id) {
        Long blogId = Long.parseLong(id);
        return service.deletePlayer(blogId);
    }
}
