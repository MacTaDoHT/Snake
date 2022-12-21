package Snake;

import java.awt.*;  // Загрузил пакет с классами для графических манипуляций

// Создал игровое поле
public class Cell {
    private int x;
    private int y;
    private int size;
    private Color color;


    // Создал конструктор игрового поля
    public Cell(int x, int y, int size, Color color){
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }


    // Создал методы get and set для доступа к приватным переменным.
    public void set(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    // Создал метод для отрисовки поля
    public void paint(Graphics2D graphics2D){
        graphics2D.setColor(color);
        graphics2D.fillOval(x * size, y * size, size, size);
    }
}
