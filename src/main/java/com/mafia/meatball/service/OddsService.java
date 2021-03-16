package com.mafia.meatball.service;

import com.mafia.meatball.entity.Game;
import com.mafia.meatball.entity.OddsResponse;
import com.mafia.meatball.entity.Sport;
import com.mafia.meatball.entity.SportsResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OddsService {
    RestTemplate restTemplate;

    public OddsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Game> getAllGameOdds(String apiKey, String freeBetSite) {
        List<Sport> activeAndNonSoccerSports = getActiveAndNonSoccerSports(apiKey);

        return getGameOddsForSites(apiKey, activeAndNonSoccerSports, freeBetSite);
    }

    private List<Game> getGameOddsForSites(String apiKey,
                                           List<Sport> sports,
                                           String freeBetSite) {
        String url = "https://api.the-odds-api.com/v3/odds";

        List<Game> games = new java.util.ArrayList<>(Collections.emptyList());
        sports.forEach(sport -> {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("apiKey", apiKey)
                    .queryParam("sport", sport.getKey())
                    .queryParam("region", "us")
                    .queryParam("mkt", "h2h")
                    .queryParam("oddsFormat", "american");

            OddsResponse response = restTemplate.getForObject(
                    builder.toUriString(),
                    OddsResponse.class);


            List<String> accepetedSites = Arrays.asList("William Hill (US)", "FanDuel", "DraftKings", "PointsBet (US)", "BetMGM", "BetRivers");
            assert response != null;
            if (response.getData() != null && response.getData().size() != 0) {
                response.getData().forEach(game -> {
                    HashMap<String, Integer> siteOdds = new HashMap<>();

                    game.getSites().forEach(site -> {
                        if (site.getOdds().getH2h().get(0) < 200 || !accepetedSites.contains(site.getSite_nice())) {
                            return;
                        }

                        Integer odds = site.getOdds().getH2h().get(1);

                        if (site.getSite_nice().equals(freeBetSite)) {
                            odds = site.getOdds().getH2h().get(0);
                        }

                        siteOdds.put(site.getSite_nice(), odds);
                    });

                    if(siteOdds.containsKey(freeBetSite)) {
                        games.add(Game.builder()
                                .sport(sport.getTitle())
                                .teams(game.getTeams())
                                .siteOdds(siteOdds)
                                .build());
                    }
                });
            }
        });
        return games;
    }

    private List<Sport> getActiveAndNonSoccerSports(String apiKey) {
        String url = "https://api.the-odds-api.com/v3/sports";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("apiKey", apiKey);

        SportsResponse response = restTemplate.getForObject(
                builder.toUriString(),
                SportsResponse.class);

        return response.getData().stream()
                .filter(sport -> sport.getActive() && !sport.getKey().contains("soccer"))
                .collect(Collectors.toList());

    }
}
