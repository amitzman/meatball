package com.mafia.meatball.entity;

import lombok.Getter;

import java.io.Serializable;
@Getter
public class Site implements Serializable {
    String site_nice;
    Odds odds;
}
