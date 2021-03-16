package com.mafia.meatball.controller;

import com.mafia.meatball.entity.Game;
import com.mafia.meatball.response.OptimalFreeBetResponse;
import com.mafia.meatball.service.OddsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.Map;

@RestController
@EnableWebMvc
public class BetController {
    private OddsService oddsService;

    public BetController(OddsService oddsService) {
        this.oddsService = oddsService;
    }

    @GetMapping(value = "/freebet", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OptimalFreeBetResponse> getOptimalFreeBet(@RequestParam(value = "book") String book,
                                            @RequestHeader(value = "apiKey") String apiKey) {
        List<Game> allGameOdds = oddsService.getAllGameOdds(apiKey, book);

        OptimalFreeBetResponse optimalFreeBet = new OptimalFreeBetResponse();
        optimalFreeBet.setDifference(500);
        optimalFreeBet.setFreeBetSite(book);

        for (Game game : allGameOdds) {
            Integer underdogOdds = game.getSiteOdds().get(book);
            for (Map.Entry<String, Integer> entry : game.getSiteOdds().entrySet()) {
                Integer currentBookOdds = entry.getValue();
                int difference = underdogOdds + currentBookOdds;
                int currentOptimalFreeBetDifference = optimalFreeBet.getDifference()
                if (Math.abs(difference) < Math.abs(currentOptimalFreeBetDifference)) {
                    optimalFreeBet.setDifference(difference);
                    optimalFreeBet.setOppositeSite(entry.getKey());
                    optimalFreeBet.setOppositeSiteOdds(currentBookOdds);
                    optimalFreeBet.setSport(game.getSport());
                    optimalFreeBet.setTeams(game.getTeams());
                    optimalFreeBet.setFreeBetOdds(underdogOdds);
                }
            }
        }
        return ResponseEntity.status(200).body(
                optimalFreeBet
        );
    }
}
