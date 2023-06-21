import java.util.ArrayList;

public class Simulation {

    private final int HARE_RESET_SIZE = 3;
    private final int LYNX_RESET_SIZE = 1;

    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        try
        {
            simulation.tests();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    ArrayList<Hare> hares = new ArrayList<Hare>();
    ArrayList<Lynx> lynxes = new ArrayList<Lynx>();
    ArrayList<String> events = new ArrayList<String>();
    int generations = 0;

    public Simulation() {
        hares.add(new Hare());
        hares.add(new Hare());
        hares.add(new Hare());
        lynxes.add(new Lynx());
    }
    
    public void nextStep() {
        generations++;
        events.clear();
        if (hares.size() == 0) {
            for (int x = 0; x < HARE_RESET_SIZE; x++) {
                hares.add(new Hare());
            }
            events.add("Hare population reset to 3");
        }
        if (lynxes.size() == 0) {
            for (int x = 0; x < LYNX_RESET_SIZE; x++){
                lynxes.add(new Lynx());
            }
            events.add("Lynx population reset to 1");
        }
        
        // Make organisms make checks to see if they live or die
        for (Lynx lynx : lynxes) {
            lynx.eatPrey(hares);
        }
        
        // Remove dead organisms
        // TODO: this section's code doesn't work for obvious reasons
        //       (writing to a list that is being read)
        for (int x = 0; x < hares.size(); x++) {
            if (!(hares.get(x).getAlive())) {
                hares.remove(x);
            }
        }
        for (int x = 0; x < lynxes.size(); x++) {
            if (!(lynxes.get(x).getAlive())) {
                lynxes.remove(x);
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
                lynxes.add(new Lynx());
                lynx.reset();
            }
        }
    }
    
    public void displayInfo() {
        System.out.println("Generation " + generations + " (" + events.size() + " event)");
        System.out.println(hares.size() + " Hares | " + lynxes.size() + " Lynxes");
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
    }
}