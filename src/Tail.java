public class Tail extends Point {
    private int size;

    public Tail(int posX, int posY, int sizeX, int sizeY) {
        super(posX, posY);
        this.size = sizeX;
        this.size = sizeY;
    }

    public int getSizeX() {
        return size;
    }

    public int getSizeY() {
        return size;
    }

    public void setSizeX(int sizeX) {
        this.size = sizeX;
    }

    public void setSizeY(int sizeY) {
        this.size = sizeY;
    }
}
