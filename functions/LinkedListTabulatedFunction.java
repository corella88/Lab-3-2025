package functions;

public class LinkedListTabulatedFunction implements TabulatedFunction {
    // Внутренний класс для узла списка
    private static class FunctionNode {
        FunctionPoint point;
        FunctionNode prev;
        FunctionNode next;
        
        FunctionNode(FunctionPoint point) {
            this.point = point;
        }
    }

    private FunctionNode head; // Голова списка (не содержит данных)

    private FunctionNode lastAccessedNode; // Для оптимизации
    private int lastAccessedIndex;

    private int pointsCount;
    
    private static final double EPSILON = 1e-10;
    
    // Вспомогательные методы для сравнения
    private boolean equals(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    private boolean lessOrEqual(double a, double b) {
        return a < b || equals(a, b);
    }

    private void initializeList() {
        head = new FunctionNode(null);  // голова с null данными
        head.prev = head;                     // замыкаем на себя
        head.next = head;                     // замыкаем на себя
        pointsCount = 0;

        lastAccessedNode = head;
        lastAccessedIndex = -1;
    }
    
    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (leftX >= rightX) {
            throw new IllegalArgumentException("Левая граница должна быть меньше правой");
        }
        if (pointsCount < 2) {
            throw new IllegalArgumentException("Количество точек должно быть не менее 2");
        }

        initializeList();

        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i * step;
            addNodeToTail().point = new FunctionPoint(x, 0);  // пока значения Y = 0
        }
    }
    
    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) {
        if (leftX >= rightX) {
            throw new IllegalArgumentException("Левая граница должна быть меньше правой");
        }
        if (values.length < 2) {
            throw new IllegalArgumentException("Количество точек должно быть не менее 2");
        }
        
        initializeList();

        double step = (rightX - leftX) / (values.length - 1);
        for (int i = 0; i < values.length; i++) {
            double x = leftX + i * step;
            addNodeToTail().point = new FunctionPoint(x, values[i]);
        }
    }

    private FunctionNode getNodeByIndex(int index) {
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " вне границ [0, " + (pointsCount - 1) + "]");
        }

        // Оптимизация: начинаем с последнего доступного узла если это ближе
        FunctionNode current;
        if (lastAccessedIndex != -1 && Math.abs(index - lastAccessedIndex) < Math.min(index, pointsCount - index)) {
            current = lastAccessedNode;
            int currentIndex = lastAccessedIndex;
        
            if (index > currentIndex) {
                for (int i = currentIndex; i < index; i++) {
                    current = current.next;
                }
            } else {
                for (int i = currentIndex; i > index; i--) {
                    current = current.prev;
                }
            }
        } else {
            // Иначе начинаем с начала
            current = head.next;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        }
    
        // Сохраняем для следующего вызова
        lastAccessedNode = current;
        lastAccessedIndex = index;

        return current;
    }

    private FunctionNode addNodeByIndex(int index) {
        if (index < 0 || index > pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс: " + index);
        }

        FunctionNode newNode = new FunctionNode(new FunctionPoint(0, 0));
        FunctionNode currentNode;

        if (index == pointsCount) {
            currentNode = head;  // вставка в конец
        } else {
            currentNode = getNodeByIndex(index);
        }

        // Вставляем newNode между currentNode.prev и currentNode
        newNode.prev = currentNode.prev;
        newNode.next = currentNode;
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
    
        pointsCount++;
        
        lastAccessedNode = newNode;
        lastAccessedIndex = index;

        return newNode;
    }

    private FunctionNode deleteNodeByIndex(int index) {
        if (pointsCount < 3) {
            throw new IllegalStateException("Нельзя удалить точку: минимальное количество точек - 3");
        }

        FunctionNode nodeToDelete = getNodeByIndex(index);
    
        // Исключаем nodeToDelete из списка
        nodeToDelete.prev.next = nodeToDelete.next;
        nodeToDelete.next.prev = nodeToDelete.prev;
    
        pointsCount--;

        if (lastAccessedIndex == index) {
            lastAccessedNode = head;
            lastAccessedIndex = -1;
        } else if (lastAccessedIndex > index) {
            lastAccessedIndex--;
        }

        return nodeToDelete;
    }

    public double getLeftDomainBorder() {
        return head.next.point.getX();  // первая реальная точка после головы
    }

    public double getRightDomainBorder() {
        return head.prev.point.getX();  // последняя реальная точка перед головой
    }

    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) {
            return Double.NaN;
        }

        // Ищем отрезок, содержащий x
        FunctionNode current = head.next;
        while (current != head && current.next != head) {
            if (lessOrEqual(current.point.getX(), x) && lessOrEqual(x, current.next.point.getX())) {
                // Линейная интерполяция
                double k = (current.next.point.getY() - current.point.getY()) / (current.next.point.getX() - current.point.getX());
                return current.point.getY() + k * (x - current.point.getX());
            }

            current = current.next;
        }

        return Double.NaN;
    }

    public int getPointsCount() {
        return pointsCount;
    }

    public FunctionPoint getPoint(int index) {
        return new FunctionPoint(getNodeByIndex(index).point);
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        FunctionNode node = getNodeByIndex(index);

        // Проверка порядка точек
        if (index > 0 && lessOrEqual(point.getX(), node.prev.point.getX())) {
            throw new InappropriateFunctionPointException("X координата должна быть больше предыдущей точки");
        }
        if (index < pointsCount - 1 && lessOrEqual(node.next.point.getX(), point.getX())) {
            throw new InappropriateFunctionPointException("X координата должна быть меньше следующей точки");
        }

        node.point = new FunctionPoint(point);
    }

    public double getPointX(int index) {
        return getNodeByIndex(index).point.getX();
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        FunctionNode node = getNodeByIndex(index);

        // Проверка порядка точек
        if (index > 0 && lessOrEqual(x, node.prev.point.getX())) {
            throw new InappropriateFunctionPointException("X координата должна быть больше предыдущей точки");
        }

        if (index < pointsCount - 1 && lessOrEqual(node.next.point.getX(), x)) {
            throw new InappropriateFunctionPointException("X координата должна быть меньше следующей точки");
        }

        double oldY = node.point.getY();
        node.point = new FunctionPoint(x, oldY);
    }

    public double getPointY(int index) {
        return getNodeByIndex(index).point.getY();
    }

    public void setPointY(int index, double y) {
        FunctionNode node = getNodeByIndex(index);
        double oldX = node.point.getX();
        node.point = new FunctionPoint(oldX, y);
    }

    public void deletePoint(int index) {
        deleteNodeByIndex(index);
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        // Проверка на дублирование X
        FunctionNode current = head.next;
        while (current != head) {
            if (equals(current.point.getX(), point.getX())) {
                throw new InappropriateFunctionPointException("Точка с X=" + point.getX() + " уже существует");
            }

            current = current.next;
        }

        // Поиск позиции для вставки
        int insertIndex = 0;
        current = head.next;
        while (current != head && current.point.getX() < point.getX()) {
            current = current.next;
            insertIndex++;
        }

        FunctionNode newNode = addNodeByIndex(insertIndex);
        newNode.point = new FunctionPoint(point);
    }

    private FunctionNode addNodeToTail() {
        return addNodeByIndex(pointsCount);
    }
}
