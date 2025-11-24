public class Main {
    public static void main(String[] args) {
        System.out.println("=== ТЕСТИРОВАНИЕ ARRAY TABULATED FUNCTION ===");
        testArrayFunction();
        
        System.out.println("\n=== ТЕСТИРОВАНИЕ LINKED LIST TABULATED FUNCTION ===");
        testLinkedListFunction();
        
        System.out.println("\n=== ТЕСТИРОВАНИЕ ИСКЛЮЧЕНИЙ ===");
        testExceptions();
    }

    private static void testArrayFunction() {
        try {
            double[] values = {0.0, 1.0, 4.0, 9.0, 16.0, 25.0};
            functions.TabulatedFunction func = new functions.ArrayTabulatedFunction(0.0, 5.0, values);
            
            System.out.println("ArrayTabulatedFunction создана успешно!");
            System.out.println("Границы: [" + func.getLeftDomainBorder() + ", " + func.getRightDomainBorder() + "]");
            System.out.println("Количество точек: " + func.getPointsCount());
            
            System.out.println("\n--- ДЕТАЛЬНАЯ ДЕМОНСТРАЦИЯ РАБОТЫ ARRAY ---");
            
            // 1. Начальное состояние
            System.out.println("1. НАЧАЛЬНОЕ СОСТОЯНИЕ ФУНКЦИИ:");
            printFunctionTable(func);
            
            // 2. Добавление точки - ПОКАЗЫВАЕМ ДО и ПОСЛЕ
            System.out.println("2. ДОБАВЛЕНИЕ ТОЧКИ (2.5, 6.25):");
            System.out.println("ФУНКЦИЯ ДО добавления:");
            printFunctionTable(func);
            func.addPoint(new functions.FunctionPoint(2.5, 6.25));
            System.out.println("ФУНКЦИЯ ПОСЛЕ добавления:");
            printFunctionTable(func);
            
            // 3. Изменение точки - ПОКАЗЫВАЕМ ДО и ПОСЛЕ
            System.out.println("3. ИЗМЕНЕНИЕ ТОЧКИ [2]:");
            System.out.println("ФУНКЦИЯ ДО изменения:");
            printFunctionTable(func);
            func.setPoint(2, new functions.FunctionPoint(2.0, 10.0));
            System.out.println("ФУНКЦИЯ ПОСЛЕ изменения:");
            printFunctionTable(func);
            
            // 4. Изменение координаты Y - ПОКАЗЫВАЕМ ДО и ПОСЛЕ
            System.out.println("4. ИЗМЕНЕНИЕ КООРДИНАТЫ Y ТОЧКИ [1]:");
            System.out.println("ФУНКЦИЯ ДО изменения:");
            printFunctionTable(func);
            func.setPointY(1, 2.0);
            System.out.println("ФУНКЦИЯ ПОСЛЕ изменения:");
            printFunctionTable(func);
            
            // 5. Удаление точки - ПОКАЗЫВАЕМ ДО и ПОСЛЕ
            System.out.println("5. УДАЛЕНИЕ ТОЧКИ [2]:");
            System.out.println("ФУНКЦИЯ ДО удаления:");
            printFunctionTable(func);
            func.deletePoint(2);
            System.out.println("ФУНКЦИЯ ПОСЛЕ удаления:");
            printFunctionTable(func);
            
            // 6. Демонстрация интерполяции
            System.out.println("6. ВЫЧИСЛЕНИЕ ЗНАЧЕНИЙ ФУНКЦИИ:");
            printFunctionValues(func, new double[]{0.5, 1.5, 2.5, 3.5, 4.5});
            
        } catch (Exception e) {
            System.out.println("Ошибка в Array: " + e.getMessage());
        }
    }

    private static void testLinkedListFunction() {
        try {            
            double[] values = {0.0, 1.0, 4.0, 9.0, 16.0, 25.0};
            functions.TabulatedFunction func = new functions.LinkedListTabulatedFunction(0.0, 5.0, values);
        
            System.out.println("LinkedListTabulatedFunction создана успешно!");
            System.out.println("Границы: [" + func.getLeftDomainBorder() + ", " + func.getRightDomainBorder() + "]");
            System.out.println("Количество точек: " + func.getPointsCount());
            
            System.out.println("\n--- ДЕТАЛЬНАЯ ДЕМОНСТРАЦИЯ РАБОТЫ LINKEDLIST ---");
            
            // 1. Начальное состояние
            System.out.println("1. НАЧАЛЬНОЕ СОСТОЯНИЕ ФУНКЦИИ:");
            printFunctionTable(func);
            
            // 2. Добавление точки - ПОКАЗЫВАЕМ ДО и ПОСЛЕ
            System.out.println("2. ДОБАВЛЕНИЕ ТОЧКИ (2.5, 6.25):");
            System.out.println("ФУНКЦИЯ ДО добавления:");
            printFunctionTable(func);
            func.addPoint(new functions.FunctionPoint(2.5, 6.25));
            System.out.println("ФУНКЦИЯ ПОСЛЕ добавления:");
            printFunctionTable(func);
            
            // 3. Изменение точки - ПОКАЗЫВАЕМ ДО и ПОСЛЕ
            System.out.println("3. ИЗМЕНЕНИЕ ТОЧКИ [2]:");
            System.out.println("ФУНКЦИЯ ДО изменения:");
            printFunctionTable(func);
            func.setPoint(2, new functions.FunctionPoint(2.0, 10.0));
            System.out.println("ФУНКЦИЯ ПОСЛЕ изменения:");
            printFunctionTable(func);
            
            // 4. Изменение координаты Y - ПОКАЗЫВАЕМ ДО и ПОСЛЕ
            System.out.println("4. ИЗМЕНЕНИЕ КООРДИНАТЫ Y ТОЧКИ [1]:");
            System.out.println("ФУНКЦИЯ ДО изменения:");
            printFunctionTable(func);
            func.setPointY(1, 2.0);
            System.out.println("ФУНКЦИЯ ПОСЛЕ изменения:");
            printFunctionTable(func);
            
            // 5. Удаление точки - ПОКАЗЫВАЕМ ДО и ПОСЛЕ
            System.out.println("5. УДАЛЕНИЕ ТОЧКИ [2]:");
            System.out.println("ФУНКЦИЯ ДО удаления:");
            printFunctionTable(func);
            func.deletePoint(2);
            System.out.println("ФУНКЦИЯ ПОСЛЕ удаления:");
            printFunctionTable(func);
            
            // 6. Демонстрация интерполяции
            System.out.println("6. ВЫЧИСЛЕНИЕ ЗНАЧЕНИЙ ФУНКЦИИ:");
            printFunctionValues(func, new double[]{0.5, 1.5, 2.5, 3.5, 4.5});
            
        } catch (Exception e) {
            System.out.println("Ошибка в LinkedList: " + e.getMessage());
        }
    }
    
    // Метод для вывода таблицы функции (все точки)
    private static void printFunctionTable(functions.TabulatedFunction func) {
        System.out.println("   Таблица функции:");
        System.out.println("   Все точки функции:");
        for (int i = 0; i < func.getPointsCount(); i++) {
            functions.FunctionPoint point = func.getPoint(i);
            System.out.printf("   Индекс %d: X = %.3f, Y = %.3f%n", i, point.getX(), point.getY());
        }
        System.out.println("   Всего точек: " + func.getPointsCount());
        System.out.println("   Границы: [" + func.getLeftDomainBorder() + ", " + func.getRightDomainBorder() + "]");
        System.out.println();
    }
    
    // Метод для вывода вычисленных значений функции
    private static void printFunctionValues(functions.TabulatedFunction func, double[] xValues) {
        System.out.println("   Вычисленные значения:");
        for (double x : xValues) {
            double y = func.getFunctionValue(x);
            System.out.printf("   f(%.3f) = %.3f%n", x, y);
        }
        System.out.println();
    }

    private static void testExceptions() {
        // Тесты исключений остаются без изменений
        System.out.println("1. Тест неверных границ:");
        try {            
            functions.TabulatedFunction func = new functions.ArrayTabulatedFunction(10.0, 5.0, 5);
        } catch (IllegalArgumentException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
        }

        System.out.println("2. Тест недостаточного количества точек:");
        try {    
            functions.TabulatedFunction func = new functions.LinkedListTabulatedFunction(0.0, 5.0, 1);
        } catch (IllegalArgumentException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
        }

        System.out.println("3. Тест выхода за границы индекса:");
        try {            
            double[] values = {0.0, 1.0, 4.0};
            functions.TabulatedFunction func = new functions.ArrayTabulatedFunction(0.0, 2.0, values);
            func.getPoint(10);
        } catch (functions.FunctionPointIndexOutOfBoundsException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
        }

        System.out.println("4. Тест нарушения порядка точек:");
        try {
            double[] values = {0.0, 1.0, 4.0};
            functions.TabulatedFunction func = new functions.LinkedListTabulatedFunction(0.0, 2.0, values);
            func.setPoint(1, new functions.FunctionPoint(2.5, 2.0));
        } catch (functions.InappropriateFunctionPointException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
        }

        System.out.println("5. Тест дублирования X координаты:");
        try {            
            double[] values = {0.0, 1.0, 4.0};
            functions.TabulatedFunction func = new functions.ArrayTabulatedFunction(0.0, 2.0, values);
            func.addPoint(new functions.FunctionPoint(1.0, 5.0));
        } catch (functions.InappropriateFunctionPointException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
        }

        System.out.println("6. Тест удаления при минимальном количестве точек:");
        try {
            double[] values = {0.0, 1.0, 4.0};
            functions.TabulatedFunction func = new functions.LinkedListTabulatedFunction(0.0, 2.0, values);
            func.deletePoint(0);
            func.deletePoint(0);
            func.deletePoint(0);
        } catch (IllegalStateException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
        }
    }
}