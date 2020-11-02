import javafx.scene.canvas.GraphicsContext;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

public class Leaderboard {
    private final ArrayList<Integer> points;
    private GraphicsContext gc;

    public Leaderboard(GraphicsContext gc) {
        points = new ArrayList<>();
        this.gc = gc;
    }

    public void initFakeLeaderboard() {
        for (int i = 0; i < 10; i++)
            points.add(i);
    }

    public void loadLeaderboard() {
        try {
            File file = new File("leaderboard.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                int data = scanner.nextInt();
                points.add(data);
                System.out.println(data);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void saveLeaderboard(ArrayList<Integer> points) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("leaderboard.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (Integer point : points)
                out.writeObject(point);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in leaderboard.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void show() throws IOException {
        initFakeLeaderboard();
        saveLeaderboard(points);
    }


}

