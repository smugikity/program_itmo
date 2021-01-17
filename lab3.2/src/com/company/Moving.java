package com.company;

public interface Moving {
    String Move(Action move);
    String Move(Action move, Adverb adverb);
    String Move(Action move, Position position);
    String Move(Action move, Subject subject);
    String Move(Action move, Position position, Adverb adverb);
    void setCurrentPosition(Position pos);
    Position getCurrentPosition();
}
