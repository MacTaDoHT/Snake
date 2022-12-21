package Snake;

import javax.swing.*;   // Создал для графичекого интерфейса, для отрисовки используется 2D
import java.awt.*;      // Создал для отрисовки игорового поля
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// Создал main игры
public class GameSnake extends JFrame {
    // Константы
    final String TITLE_OF_PROGRAM = "Приключения Черной Мамбы"; // Название приложения
    final String GAME_OVER_MSG = "Из вас сделали одежду";       // Уведомление о вашем пройгрише
    final static int CELL_SIZE = 20;                            // Размер ячеики в пикселях
    final static int CANVAS_WIDTH = 30;                         // Размер поля в ширину
    final static int CANVAS_HEIGHT = 25;                        // Размер поля в высоту
    final static Color SNAKE_COLOR = Color.black;               // Цвет змеюки
    final static Color FOOD_COLOR = Color.green;                // Цевт еды
    final static Color POISON_COLOR = Color.red;                // Цвет яда
    final static int KEY_LEFT = 37;                             // Код кнопки с клавиатуры (лево)
    final static int KEY_UP = 38;                               // Код кнопки с клавиатуры (вверх)
    final static int KEY_RIGHT = 39;                            // Код кнопки с клавиатуры (право)
    final static int KEY_DOWN = 40;                             // Код кнопки с клавиатуры (вниз)
    final int START_SNAKE_SIZE = 5;                             // Дефолтная длинна змеюки
    final int START_SNAKE_X = CANVAS_WIDTH/2;                   // Начальное положение змеюки
    final int START_SNAKE_Y = CANVAS_HEIGHT/2;                  // Начальное положение змеюки
    final int SNAKE_DELAY = 150;                                // Задрежка при движении змеюки
    int snakeSize = 0;                                          // Текущий размер змеи
    static boolean gameOver = false;                            // Знак, игра окончена или нет

    // Создаю обьекты игры
    Canvas canvas; // Игровое поле
    Snake snake;   // Змеюка
    Food food;     // Еда
    Poison poison; // Яд


    // Создал конструктор
    public GameSnake(){
        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Операция которая завершает программу

        canvas = new Canvas();                          // Создаю само поле
        canvas.setBackground(Color.lightGray);          // Цвет поля
        canvas.setPreferredSize(new Dimension(CELL_SIZE * CANVAS_WIDTH - 10,
                CELL_SIZE * CANVAS_HEIGHT - 10)); // Размер поля

        // Обьект (адаптер) позволяющий управлять змеям с клавиатуры
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                snake.setDirection(e.getKeyCode());
            }
        });

        // Создал окошко для игрового поля
        add(canvas);
        pack(); // Метод который создает размер окошка равный размерам "canvas"
        setLocationRelativeTo(null); // Относительно чего появляется окошко (поставил по центру)
        setResizable(false); // Окошко нельзя увеличивать или уменьшать ("False")
        setVisible(true); // Видимость окна вкл ("True")
    }

    public static void main(String[] args) {
        new GameSnake().game();
    }

    // Метод реализующий всю логику игры
    private void game() {
        snake = new Snake( // Создаю саму змеюку
                START_SNAKE_X,    // Координаты (начальное положение)
                START_SNAKE_Y,    // Координаты (начальное положение)
                START_SNAKE_SIZE, // Размер змеюки, первоначальный
                KEY_RIGHT         // Первоначальное движение в право
        );
        food = new Food(snake);
        snake.setFood(food);

        poison = new Poison(snake);
        poison.setFood(food);
        food.setPoison(poison);
        snake.setPoison(poison);

        // С помощью этого цикла змея двигается пока мы не проиграем
        while(!gameOver){
            snake.move();

            if (snake.size() > snakeSize){
                snakeSize = snake.size();
                setTitle(TITLE_OF_PROGRAM + ":" + snakeSize); // Видимость длинны змеюки в названии
            }

            if(food.isEaten()){
                food.appear();
                poison.add();
            }
            canvas.repaint();
            sleep(SNAKE_DELAY); // Уменьшение скорости движения змеюки
        }
        JOptionPane.showMessageDialog(this, GAME_OVER_MSG);
    }

    // Метод добавляющий задержки в поток, для того что бы змейка двигалась с умеренной скоростью
    private void sleep(long ms){
        try {
            Thread.sleep(ms);   // Поток и задержки(мс)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    // Метод необходимы для отрисовки
    class Canvas extends JPanel {    // Класс для рендеринга (рисования)
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2D = (Graphics2D) g;
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            snake.paint(g2D);  // Отрисовка змеюки
            food.paint(g2D);   // Отрисовка еды
            poison.paint(g2D); // Отрисовка яда
        }
    }
}