package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + ", but Size " + size);
        }
    }

    @Override
    public void add(T value) {
        Node newNode = new Node(tail, value, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + ", but Size " + size);
        }
        Node newNode = new Node(null, value, null);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        } else {
            Node prevNode = head;
            for (int i = 0; i < index - 1; i++) {
                prevNode = prevNode.next;
            }
            Node nextNode = prevNode.next;
            newNode.prev = prevNode;
            newNode.next = nextNode;
            prevNode.next = newNode;
            if (nextNode != null) {
                nextNode.prev = newNode;
            }
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            return;
        }
        for (T element : list) {
            this.add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node current;
        if (index <= size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node current;
        T old;
        if (index <= size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            old = current.item;
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            old = current.item;
        }
        current.item = value;
        return old;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);

        Node current;

        if (index <= size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }

        T old = current.item;
        Node prevNode = current.prev;
        Node nextNode = current.next;

        if (current == head) {
            head = nextNode;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
        } else if (current == tail) {
            tail = prevNode;
            if (tail != null) {
                tail.next = null;
            } else {
                head = null;
            }
        } else {
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }

        size--;
        return old;
    }

    @Override
    public boolean remove(T object) {
        if (size == 1 && (object == null ? head.item == null : object.equals(head.item))) {
            head = null;
            tail = null;
            size--;
            return true;
        } else if (size > 1) {
            Node current = head;
            while (current != null) {
                if (object == null ? current.item == null : object.equals(current.item)) {
                    Node prevNode = current.prev;
                    Node nextNode = current.next;
                    if (current == head) {
                        head = current.next;
                        head.prev = null;
                    } else if (current == tail) {
                        tail = current.prev;
                        tail.next = null;
                    } else {
                        prevNode.next = nextNode;
                        nextNode.prev = prevNode;
                    }
                    size--;
                    return true;
                }
                current = current.next;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private class Node {
        private T item;
        private Node prev;
        private Node next;

        public Node(Node prev, T value, Node next) {
            this.item = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
