package com.mafia.meatball.entity;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class OddsResponse implements Serializable {
    Boolean success;
    List<GameResponse> data;
}
