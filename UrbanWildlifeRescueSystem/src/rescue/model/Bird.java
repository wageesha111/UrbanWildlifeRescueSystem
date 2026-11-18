package rescue.model;

public class Bird extends Animal {
    public Bird(Condition condition, int count) {
        super("bird", condition, count);
    }

    @Override
    public String getHandlingNote() {
        return "Use a towel and a ventilated box. Keep the area quiet.";
    }
}
