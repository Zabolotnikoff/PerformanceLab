import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class task4 {

    static int SIZE = (20 - 8) * 60;

    public static void main(String[] args) throws IOException {

        ArrayList<LocalTime[]> timeArrayList = new ArrayList<>();
        loadTimes(timeArrayList, args[0]);

        int[] sessionTracking = new int[SIZE];
        for (int i = 0; i < SIZE; i++) sessionTracking[i] = 0;

        fillingSessions(timeArrayList, sessionTracking);
        maxWorkload (sessionTracking);
    }

    // Загрузка данных из файла о посещении банка
    public static void loadTimes(ArrayList<LocalTime[]> times, String fileName) throws IOException {
        List<String> list = Files.readAllLines(Path.of(fileName));
        String[] timeString;
        String[] timeHoursMinutes;
        LocalTime[] nextClient = new LocalTime[2];
        for (String str : list) {
            timeString = str.split(" ");
            for (int i = 0; i < 2; i++) {
                timeHoursMinutes = timeString[i].split(":");
                nextClient[i] = LocalTime.of(Integer.parseInt(timeHoursMinutes[0]), Integer.parseInt(timeHoursMinutes[1]));
            }
            times.add(nextClient.clone());
        }
    }

    public static void printTimes(ArrayList<LocalTime[]> times) {
        for (LocalTime[] t : times) System.out.println(t[0] + " " + t[1]);
        System.out.println();
    }

    // Конвертирует время через минуты в индекс массива
    public static int timeToIndex(LocalTime time) {
        return (time.getHour() - 8) * 60 + time.getMinute();
    }

    // Конвертирует индекс массива во время.
    public static LocalTime IndexToTime(int index) {
        return LocalTime.of (8 + index / 60, index % 60);
    }

    // Добавляет в массив данные о присутствии пользователей
    public static void fillingSessions(ArrayList<LocalTime[]> times, int[] sessionTracking) {
        for (LocalTime[] t : times) {
            int first = timeToIndex(t[0]);
            int last = timeToIndex(t[1]);
            for (int i = first; i < last; i++)
                sessionTracking[i]++;
        }
    }

    // Выбирает максимальный элемент массива
    public static int getMax(int[] sessionTracking) {
        int max = 0;
        for (int item : sessionTracking) max = Math.max(max, item);
        return max;
    }

    // На основании заполненного массива о посещениях, печатает периоды с максимальным количеством посетителей
    public static void maxWorkload (int[] sessionTracking){
        int max = getMax(sessionTracking);
        for (int i = 0; i < SIZE; i++ ) {
            if (sessionTracking[i] == max) {
                System.out.print(IndexToTime(i));
                while (sessionTracking[i] == max) i++;
                System.out.println(" " + IndexToTime(i));
            }
        }
    }
}
