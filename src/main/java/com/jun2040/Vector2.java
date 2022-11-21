package com.jun2040;

class Vector2 {
    int x, y;

    // Unit vectors
    public static final Vector2 LEFT = new Vector2(1, 0);
    public static final Vector2 RIGHT = new Vector2(-1, 0);
    public static final Vector2 UP = new Vector2(0, 1);
    public static final Vector2 DOWN = new Vector2(0, -1);

    // Initialize vector
    public Vector2(int x1, int y1) {
        x = x1;
        y = y1;
    }

    // Generate unique hashcode to check for equality
    @Override
    public int hashCode() {
        return x * 31 + y;
    }

    // Used to check if two vectors are equal
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Vector2 vector2 = (Vector2) o;
        return vector2.x == x && vector2.y == y;
    }

    // Convert vector to string: useful for debugging
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
