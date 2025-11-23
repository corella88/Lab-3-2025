package functions;

public class ArrayTabulatedFunction implements TabulatedFunction {
    private FunctionPoint[] points;
    private int pointsCount;

    private static final double EPSILON = 1e-10;

    private boolean equals(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    private boolean lessOrEqual(double a, double b) {
        return a < b || equals(a, b);
    }

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (leftX >= rightX) {
            throw new IllegalArgumentException("Левая граница должна быть меньше правой");
        }

        if (pointsCount < 2) {
            throw new IllegalArgumentException("Количество точек должно быть не менее 2");
        }
        
        this.pointsCount = pointsCount;
        this.points = new FunctionPoint[pointsCount+10];

        double step = (rightX - leftX) / (pointsCount - 1);

        for (int i=0; i<pointsCount; i++) {
            double x = leftX + i*step;
            points[i] = new FunctionPoint(x,0.0);
        }
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) {
        if (leftX >= rightX) {
            throw new IllegalArgumentException("Левая граница должна быть меньше правой");
        }

        if (values.length < 2) {
            throw new IllegalArgumentException("Количество точек должно быть не менее 2");
        }
        
        this.pointsCount = values.length;
        this.points = new FunctionPoint[pointsCount+10];

        double step = (rightX - leftX) / (pointsCount - 1);

        for (int i=0; i<pointsCount; i++) {
            double x = leftX + i*step;
            points[i] = new FunctionPoint(x, values[i]);
        }
    }

    public double getLeftDomainBorder() {
        return points[0].getX();
    }

    public double getRightDomainBorder() {
        return points[pointsCount - 1].getX();
    }

    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) {
            return Double.NaN;
        }

        for (int i = 0; i < pointsCount - 1; i++) {
            double x1 = points[i].getX();
            double x2 = points[i + 1].getX();

            if (lessOrEqual(x1, x) && lessOrEqual(x, x2)) {
                // проверяем точное совпадение с x1
                if (equals(x, x1)) {
                    return points[i].getY();
                }
                // проверяем точное совпадение с x2
                if (equals(x, x2)) {
                    return points[i + 1].getY();
                }

                // линейная интерполяция
                double y1 = points[i].getY();
                double y2 = points[i + 1].getY();
                return y1 + (y2 - y1) * (x - x1) / (x2 - x1);
            }
        }

        return Double.NaN;
    }

    public int getPointsCount() {
        return pointsCount;
    }

    public FunctionPoint getPoint(int index) {
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " вне границ [0, " + (pointsCount - 1) + "]");
        }

        return new FunctionPoint(points[index]);
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " вне границ [0, " + (pointsCount - 1) + "]");
        }

        // Проверка порядка точек
        if (index > 0 && lessOrEqual(point.getX(), points[index - 1].getX())) {
            throw new InappropriateFunctionPointException("X координата должна быть больше предыдущей точки");
        }

        if (index < pointsCount - 1 && lessOrEqual(points[index + 1].getX(), point.getX())) {
            throw new InappropriateFunctionPointException("X координата должна быть меньше следующей точки");
        }

        points[index] = new FunctionPoint(point);
    }

    public double getPointX(int index) {
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " вне границ [0, " + (pointsCount - 1) + "]");
        }

        return points[index].getX();
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " вне границ [0, " + (pointsCount - 1) + "]");
        }

        // Проверка порядка точек
        if (index > 0 && lessOrEqual(x, points[index - 1].getX())) {
            throw new InappropriateFunctionPointException("X координата должна быть больше предыдущей точки");
        }

        if (index < pointsCount - 1 && lessOrEqual(points[index + 1].getX(), x)) {
            throw new InappropriateFunctionPointException("X координата должна быть меньше следующей точки");
        }

        // безопасно заменяем
        double oldY = points[index].getY();
        points[index] = new FunctionPoint(x, oldY);
    }

    public double getPointY(int index) {
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " вне границ [0, " + (pointsCount - 1) + "]");
        }

        return points[index].getY();
    }

    public void setPointY(int index, double y) {
        if (index < 0 || index >= pointsCount) {
           throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " вне границ [0, " + (pointsCount - 1) + "]"); 
        }

        double oldX = points[index].getX();
        points[index] = new FunctionPoint(oldX, y);
    }

    public void deletePoint(int index) {
        if (pointsCount < 3) {
            throw new IllegalStateException("Нельзя удалить точку: минимальное количество точек - 3");
        }

        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " вне границ [0, " + (pointsCount - 1) + "]");
        }

        for (int i = index; i < pointsCount - 1; i++) {
            points[i] = points[i+1];
        }

        pointsCount--;
        points[pointsCount] = null;
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        // Проверка на дублирование X
        for (int i = 0; i < pointsCount; i++) {
            if (equals(points[i].getX(), point.getX())) {
                throw new InappropriateFunctionPointException("Точка с X=" + point.getX() + " уже существует");
            }
        }

        FunctionPoint newPoint = new FunctionPoint(point);

        int insertIndex = 0; // поиск индекса куда вставить точку
        while (insertIndex < pointsCount && lessOrEqual(points[insertIndex].getX(), newPoint.getX())) {
            insertIndex++;
        }

        if ( pointsCount == points.length) { // если не хватает места
            int newCapacity = points.length+10;
            FunctionPoint[] newArray = new FunctionPoint[newCapacity];
            for (int i = 0; i < pointsCount; i++) {
                newArray[i] = points[i];
            }
            points = newArray;
        }
        
        // сдвиг точек право (для освобождения места)
        if (pointsCount - insertIndex > 0) {
            for (int i = pointsCount; i > insertIndex; i--) {
                points[i] = points[i - 1];
            }
        }

        points[insertIndex] = newPoint;
        pointsCount++;
    }
}
