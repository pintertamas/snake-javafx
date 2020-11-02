import javafx.scene.canvas.GraphicsContext;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

public class Leaderboard {

    private final ArrayList<Integer> points;
    private GraphicsContext gc;
    private Object ScoreComparator;

    public Leaderboard(GraphicsContext gc) {
        points = new ArrayList<>();
        this.gc = gc;
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

    public void saveLeaderboard(boolean saved, ArrayList<Integer> points) {
        if (!saved) {
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
    }

    public void initLeaderboard() {
        for (int i = 0; i < 10; i++)
            points.add(0);
    }

    public void show() {
        //saveLeaderboard(points);
    }

    public void updateScores(ArrayList<Integer> topScores, int score) {
        //topScores.sort(Integer::compareTo);
        int smallest = 100000;
        int smallestIndex = -1;
        for (int i = 0; i < topScores.size(); i++)
            if (smallest > topScores.get(i)) {
                smallest = topScores.get(i);
                smallestIndex = i;
            }
        //if (smallest < score && smallestIndex != -1)
        //topScores.get(smallestIndex) = score;
    }

    public ArrayList<Integer> getPoints() {
        return points;
    }


}

