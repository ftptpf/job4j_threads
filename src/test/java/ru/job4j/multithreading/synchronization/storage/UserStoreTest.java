package ru.job4j.multithreading.synchronization.storage;

import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class UserStoreTest {
    private class ThreadTransferMoney extends Thread {
        private final UserStore userStore;

        private ThreadTransferMoney(UserStore userStore) {
            this.userStore = userStore;
        }

        @Override
        public void run() {
            this.userStore.transfer(1, 2, 20);
        }
    }

    @Test
    public void whenExecute2Thread() throws InterruptedException {
        final UserStore us = new UserStore();
        User userFrom = new User(1, 100);
        User userTo = new User(2, 10);
        us.add(userFrom);
        us.add(userTo);
        Thread thread1 = new ThreadTransferMoney(us);
        Thread thread2 = new ThreadTransferMoney(us);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat()
    }
}