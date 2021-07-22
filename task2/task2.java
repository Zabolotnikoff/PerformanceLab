import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class task2 {

    public static void main(String[] args) throws IOException {

        ArrayList<Float[]> vertexArrayList = new ArrayList<>();
        loadPoints(vertexArrayList, args[0]);

        ArrayList<Float[]> pointArrayList = new ArrayList<>();
        loadPoints(pointArrayList, args[1]);

//        printPoints(vertexArrayList);

        for (Float[] point : pointArrayList)
            System.out.println(pointPlace(vertexArrayList, point));

    }

    // Загрузка координат точек из файла
    public static void loadPoints(ArrayList<Float[]> points, String fileName) throws IOException {
        List<String> list = Files.readAllLines(Path.of(fileName));
        String[] pointString;
        Float[] newPoint = new Float[2];
        for (String str : list) {
            pointString = str.split(" ");
            newPoint[0] = Float.parseFloat(pointString[0]);
            newPoint[1] = Float.parseFloat(pointString[1]);
            points.add(newPoint.clone());
        }
    }

    public static void printPoints(ArrayList<Float[]> points) {
        for (Float[] floats : points) System.out.println(floats[0] + " " + floats[1]);
        System.out.println();
    }

    // поворот вектора; если больше нуля левый, если меньше то правый
    public static float rotate(Float[] a, Float[] b, Float[] c) {
        return (b[0] - a[0]) * (c[1] - b[1]) - (b[1] - a[1]) * (c[0] - b[0]);
    }

    // находится ли точка на отрезке
    public static boolean pointOnSegment(Float[] a, Float[] b, Float[] p) {
        if (((p[0] - a[0]) * (b[1] - a[1]) - (p[1] - a[1]) * (b[0] - a[0]) == 0)) {
            return (((p[0] - a[0]) * (p[0] - b[0])) <= 0) && (((p[1] - a[1]) * (p[1] - b[1])) <= 0);
        }
        return false;
    }

    // проверяем все стороны на нахождение точки
    public static boolean touch(Float[] a, Float[] b, Float[] c, Float[] d, Float[] p) {
        return pointOnSegment(a, b, p) || pointOnSegment(b, c, p) || pointOnSegment(c, d, p) || pointOnSegment(d, a, p);
    }

    // проверяем, пересекаются ли отрезки (ab) и (cd)
    public static boolean intersect(Float[] a, Float[] b, Float[] c, Float[] d) {
        return (rotate(a, b, c) * rotate(a, b, d) < 0) && (rotate(c, d, a) * rotate(c, d, b) < 0);
    }

    // сравнение точек по координатам
    public static boolean equal(Float[] a, Float[] b) {
        return (a[0].equals(b[0])) && (a[1].equals(b[1]));
    }

    // финальное определение местоположения точки
    public static int pointPlace(ArrayList<Float[]> vertex, Float[] point) {

        // проверяем вершины
        for (Float[] p : vertex)
            if (equal(p, point)) return 0;

        // проверяем стороны
        if (touch(vertex.get(0), vertex.get(1), vertex.get(2), vertex.get(3), point)) return 1;

        //проверяем сектор за пределами четырёхугольника - слева от первой по порядку грани и справа от последней
        if ((rotate(vertex.get(0), vertex.get(1), point) >= 0) ||
                (rotate(vertex.get(0), vertex.get(3), point) <= 0))
            return 3;

        // проверяем сектор с треугольником слева от диагонали проведённой от первой вершины
        if ((rotate(vertex.get(0), vertex.get(2), point) > 0) &&
                !intersect(vertex.get(0), point, vertex.get(1), vertex.get(2))) return 2;

        // проверяем сектор с треугольником справа от диагонали проведённой от первой вершины, включая саму диагональ
        if ((rotate(vertex.get(0), vertex.get(2), point) <= 0) &&
                !intersect(vertex.get(0), point, vertex.get(2), vertex.get(3))) return 2;

/*        if (((rotate(vertex.get(0), vertex.get(2), point) > 0) &&
                !intersect(vertex.get(0), point, vertex.get(1), vertex.get(2))) ||
        ((rotate(vertex.get(0), vertex.get(2), point) <= 0) &&
                !intersect(vertex.get(0), point, vertex.get(2), vertex.get(3)))) return 2;*/

        return 3;
    }
}
