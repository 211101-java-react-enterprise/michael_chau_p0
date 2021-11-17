package com.Revature.Upposit.util;

public interface Deque<T> extends Collection<T> {
    void addFirst(T element);
    void addLast(T element);
    T pollFirst();
    T pollLast();
    T peekFirst();
    T peekLast();
}