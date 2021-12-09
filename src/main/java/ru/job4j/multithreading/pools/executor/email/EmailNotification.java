package ru.job4j.multithreading.pools.executor.email;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Рассылка почты.
 */
@ThreadSafe
public class EmailNotification {
    @GuardedBy("this")
    ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    /**
     * Осуществляем подготовку данных для отправки.
     * @param user
     */
    public void emailTo(User user) {
        String subject = "Notification {username} to email {email}";
        String body = "Add a new event to {username}";
        String username = user.getUsername();
        String email = user.getEmail();
        pool.submit(() -> {

        });

    }

    /**
     * Закрываем пул потоков.
     */
    public void close() throws InterruptedException {
        pool.shutdown();
        pool.awaitTermination(10L, TimeUnit.MINUTES);
    }

    /**
     * Отправляем сообщение. Метод оставлен пустым.
     * @param subject тема письма
     * @param body содержание письма
     * @param email электронный адрес
     */
    public void send(String subject, String body, String email) {
    }
}
