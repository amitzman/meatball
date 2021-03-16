package com.mafia.meatball.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class OptimalFreeBetResponse {
    Integer difference;
    String freeBetSite;
    Integer freeBetOdds;
    String oppositeSite;
    Integer oppositeSiteOdds;
    String sport;
    List<String> teams;
}