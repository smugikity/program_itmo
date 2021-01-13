public class Main {
    public static void main(String[] args) {
        Thing Box = new Thing("The thread box");
        Box.Move(Moves.FLOAT);
        Box.Move(Moves.CARRIED,Positions.BAY);
        Thing House = new Thing("The house");
        House.Move(Moves.LOCATED,Positions.BAY);
        Human Mu = new Human("Mu");
        Thing Fishhook = new Thing("The Fishhook");
        Mu.Wakeup(Math.random()<0.5,Fishhook.MoveAsString(Moves.HOVER,Adverbs.GIRL));
        Fishhook.Move(Moves.CLUNG,Positions.BOX);
        Box.Move(Moves.FLOAT,Adverbs.CAREFULLY);
        Human Moonmin = new Human("Moomin's family",Positions.BAY);
        Human Snusmumrik = new Human("Snusmumrik",Positions.BAY);
        int numberofhumans = (int)(Math.round(Math.random()*10)+5);
        Human[] humans = new Human[numberofhumans];
        humans[0] = Moonmin; humans[1]=Snusmumrik;
        for (int i=2;i<numberofhumans;i++){
            humans[i] = new Human();
        }
        humans[0].synchronizedmove();
        Snusmumrik.Move(Moves.STAND,Positions.BANK,Adverbs.GREENHAT);
        Snusmumrik.Move(Moves.STARE,Positions.BOX);
    }
}
