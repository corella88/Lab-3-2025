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
        } catch (Exception e) {
            System.out.println("Ошибка при создании ArrayTabulatedFunction: " + e.getMessage());
        }
    }

    private static void testLinkedListFunction() {
        try {            
            double[] values = {0.0, 1.0, 4.0, 9.0, 16.0, 25.0};
            functions.TabulatedFunction func = new functions.LinkedListTabulatedFunction(0.0, 5.0, values);
        
            System.out.println("LinkedListTabulatedFunction создана успешно!");
            System.out.println("Границы: [" + func.getLeftDomainBorder() + ", " + func.getRightDomainBorder() + "]");
            System.out.println("Количество точек: " + func.getPointsCount());
            
            // ↓↓↓ ДОБАВИТЬ ЭТИ ТЕСТЫ ↓↓↓
            
            System.out.println("\n--- Детальное тестирование методов LinkedList ---");
            
            // Тест getPoint()
            System.out.println("1. getPoint():");
            System.out.println("   Точка 0: x=" + func.getPoint(0).getX() + ", y=" + func.getPoint(0).getY());
            System.out.println("   Точка 3: x=" + func.getPoint(3).getX() + ", y=" + func.getPoint(3).getY());
            
            // Тест getFunctionValue() - интерполяция
            System.out.println("2. getFunctionValue() - интерполяция:");
            System.out.println("   f(0.5) = " + func.getFunctionValue(0.5));
            System.out.println("   f(2.5) = " + func.getFunctionValue(2.5));
            
            // Тест addPoint()
            System.out.println("3. addPoint():");
            func.addPoint(new functions.FunctionPoint(1.5, 2.25));
            System.out.println("   Добавлена точка (1.5, 2.25), теперь точек: " + func.getPointsCount());
            
            // Тест deletePoint()
            System.out.println("4. deletePoint():");
            func.deletePoint(2); // Удаляем точку с индексом 2
            System.out.println("   Удалена точка с индексом 2, теперь точек: " + func.getPointsCount());
            
            // Тест setPoint()
            System.out.println("5. setPoint():");
            func.setPoint(1, new functions.FunctionPoint(1.2, 1.44));
            System.out.println("   Изменена точка 1 на (1.2, 1.44)");
            
            // Тест setPointX() и setPointY()
            System.out.println("6. setPointX() и setPointY():");
            func.setPointX(2, 3.5);
            func.setPointY(2, 12.25);
            System.out.println("   Точка 2 изменена на: x=" + func.getPointX(2) + ", y=" + func.getPointY(2));
        
            // Финальная проверка
            System.out.println("7. Финальное состояние:");
            System.out.println("   Количество точек: " + func.getPointsCount());
            System.out.println("   Границы: [" + func.getLeftDomainBorder() + ", " + func.getRightDomainBorder() + "]");
        
            // Вывод всех точек
            System.out.println("   Все точки:");
            for (int i = 0; i < func.getPointsCount(); i++) {
                System.out.println("     " + i + ": (" + func.getPoint(i).getX() + ", " + func.getPoint(i).getY() + ")");
            }
        
        } catch (Exception e) {
            System.out.println("Ошибка при создании LinkedListTabulatedFunction: " + e.getMessage());
        }
    }

    private static void testExceptions() {
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
            func.getPoint(10); // Неверный индекс
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
            func.addPoint(new functions.FunctionPoint(1.0, 5.0)); // Дублирует X=1.0
        } catch (functions.InappropriateFunctionPointException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
        }

        System.out.println("6. Тест удаления при минимальном количестве точек:");
        try {
            double[] values = {0.0, 1.0, 4.0};
            functions.TabulatedFunction func = new functions.LinkedListTabulatedFunction(0.0, 2.0, values);
            func.deletePoint(0);
            func.deletePoint(0);
            func.deletePoint(0); // Попытка удалить последнюю точку            
        } catch (IllegalStateException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
        }
    }
}