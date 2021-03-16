package com.mafia.meatball.entity;

import lombok.Getter;

import java.util.List;

@Getter
public class GameResponse {
    List<String> teams;
    List<Site> sites;
}
