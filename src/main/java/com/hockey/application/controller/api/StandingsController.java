package com.hockey.application.controller.api;

import com.hockey.core.interfaces.services.StandingService;
import com.hockey.services.StandingServiceImpl;
import com.hockey.infrastructure.dto.StandingDTO;
import com.hockey.infrastructure.repositories.StandingRepositoryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
public class StandingsController {

    private StandingService service;

    public StandingsController() throws SQLException {
        this.service = new StandingServiceImpl(new StandingRepositoryImpl());
    }

//    @CrossOrigin(origins = "http://localhost:8081")
//    @GetMapping("/standings")
//    public ModelAndView index() {
//        ModelAndView modelAndView = new ModelAndView("standings/index");
//        List<StandingDTO> standings = service.getStanding(1L);
//        modelAndView.addObject("standings", standings);
//        return modelAndView;
//    }

    //    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping({"/standings/{seasonId}", "/standings"})
    public ModelAndView index(@PathVariable(name = "seasonId", required = false) String seasonId) {
        ModelAndView modelAndView = new ModelAndView("standings/index");
        long seasonId1 = StringUtils.isEmpty(seasonId) ? 1L : Long.valueOf(seasonId);
        List<StandingDTO> standings = service.getStanding(seasonId1);
        modelAndView.addObject("standings", standings);
        return modelAndView;
    }
}
