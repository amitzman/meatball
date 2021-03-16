package com.mafia.meatball.entity;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Sport implements Serializable {
    String key;
    Boolean active;
    String group;
    String details;
    String title;
}
