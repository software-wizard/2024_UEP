package pl.psi;

import lombok.Value;

@Value
public class Location extends Point {
    private final BoardIf board;

    public Location(final int aX, final int aY, final BoardIf board) {
        super(aX, aY);
        this.board = board;

        if (!isValidPoint()) throw new IllegalArgumentException("location invalid: point is not a valid point on the board");
    }

    public Location(final Point p, final BoardIf board) {
        super(p.getX(), p.getY());
        this.board = board;

        if (!isValidPoint()) throw new IllegalArgumentException("location invalid: point is not a valid point on the board");
    }

    private boolean isValidPoint() {
        return this.board.isValidPoint(this);
    }

}
