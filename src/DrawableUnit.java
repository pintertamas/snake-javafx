/**
 * This class represents a drawable unit.
 * It has x, y position and a size.
 */
public class DrawableUnit {
    private int posX;
    private int posY;
    private final int size;

    /**
     * Constructor that sets up the DrawableUnit's position and size.
     *
     * @param posX x position
     * @param posY y position
     */
    public DrawableUnit(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        size = 40;
    }

    /**
     * X position getter
     *
     * @return posX
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Y position getter
     *
     * @return posY
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Size getter
     *
     * @return size
     */
    public int getSize() {
        return size;
    }

    /**
     * X position setter
     *
     * @param posX sets posX to this value
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Y position setter
     *
     * @param posY sets posY to this value
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }
}
