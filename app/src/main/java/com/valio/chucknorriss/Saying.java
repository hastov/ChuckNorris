package com.valio.chucknorriss;

enum Mood {
    SERIOUS,
    FUNNY
}

public class Saying {
    String text;
    Mood mood = Mood.SERIOUS;
}
