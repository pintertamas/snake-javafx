public class DrawableUnit {
    private int posX;
    private int posY;
    private final int size;

    public DrawableUnit(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        size = 40;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getSize() {
        return size;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
