public interface Actions {
    void Move(Moves move);
    void Move(Moves move, Positions position);
    void Move(Moves move, Adverbs adverb);
    void Move(Moves move, Positions position, Adverbs adverb);
}
