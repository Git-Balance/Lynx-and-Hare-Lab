public class Organism {
    private boolean alive;
    private int age;
    
    public Organism() {
        alive = true;
        age = 0;
    }
    
    public void die() { alive = false; }
    public boolean getAlive() { return alive; }
    
    public void age() { age++; }
    public void age(int newAge) { age += newAge; }
    public int getAge() { return age; }
    
    public void reset() {
        ; // nothing is needed for superclass
    }
    
    public String toString() {
        return "Organism(" + getAge() + "," + getAlive() + ")";
    }
}