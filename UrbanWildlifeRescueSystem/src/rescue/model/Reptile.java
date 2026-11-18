package rescue.model;

public class Reptile extends Animal {
    public Reptile(Condition condition, int count) {
        super("reptile", condition, count);
    }

    @Override
    public String getHandlingNote() {
        return "Use gloves or a hook. Keep the animal warm and avoid direct handling.";
    }
}
