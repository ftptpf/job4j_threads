package ru.job4j.multithreading.pools.executor.email;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Рассылка почты.
 */
@ThreadSafe
public class EmailNotification {
    @GuardedBy("this")
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    /**
     * Осуществляем подготовку данных для отправки.
     * @param user
     */
    public synchronized void emailTo(User user) {
        String username = user.getUsername();
        String email = user.getEmail();
        String subject = "Notification " + username + " to email " + email;
        String body = "Add a new event to " + username;
        pool.execute(() -> send(subject, body, email));
    }

    /**
     * Закрываем пул потоков.
     */
    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Отправляем сообщение. Метод оставлен пустым, только вывод на консоль для демонстрации.
     * @param subject тема письма
     * @param body содержание письма
     * @param email электронный адрес
     */
    public synchronized void send(String subject, String body, String email) {
        System.out.println("Subject: " + subject + " Body: " + body + " Email: " + email);
    }

    public static void main(String[] args) {
        EmailNotification emailNotification = new EmailNotification();
        List<User> users = new ArrayList<>();
        users.add(new User("Andy", "andy@mail.ru"));
        users.add(new User("Tony", "tony@mail.ru"));
        users.add(new User("Liza", "liza@mail.ru"));
        users.add(new User("Igor", "igor@mail.ru"));
        users.add(new User("Oleg", "oleg@mail.ru"));
        for (User user : users) {
            emailNotification.emailTo(user);
        }
        emailNotification.close();
    }
}
