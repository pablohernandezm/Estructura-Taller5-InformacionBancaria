package co.edu.unicartagena.Estructuras;

/**
 * Clase que representa una lista enlazada simple.
 *
 * @param <T> Tipo de dato que almacenará la lista.
 * @author Pablo José Hernández Meléndez
 */
public class SimpleLinkedList<T> {
    /**
     * Nodo inicial de la lista.
     */
    protected Node<T> head;
    /**
     * Tamaño de la lista.
     */
    protected int size;

    /**
     * Clase que representa un nodo de la lista.
     *
     * @param <E> Tipo de dato que almacenará el nodo.
     */
    protected static class Node<E> {
        /**
         * Valor que almacenará el nodo.
         */
        private final E value;
        /**
         * Sucesor del nodo actual.
         */
        private Node<E> next;
        /**
         * Booleano que indica si el nodo actual tiene un sucesor.
         */
        private boolean hasNext;

        /**
         * Constructor de la clase.
         *
         * @param value Valor que almacenará el nodo.
         */
        public Node(E value) {
            this.value = value;
            hasNext = false;
        }

        /**
         * Método que permite modificar el sucesor del nodo actual.
         *
         * @param next Nuevo sucesor del nodo actual.
         */
        public void setNext(Node<E> next) {
            this.next = next;
            this.hasNext = true;
        }

        /**
         * Método que permite obtener el valor del nodo.
         *
         * @return Valor del nodo.
         */
        public Node<E> getNext() {
            if (!this.hasNext) {
                throw new NullPointerException("El nodo actual no tiene un sucesor.");
            }
            return next;
        }

        /**
         * Método que permite obtener el sucesor del nodo actual.
         *
         * @return Sucesor del nodo actual.
         */
        public E getValue() {
            return this.value;
        }

        /**
         * Método que permite saber si el nodo actual tiene un sucesor.
         *
         * @return Booleano que indica si el nodo actual tiene un sucesor.
         */
        public boolean hasNext() {
            return this.hasNext;
        }

        /**
         * Método que permite obtener el valor del nodo en formato String.
         *
         * @return Valor del nodo en formato String.
         */
        @Override
        public String toString() {
            return "" + this.value;
        }
    }

    /**
     * Constructor de la clase.
     */
    public SimpleLinkedList() {
        this.size = 0;
        head = null;
    }


    /**
     * Método que permite agregar un nuevo nodo a la lista.
     *
     * @param values Valor(es) que almacenará(n) el/los nuevo(s) nodo(s).
     */
    @SafeVarargs
    public final void add(T... values) {
        for (T value : values) {
            handleAdd(this.head, value);
            this.size = this.size + 1;
        }
    }

    /**
     * Método de ayuda para agregar un nuevo nodo a la lista.
     *
     * @param node  Nodo actual.
     * @param value Valor que almacenará el nuevo nodo.
     */
    private void handleAdd(Node<T> node, T value) {
        if (this.size == 0) {
            head = new Node<>(value);
        } else if (node.hasNext()) {
            handleAdd(node.getNext(), value);
        } else {
            node.setNext(new Node<>(value));
        }
    }

    /**
     * Método para convertir la lista en un String.
     *
     * @return Lista en formato String.
     * @throws NullPointerException Si la lista no tiene nodos.
     */
    public String toString() throws NullPointerException {
        if (this.size == 0) {
            throw new NullPointerException("La lista no tiene nodos.");
        }

        return handleToString(head);
    }

    /**
     * Método de ayuda para convertir la lista en un String.
     *
     * @param node Nodo de origen.
     * @return Lista en formato String.
     */
    private String handleToString(Node<T> node) {
        if (node.hasNext()) {
            return String.format("%s\n%s", node, handleToString(node.getNext()));
        }

        return String.format("%s\n", node);
    }

    /**
     * Método que permite obtener el tamaño de la lista.
     *
     * @return Tamaño de la lista.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Método para eliminar la primera ocurrencia de un valor en la lista.
     *
     * @param value Valor que se desea eliminar.
     * @throws NullPointerException Si el valor que se desea eliminar no existe en la lista.
     */
    public void deleteFirst(T value) throws NullPointerException {
        handleDeleteFirst(head, value);
        this.size = this.size - 1;
    }

    /**
     * Método de ayuda para eliminar la primera ocurrencia de un valor en la lista.
     *
     * @param node  Nodo actual.
     * @param value Valor que se desea eliminar.
     * @throws IllegalArgumentException Si el valor que se desea eliminar no existe en la lista.
     */
    private void handleDeleteFirst(Node<T> node, T value) throws NullPointerException {
        if (node.getValue().equals(value) && node == this.head) {
            if (!node.hasNext()) {
                this.head = null;
            } else {
                this.head = node.getNext();
            }
        } else if (node.getNext().getValue().equals(value)) {
            node.setNext(node.getNext().getNext());
        } else if (node.hasNext()) {
            handleDeleteFirst(node.getNext(), value);
        } else {
            throw new NullPointerException("El valor que desea eliminar no existe en la lista.");
        }
    }
}