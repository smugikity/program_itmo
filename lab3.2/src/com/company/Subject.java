package com.company;

public abstract class Subject implements ExtendMath, Move {
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

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
}
