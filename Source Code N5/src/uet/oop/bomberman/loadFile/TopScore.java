package uet.oop.bomberman.loadFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TopScore {
    private static List<String> scoreList = new ArrayList<String>();

    public static void getList() {
        try {
            FileReader input = new FileReader("res\\scores\\score.txt");
            BufferedReader reader = new BufferedReader(input);
            String line = reader.readLine();
            while (line != null) {
                scoreList.add(line);
                line = reader.readLine();
            }

            input.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            //scoreList.add("File dẫn không hợp lệ!");
        }
    }

    public static List<String> topScore(List<String> list) {
         Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (Long.parseLong(o1) > Long.parseLong(o2))
                    return -1;
                return 1;
            }
        });
         return list;
    }

    public static List<String> printList() {
        scoreList.clear();
        getList();
        List<String> res = topScore(scoreList);
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if ( i < res.size()) {
                //System.out.println(res.get(i));
                ans.add(res.get(i));
            } else {
                //System.out.println("N/A");
                ans.add("N/A");
            }
        }
        return ans;
    }

    public static void addScore(int score) {
        String newScore = Integer.toString(score);
        scoreList.add(newScore);
    }

    public static void outScore() {
        try {
            FileOutputStream fileOut = new FileOutputStream("res\\scores\\score.txt"); //Outfile
            for (int i = 0; i < scoreList.size(); i++) {
                String aScore = scoreList.get(i) + "\n";
                byte out[] = aScore.getBytes();
                fileOut.write(out);
            }
            fileOut.close();
        } catch (IOException e) {
            System.out.println("An error occurred." + e);
        }

    }
}
