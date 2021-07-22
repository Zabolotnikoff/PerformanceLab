import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class task1 {
    public static void main(String[] args) throws IOException {

        ArrayList<Short> shortArrayList = new ArrayList<>();
        loadArrayList(args[0], shortArrayList);
        Collections.sort(shortArrayList);

        System.out.printf("%.2f%n", (float) percentile90(shortArrayList));
        System.out.printf("%.2f%n", median(shortArrayList));
        System.out.printf("%.2f%n", (float) Collections.max(shortArrayList));
        System.out.printf("%.2f%n", (float) Collections.min(shortArrayList));
        System.out.printf("%.2f%n", average(shortArrayList));
    }

    // Читает ArrayList<Short> из файла
    public static void loadArrayList(String fileName, ArrayList<Short> shortArrayList) throws IOException {

        List<String> list = Files.readAllLines(Path.of(fileName));
        for (String str : list)
            shortArrayList.add(Short.valueOf(str));
    }

    //  Вычисляет 90 перцентиль в отсортированном по возрастанию ArrayList<Short>
    public static short percentile90(ArrayList<Short> shortArrayListSorted) {
        return shortArrayListSorted.get((int) Math.ceil(90 / 100.0 * shortArrayListSorted.size()) - 1);
    }

    //  Вычисляет медиану в отсортированном по возрастанию ArrayList<Short>
    public static float median(ArrayList<Short> shortArrayListSorted) {
        int leftCenter = (int) Math.ceil(shortArrayListSorted.size() / 2.0) - 1;
        return (float) ((shortArrayListSorted.get(leftCenter) + shortArrayListSorted.get(shortArrayListSorted.size() - leftCenter - 1)) / 2);
    }

    //  Вычисляет среднее значение в ArrayList<Short>
    public static float average(ArrayList<Short> shortArrayList) {
        int summ = 0;
        for (Short item : shortArrayList) summ += item;
        return (float) summ / shortArrayList.size();
    }
}
