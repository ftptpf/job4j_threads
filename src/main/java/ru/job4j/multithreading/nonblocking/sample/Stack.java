package ru.job4j.multithreading.nonblocking.sample;

import net.jcip.annotations.NotThreadSafe;

/**
 * Не потокобезопасный Stack.
 * Метод push и poll используют шаблон check-then-act.
 */
@NotThreadSafe
public class Stack<T> {
    private Node<T> head;

    public void push(T value) {
        Node<T> temp = new Node<>(value);
        if (head == null) {
            head = temp;
            return;
        }
        temp.next = head;
        head = temp;
    }

    public T poll() {
        Node<T> temp = head;
        if (temp == null) {
            throw new IllegalArgumentException("Stack is empty");
        }
        head = temp.next;
        temp.next = null;
        return temp.value;
    }

    private static final class Node<T> {
        private final T value;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }
}
