package com.mafia.meatball.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;

@Builder
@Getter
public class Game {
    String sport;
    List<String> teams;
    HashMap<String, Integer> siteOdds;
}
