import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class task3 {
    // Входящий параметр - путь к папке с файлами данных следует указывать без слэша на конце
    public static void main(String[] args) throws IOException {
        float dataSumm;
        float dataMax = 0.0f;
        int indexMax = 0;
        String[] fileNames = new String[5];
        BufferedReader[] bufferedReader = new BufferedReader[5];

        for (int i = 0; i < 5; i++) {
            fileNames[i] = args[0].concat("\\Cash" + (i + 1) + ".txt");
            bufferedReader[i] = new BufferedReader(new FileReader(new File(fileNames[i])));
        }

        for (int i = 0; i < 16; i++) {
            dataSumm = 0.0f;
            for (int j = 0; j < 5; j++) {
                dataSumm += Float.parseFloat(bufferedReader[j].readLine());
            }
            if (dataMax < dataSumm) {
                dataMax = dataSumm;
                indexMax = i;
            }
        }
        System.out.println(++indexMax);
    }
}
