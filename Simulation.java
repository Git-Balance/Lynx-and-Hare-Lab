import java.util.ArrayList;
import java.util.Scanner;

public class Simulation {

    private final int HARE_RESET_SIZE = 3;
    private final int LYNX_RESET_SIZE = 1;

    private ArrayList<Hare> hares = new ArrayList<Hare>();
    private ArrayList<Lynx> lynxes = new ArrayList<Lynx>();
    private ArrayList<String> events = new ArrayList<String>();
    private int generations = 0;

    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        Scanner in = new Scanner(System.in);

        final String line = "-------------------------";
        final String instructions = "Enter what you want to do (n,d,t,q)\n> "; 

        simulation.displayInfo();
        System.out.println(line);
        System.out.print(instructions);
        String input = in.next().toLowerCase();
        if (!(input.equals("n"))) {
            System.out.println(line);
        }
        while (!(input.equals("q"))) {
            if (input.equals("n")) {
                simulation.nextStep();
            }
            else if (input.equals("d")) {
                simulation.displayDetails();
            }
            else if (input.equals("t")) {
                try
                {
                    simulation.tests();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            System.out.println(line);
            simulation.displayInfo();
            System.out.println(line);
            System.out.print(instructions);
            input = in.next().toLowerCase();
            if (!(input.equals("n"))) {
                System.out.println(line);
            }
        }

    }

    public Simulation() {
        hares.add(new Hare());
        hares.add(new Hare());
        hares.add(new Hare());
        lynxes.add(new Lynx());
    }

    public void nextStep() {
        // Prepare for the next generation
        generations++;
        events.clear();
        for (Hare hare : hares) {
            hare.age();
        }
        for (Lynx lynx : lynxes) {
            lynx.age();
        }
        if (hares.size() == 0) {
            for (int x = 0; x < HARE_RESET_SIZE; x++) {
                hares.add(new Hare());
            }
            events.add("Hare population reset to 3 due to extinction");
        }
        if (lynxes.size() == 0) {
            for (int x = 0; x < LYNX_RESET_SIZE; x++){
                lynxes.add(new Lynx());
            }
            events.add("Lynx population reset to 1 due to extinction");
        }

        // Make organisms make checks to see if they live or die
        for (Lynx lynx : lynxes) {
            lynx.eatPrey(hares);
        }

        // Remove dead organisms
        // TODO:    The current fixes aren't working, figure it out next session
        // NOTE1:   This current fix is working, but I HAVE NOT IDEA WHY
        //          I commented out the first 2 hareIndex++ lines of code, and that worked?
        //          Figure out why it worked, make the code look better, then copy it for the lynxes
        // NOTE2:   "If (hares.contains(hareIndex + 1) == false), then hareIndex++" was causing the bug
        //          It IS necessary is some form
        //          However, skipping the next hare when the condition returns true causes problems
        //          It should be reworked to:
        //              else if (hares.contains(hareIndex + 1) == true) {
        //                  hareIndex++;
        //          Then remove the final alone "else { ... }" statement
        // NOTE3:   Attempted the rework, and it is working
        //          Going to apply the new removal program to lynxes
        
        int hareIndex = 0;
        while (hareIndex < hares.size()) {
            if (hares.get(hareIndex).getAlive() == false) {
                hares.remove(hareIndex);
                if (hares.contains(hareIndex) && hares.get(hareIndex).getAlive() == true) {
                    hareIndex++;
                }
                else if (hares.contains(hareIndex + 1) == true) {
                    hareIndex++;
                }
            }
            else {
                hareIndex++;
            }
        }
        int lynxIndex = 0;
        while (lynxIndex < lynxes.size()) {
            if (lynxes.get(lynxIndex).getAlive() == false) {
                lynxes.remove(lynxIndex);
                if (lynxes.contains(lynxIndex) && lynxes.get(lynxIndex).getAlive() == true) { 
                    lynxIndex++; 
                    ;
                }
                else if (lynxes.contains(lynxIndex + 1) == true) {
                    lynxIndex++;
                }
            }
            else {
                lynxIndex++;
            }
        }

        // Make organisms reproduce
        int oldHaresSize = hares.size();
        for (int x = 0; x < oldHaresSize; x++){
            hares.add(new Hare());
        }
        int newLynxes = 0;
        for (Lynx lynx : lynxes) {
            if (lynx.getReproduce() == true) {
                newLynxes++;
                lynx.reset();
            }
        }
        for (int x = 0; x < newLynxes; x++) {
            lynxes.add(new Lynx());
        }
    }

    public void displayInfo() {
        System.out.println("Generation " + generations + " (" + events.size() + " events)");
        System.out.println(hares.size() + " Hares | " + lynxes.size() + " Lynxes");
    }

    public void displayDetails() {
        System.out.println("Generation: " + generations);
        System.out.println("Events:");
        for (String event : events) {
            System.out.println("| " + event);
        }
        System.out.println("Organisms (age, alive, reproduce):");
        for (Hare hare : hares) {
            System.out.println("| " + hare);
        }
        for (Lynx lynx: lynxes) {
            System.out.println("| " + lynx);
        }
    }

    public void tests() throws Exception {
        Organism testO = new Organism();
        System.out.println(testO);
        testO.age();
        testO.age(3);
        testO.die();
        System.out.println(testO);

        Hare testH = new Hare();
        testH.age();
        System.out.println(testH);

        Lynx testL = new Lynx();
        ArrayList<Hare> testPreys = new ArrayList<Hare>();
        testPreys.add(new Hare());
        testPreys.add(new Hare());
        testPreys.add(new Hare());
        testPreys.add(new Hare());
        testPreys.add(new Hare());
        testPreys.add(new Hare());
        testPreys.add(new Hare());
        testPreys.add(new Hare());
        testPreys.add(new Hare());
        testPreys.add(new Hare());
        testL.eatPrey(testPreys);
        testL.age();
        System.out.println(testL);
        int alive = 0;
        for (Hare prey : testPreys) { if (prey.getAlive()) { alive++; }}
        System.out.println(alive);

        Simulation testS = new Simulation();
        testS.nextStep();
        testS.nextStep();
        testS.nextStep();
        testS.displayInfo();
        testS.displayDetails();
    }
}