package game.auth.tictactoe.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration(proxyBeanMethods = false)
@EnableRedisRepositories(basePackages = "game.auth.tictactoe.domain.repository")
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory(new RedisStandaloneConfiguration(redisHost, redisPort));
    }
}

//class MyStack<T> {
//    private T[] stackArray;
//    private int size;
//    private int capacity;
//
//    // Конструктор
//    @SuppressWarnings("unchecked")
//    public MyStack(int size) {
//        if (size <= 0) {
//            throw new IllegalArgumentException("Размер стека должен быть больше 0!");
//        }
//
//        this.capacity = size * 2;
//        this.stackArray = (T[]) new Object[capacity]; // Создаем массив (нельзя напрямую создать generic массив)
//        this.size = 0; // Начальное значение (стек пуст)
//    }
//
//    // Добавление элемента в стек (Push)
//    public void push(T item) {
//        if (isFull()) {
//            resize();
//        }
//
//        stackArray[size++] = item;
//    }
//
//    private void resize() {
//        capacity *= 2;
//        stackArray = Arrays.copyOf(stackArray, capacity);
//    }
//
//    // Удаление элемента из стека (Pop)
//    public T pop() {
//        if (isEmpty()) {
//            throw new RuntimeException("Стек пуст!");
//        }
//
//        int index = size - 1;
//
//        T item = stackArray[index];
//
//        stackArray[index] = null;
//
//        size--;
//
//        return item;
//    }
//
//    // Просмотр верхнего элемента (Peek)
//    public T peek() {
//        if (isEmpty()) {
//            throw new RuntimeException("Стек пуст!");
//        }
//        return stackArray[size - 1];
//    }
//
//    // Проверка, пуст ли стек
//    public boolean isEmpty() {
//        return size == 0;
//    }
//
//    // Проверка, полон ли стек
//    public boolean isFull() {
//        return size == capacity;
//    }
//
//    // Получение размера стека
//    public int size() {
//        return size;
//    }
//
//    @Override
//    public String toString() {
//        System.out.println("CAP = " + capacity);
//        System.out.println("ARRAY SIZE = " + stackArray.length);
//        return Arrays.toString(stackArray);
//    }
//
//    // Тестирование стека
//    public static void main(String[] args) {
//        MyStack<Integer> stack = new MyStack<>(5);
//
//        stack.push(10);
//        stack.push(20);
//        stack.push(30);
//
//        System.out.println("Верхний элемент: " + stack.peek()); // 30
//
//        System.out.println("Удалённый элемент: " + stack.pop()); // 30
//
//        System.out.println("Размер стека: " + stack.size()); // 2
//
//        System.out.println("Верхний элемент: " + stack.peek()); // 20
//
//        System.out.println("Стек пуст?: " + stack.isEmpty()); // false
//
//        System.out.println("Стек полон?: " + stack.isFull()); // false
//
//        System.out.println("Удалённый элемент: " + stack.pop()); // 20
//
//        System.out.println("Размер стека: " + stack.size()); // 1
//
//        System.out.println("Последний элемент: " + stack.peek()); // 10
//
//        stack.pop();
//
//        for(int i = 0; i < 30; i++) {
//            stack.push(i);
//        }
//
//        System.out.println(stack);
//    }
//}

//class MyQueue<T> {
//    private T[] queueArray;
//    private int size;
//    private int capacity;
//
//    // Конструктор
//    @SuppressWarnings("unchecked")
//    public MyQueue(int initialSize) {
//        if (initialSize <= 0) {
//            throw new IllegalArgumentException("Размер очереди должен быть больше 0!");
//        }
//
//        this.capacity = initialSize * 2;
//        this.queueArray = (T[]) new Object[capacity];
//        this.size = 0;
//    }
//
//    // Добавление элемента (enqueue)
//    public void enqueue(T item) {
//        if (isFull()) {
//            resize();
//        }
//
//        queueArray[size++] = item;
//    }
//
//    // Удаление элемента (dequeue) с **сдвигом массива**
//    public T dequeue() {
//        if (isEmpty()) {
//            throw new RuntimeException("Очередь пуста!");
//        }
//
//        T item = queueArray[0];
//
//        // Сдвигаем все элементы влево
//        for (int i = 1; i < size; i++) {
//            queueArray[i - 1] = queueArray[i];
//        }
//
//        queueArray[--size] = null; // Очищаем последний элемент
//
//        return item;
//    }
//
//    // Просмотр первого элемента (peek)
//    public T peek() {
//        if (isEmpty()) {
//            throw new RuntimeException("Очередь пуста!");
//        }
//        return queueArray[0];
//    }
//
//    // Проверка, пуста ли очередь
//    public boolean isEmpty() {
//        return size == 0;
//    }
//
//    // Проверка, полна ли очередь
//    public boolean isFull() {
//        return size == capacity;
//    }
//
//    // Изменение размера массива
//    private void resize() {
//        capacity *= 2;
//        queueArray = Arrays.copyOf(queueArray, capacity);
//    }
//
//    // Получение размера очереди
//    public int size() {
//        return size;
//    }
//
//    @Override
//    public String toString() {
//        return Arrays.toString(queueArray);
//    }
//
//    // Тестирование
//    public static void main(String[] args) {
//        MyQueue<Integer> queue = new MyQueue<>(3);
//
//        queue.enqueue(10);
//        queue.enqueue(20);
//        queue.enqueue(30);
//        System.out.println(queue); // [10, 20, 30, null, null, null]
//
//        queue.enqueue(40);
//        System.out.println(queue); // [10, 20, 30, 40, null, null, ...]
//
//        System.out.println("Удалено: " + queue.dequeue()); // 10
//        System.out.println(queue); // [20, 30, 40, null, null, null]
//
//        System.out.println("Первый элемент: " + queue.peek()); // 20
//
//        queue.enqueue(50);
//        System.out.println(queue); // [20, 30, 40, 50, null, null]
//
//        System.out.println((byte)128);
//
//        Stack<Integer> d = new ArrayDeque<Integer>();
//    }
//}

//class com {
//    public static void main(String[] args) {
//        System.out.println((byte)128);
//
//        List.of(1, 2, 3, 4).parallelStream()
//                .forEach(System.out::println);
//    }
//}