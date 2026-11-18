package rescue.model;

import java.util.List;

/** A piece of rescue equipment, e.g. "animal carrier". */
public class Equipment {
    private final String name;

    public Equipment(String name) {
        this.name = name.trim().toLowerCase();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Equipment)) {
            return false;
        }
        return name.equals(((Equipment) other).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    /** Joins equipment names with the given separator ("none" if the list is empty). */
    public static String joinNames(List<Equipment> list, String separator) {
        if (list.isEmpty()) {
            return "none";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                sb.append(separator);
            }
            sb.append(list.get(i).getName());
        }
        return sb.toString();
    }
}
