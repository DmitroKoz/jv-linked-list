package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T x, Node<T> next) {
            this.prev = prev;
            value = x;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> addedNode = new Node<>(tail, value, null);
        if (isEmpty()) {
            head = addedNode;
        } else {
            tail.next = addedNode;
        }
        tail = addedNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Can't ADD element by that index");
        } else if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> addingNode = new Node<T>(null, value, head);
            head = addingNode;
            size++;
        } else {
            Node<T> previousNode = findNodeByIndex(index).prev;
            Node<T> nextNode = previousNode.next;
            Node<T> addingNode = new Node<>(previousNode, value, nextNode);
            nextNode.prev = addingNode;
            previousNode.next = addingNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Can't GET element by that index");
        }
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Can't SET element by that index");
        }
        Node<T> nodeToSet = findNodeByIndex(index);
        T oldValue = nodeToSet.value;
        nodeToSet.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Can't REMOVE element by that index");
        }
        if (isEmpty()) {
            return null;
        }
        Node<T> nodeToRemove = findNodeByIndex(index);
        unLink(findNodeByIndex(index));
        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        if (isEmpty()) {
            return false;
        }
        Node<T> nodeToRemove;
        for (nodeToRemove = head; nodeToRemove != null; nodeToRemove = nodeToRemove.next) {
            if (object == nodeToRemove.value
                    || object != null && object.equals(nodeToRemove.value)) {
                unLink(nodeToRemove);
                return true;
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

    private void unLink(Node<T> node) {
        Node<T> previousNode;
        Node<T> nextNode;
        if (node.prev == null && node.next == null) {
            size--;
        } else if (node.prev == null) {
            head = node.next;
            head.prev = null;
            size--;
        } else if (node.next == null) {
            tail = node.prev;
            tail.next = null;
            size--;
        } else {
            previousNode = node.prev;
            nextNode = node.next;
            previousNode.next = nextNode;
            nextNode.prev = previousNode;
            size--;
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> soughtNode;
        if (index < size / 2) {
            soughtNode = head;
            for (int i = 0; i < index; i++) {
                soughtNode = soughtNode.next;
            }
        } else {
            soughtNode = tail;
            for (int i = size - 1; i > index; --i) {
                soughtNode = soughtNode.prev;
            }
        }
        return soughtNode;
    }
}
