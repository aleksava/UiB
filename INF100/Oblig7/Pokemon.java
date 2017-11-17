import java.util.Random;

class Pokemon {
    private String name;
    private Random random;
    private int maxHealthPoints;
    private double criticalChance;
    private int strength;
    private int healthPoints;

    // Constructor with pseudo-random stats generated
    public Pokemon(String name) {
        this.name = name;
        this.random = new Random();
        this.criticalChance = Math.abs(0.1 * random.nextGaussian());
        this.strength =
            (int) Math.abs(Math.round(20 + 10 * random.nextGaussian()));
        this.healthPoints =
            (int) Math.abs(Math.round(100 + 10 * random.nextGaussian()));
        this.maxHealthPoints = this.healthPoints;
    }

    // Returns name of given pokemon
    public String getName() {
        return name;
    }

    // Returns true if the Pokemon has more HP than 0, otherwise false
    public boolean isConscious() {
        return healthPoints > 0;
    }

    // Inflicts damage on given Pokemon, and health cannot be < 0
    public void damage(int damageTaken) {
        healthPoints -= damageTaken;

        if(!this.isConscious())
            healthPoints = 0;

        System.out.println(name + " takes " + damageTaken +
                           " and is left with " + healthPoints +
                           "/" + maxHealthPoints + " HP");
    }

    // Current Pokemon attacks given Pokemon, can inflict a critical hit
    // notified if it happens
    public void attack(Pokemon target) {
        int damageInflicted =
            (int) (this.strength + this.strength / 2 * random.nextGaussian());

        if(damageInflicted < 0)
            damageInflicted = 0;

        System.out.printf("%s attacks %s.%n", this.getName(), target.getName());

        target.damage(damageInflicted);

        // Critical hit! The Pokemon strikes again.
        // Also, it's dumb to critical hit with a 0
        if((Math.random() < this.criticalChance) && damageInflicted != 0) {
            System.out.println("Critical hit!");
            
            // Attacks again with same damage if it was a critical hit
            System.out.printf("%s attacks %s.%n",
                              this.getName(),
                              target.getName());
            
            target.damage(damageInflicted);
        }

        if(!target.isConscious()) {
            System.out.println(target.getName() + " was defeated by " +
                               this.getName());
        }

        // Formatting in output for readability
        System.out.println();
    }

    // Returns name and stats for the Pokemon
    @Override
    public String toString() {
        double crit = criticalChance*100;
        
        return (name + " HP: " + healthPoints + "/" + maxHealthPoints +
                " STR: " + strength +
                " CHC: " + (int) crit + "%");
    }
}
