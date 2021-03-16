package com.mafia.meatball.controller;

import com.mafia.meatball.entity.Game;
import com.mafia.meatball.response.OptimalFreeBetResponse;
import com.mafia.meatball.service.OddsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BetControllerTest {
    BetController betController;

    @Mock
    OddsService oddsService;

    @BeforeEach
    void setUp() {
        betController = new BetController(oddsService);
    }

    @Test
    void getOptimalFreeBet() {
        String freeBetSite = "William Mitz";
        String apiKey = "test";

        List<Game> games = generateGames(freeBetSite);

        when(oddsService.getAllGameOdds(apiKey, freeBetSite))
                .thenReturn(games);

        OptimalFreeBetResponse expected = OptimalFreeBetResponse.builder()
                .difference(-10)
                .freeBetSite(freeBetSite)
                .freeBetOdds(400)
                .oppositeSite("Meatball Kings")
                .oppositeSiteOdds(-410)
                .sport("BaseketBall")
                .teams(Arrays.asList("Donkeyballs", "Firemen"))
                .build();

        ResponseEntity<OptimalFreeBetResponse> actual = betController.getOptimalFreeBet(freeBetSite, apiKey);

        assertEquals(actual.getBody().getDifference(), expected.getDifference());
        assertEquals(actual.getBody().getFreeBetOdds(), expected.getFreeBetOdds());
        assertEquals(actual.getBody().getFreeBetSite(), expected.getFreeBetSite());
        assertEquals(actual.getBody().getOppositeSite(), expected.getOppositeSite());
        assertEquals(actual.getBody().getOppositeSiteOdds(), expected.getOppositeSiteOdds());
        assertEquals(actual.getBody().getSport(), expected.getSport());
        assertEquals(actual.getBody().getTeams(), expected.getTeams());
    }

    private List<Game> generateGames(String freeBetSite){
        HashMap<String, Integer> game1siteOdds = new HashMap<>();
        HashMap<String, Integer> game2siteOdds = new HashMap<>();
        HashMap<String, Integer> game3siteOdds = new HashMap<>();

        game1siteOdds.put(freeBetSite, 220);
        game1siteOdds.put("Meatball Kings", -290);
        game1siteOdds.put("Teaser Tim", -300);
        game1siteOdds.put("Big Game Greg", -265);

        Game game1 = Game.builder()
                .sport("Basketball")
                .teams(Arrays.asList("Knicks", "Spurs"))
                .siteOdds(game1siteOdds)
                .build();

        game2siteOdds.put(freeBetSite, 400);
        game2siteOdds.put("Meatball Kings", -410);
        game2siteOdds.put("Teaser Tim", -510);
        game2siteOdds.put("Big Game Greg", -500);
        game2siteOdds.put("Sharp Schank", -500);

        Game game2 = Game.builder()
                .sport("BaseketBall")
                .teams(Arrays.asList("Donkeyballs", "Firemen"))
                .siteOdds(game2siteOdds)
                .build();

        game3siteOdds.put(freeBetSite, 1400);
        game3siteOdds.put("Meatball Kings", -2000);
        game3siteOdds.put("Teaser Tim", -2500);
        game3siteOdds.put("Big Game Greg", -2100);
        game3siteOdds.put("Sharp Schank", -3000);

        Game game3 = Game.builder()
                .sport("Running")
                .teams(Arrays.asList("USA", "Germnay"))
                .siteOdds(game3siteOdds)
                .build();

        return Arrays.asList(game1, game2, game3);
    }
}