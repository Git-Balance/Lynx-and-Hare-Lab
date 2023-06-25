import java.util.ArrayList;
import java.util.Random;

public class Lynx extends Organism {
    private boolean canReproduce;

    public Lynx() {
        canReproduce = false;
    }

    public void changeReproduce(boolean newState) { canReproduce = newState; };

    public boolean getReproduce() { return canReproduce; }

    public void eatPrey(ArrayList<Hare> preys) {
        int eaten = 0;
        Random rand = new Random();

        for (Hare prey : preys) {
            if (prey.getAlive() == true) {
                int chanceEaten = rand.nextInt(10);
                if (chanceEaten <= 1) {
                    prey.die();
                    eaten++;
                }
            }
        }

        if (eaten >= 3) {
            changeReproduce(true);
        }
        else if (eaten == 0) {
            die();
        }
    }

    public void reset() {
        changeReproduce(false);
    }

    public String toString() {
        return "Lynx(" + getAge() + "," + getAlive() + "," + getReproduce() + ")";
    }
}