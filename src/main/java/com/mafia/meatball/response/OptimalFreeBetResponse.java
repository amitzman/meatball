package com.mafia.meatball.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptimalFreeBetResponse {
    Integer difference;
    String freeBetSite;
    Integer freeBetOdds;
    String oppositeSite;
    Integer oppositeSiteOdds;
    String sport;
    List<String> teams;

    public OptimalFreeBetResponse(String freeBetSite) {
        this.freeBetSite = freeBetSite;
        this.difference = -1000;
    }
}
