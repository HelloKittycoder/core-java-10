package set;

import bytecodeAnnotations.LogEntry;

import java.util.Objects;

/**
 * An item with a description and a part number
 * Created by shucheng on 2020/10/1 22:42
 */
public class Item {
    private String description;
    private int partNumber;

    /**
     * Constructs an item.
     * @param description aDescription the item's description
     * @param partNumber aPartNumber the item's part number
     */
    public Item(String description, int partNumber) {
        this.description = description;
        this.partNumber = partNumber;
    }

    /**
     * Gets the description of this item.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "{description='" + description + '\'' + ", partNumber=" + partNumber + '}';
    }

    @LogEntry(logger = "com.kittycoder")
    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        Item other = (Item) otherObject;
        return Objects.equals(description, other.description) && partNumber == other.partNumber;
    }

    @LogEntry(logger = "com.kittycoder")
    @Override
    public int hashCode() {
        return Objects.hash(description, partNumber);
    }
}
