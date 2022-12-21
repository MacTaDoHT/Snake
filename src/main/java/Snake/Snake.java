package Snake;

import java.awt.*;
import java.util.LinkedList; // Создал двусвязный спиисок ибо он удобнее массивов

// Создал змеюку
public class Snake {
    private LinkedList<Cell> snake; // Только для обьектов типа Cell (дженерики <*>)
    private int direction;          // Создал метод для определения направления змеюки
    private Food food;
    private Poison poison;

    // Конструктор ЧернойМамбы (змеюки)
    public Snake(int x, int y, int length, int direction){
        snake = new LinkedList<>(); // Создал команду для инициализации списка
        for(int i = 0; i < length; i++){
            snake.add(new Cell(x-i, y, GameSnake.CELL_SIZE, Color.black));
        }
        this.direction = direction; // Конструктор, это не только поля, там может быть еще и логика! Сабина©
    }

    public void setFood(Food food) {
        this.food = food;
    }
    public int size(){
        return snake.size();
    } // Метод для размера змейки
    public void setDirection(int direction){   // Метод для изменения направления змеюки, с ограничениями
        if(Math.abs(this.direction - direction) != 2){
            this.direction = direction;
        }
    }

    // С помощью этого метода мы понимаем прошла змеюка через еду или нет
    public boolean isInSnake(int x, int y){
        for(Cell cell : snake){
            if (cell.getX() == x && cell.getY() == y){
                return true;
            }
        }
        return false;
    }

    // Метод реализующий движение змеюки
    public void move() {
        int x = snake.getFirst().getX();
        int y = snake.getFirst().getY();
        switch (direction) { // Проверка значений которые хранятся в "direction"
            case GameSnake.KEY_LEFT: x--;  // На лево от x
                if (x < 0)
                    x = GameSnake.CANVAS_WIDTH - 1;
                break;
            case GameSnake.KEY_RIGHT: x++; // На право от x
                if (x == GameSnake.CANVAS_WIDTH)
                    x = 0;
                break;
            case GameSnake.KEY_UP: y--;    // В верх от y
                if (y < 0)
                    y = GameSnake.CANVAS_HEIGHT - 1;
                break;
            case GameSnake.KEY_DOWN: y++;  // В низ от y
                if (y == GameSnake.CANVAS_HEIGHT)
                    y = 0;
                break;
        }
        if(isInSnake(x, y) ||
                poison.isPoison(x, y)){
            GameSnake.gameOver = true;
            return;
        }

        snake.addFirst(new Cell(x, y, GameSnake.CELL_SIZE, GameSnake.SNAKE_COLOR));

        if(food.isFood(x, y)){
            food.eat();
        }else{
            snake.removeLast();
        }

    }

    // Метод отрисовки 2D змеюки
    public void paint(Graphics2D graphics2D){
        for(Cell cell : snake){
            cell.paint(graphics2D);
        }
    }

    public void setPoison(Poison poison) {
        this.poison = poison;
    }
}
