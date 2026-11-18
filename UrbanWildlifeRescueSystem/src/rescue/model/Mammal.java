package rescue.model;

public class Mammal extends Animal {
    public Mammal(Condition condition, int count) {
        super("mammal", condition, count);
    }

    @Override
    public String getHandlingNote() {
        return "Approach slowly. Use a carrier or net and keep the animal calm.";
    }
}
