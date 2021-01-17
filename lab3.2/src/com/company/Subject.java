package com.company;

import java.util.Objects;

public abstract class Subject implements ExtendMath, Moving {
    private String Name;
    private Position Pos;


    @Override
    public void setCurrentPosition(Position pos) {
        Pos = pos;
    }
    @Override
    public Position getCurrentPosition() {
        return Pos;
    }
    static short WaveLevel = 0;
    static int getWaveLevel() {
        return WaveLevel;
    }
    static void setWaveLevel(int size) {
        WaveLevel = (short) ExtendMath.clamp(size,0,100);
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    @Override
    public String Move(Action move) {
        if (move==Action.BLOWNAWAY||move==Action.BREAKTHROUGH) {
            setCurrentPosition(Position.NONAVAILABLE);
        }
        return this.getName()+move.getDes();
    }
    @Override
    public String Move(Action move, Adverb adverb){
        if (move==Action.BLOWNAWAY||move==Action.BREAKTHROUGH) {
            setCurrentPosition(Position.NONAVAILABLE);
        }
        return this.getName()+move.getDes()+adverb.getDes();
    }
    @Override
    public String Move(Action move, Position position){
        if (move==Action.BLOWNAWAY||move==Action.BREAKTHROUGH) {
            setCurrentPosition(Position.NONAVAILABLE);
        } else {setCurrentPosition(position);}
        return this.getName()+move.getDes()+position.getDes();
    }
    @Override
    public String Move(Action move, Position position, Adverb adverb){
        if (move==Action.BLOWNAWAY||move==Action.BREAKTHROUGH) {
            setCurrentPosition(Position.NONAVAILABLE);
        } else {setCurrentPosition(position);}
        return this.getName()+move.getDes()+position.getDes()+adverb.getDes();
    }
    @Override
    public String Move(Action move, Subject subject) {
        if (move==Action.BLOWNAWAY||move==Action.BREAKTHROUGH) {
            setCurrentPosition(Position.NONAVAILABLE);
        }
        return this.getName()+move.getDes()+" to "+subject.getName();
    }
    @Override
    public String toString() {
        return "Name: " + this.getName();
    }
    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (!(o instanceof Subject)) return false;
        Subject human = (Subject) o;
        return Objects.equals(this.getName(), human.getName());
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.getName());
    }
}
