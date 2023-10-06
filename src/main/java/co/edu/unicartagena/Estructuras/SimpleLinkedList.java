package co.edu.unicartagena.Estructuras;

/**
 * Clase que representa una lista enlazada simple.
 *
 * @param <T> Tipo de dato que almacenará la lista.
 */
public class SimpleLinkedList<T> {
    /**
     * Atributos de la clase.
     * Head: Nodo que representa la cabeza de la lista.
     * Size: Tamaño de la lista.
     */
    protected Node<T> head;
    protected int size;

    /**
     * Clase que representa un nodo de la lista.
     *
     * @param <E> Tipo de dato que almacenará el nodo.
     */
    protected static class Node<E> {
        /**
         * Atributos de la clase.
         * Value: Valor que almacenará el nodo.
         * Next: Nodo sucesor del nodo actual.
         * HasNext: Booleano que indica si el nodo actual tiene un sucesor.
         */
        private E value;
        private Node<E> next;
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
         * Constructor de la clase.
         *
         * @param value Valor que almacenará el nodo.
         * @param next  Nodo sucesor del nodo actual.
         */
        private Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
            hasNext = true;
        }

        /**
         * Método que permite modificar el valor del nodo.
         *
         * @param value Nuevo valor del nodo.
         */
        public void setValue(E value) {
            this.value = value;
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
        private E getValue() {
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
    }

    /**
     * Constructor de la clase.
     *
     * @param value Valor inicial que almacenará el nodo inicial de la lista.
     */
    public SimpleLinkedList(T value) {
        this.head = new Node<>(value);
        this.size = 1;
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
            this.size = this.size+1;
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
     * Método que permite obtener el valor de un nodo de la lista.
     *
     * @param index Posición del nodo en la lista.
     * @return Valor del nodo.
     * @throws IndexOutOfBoundsException Si el índice no se encuentra en la lista.
     */
    public T get(int index) throws IndexOutOfBoundsException, NullPointerException {
        checkIndex(index);
        return handleGet(head, 0, index);
    }

    /**
     * Método de ayuda para obtener el valor de un nodo de la lista.
     *
     * @param node         Nodo actual.
     * @param indexCurrent Posición actual del nodo.
     * @param indexSearch  Posición del nodo que se desea obtener.
     * @return Valor del nodo.
     */
    private T handleGet(Node<T> node, int indexCurrent, int indexSearch) {
        if (indexCurrent != indexSearch) {
            return handleGet(node.getNext(), ++indexCurrent, indexSearch);
        }

        return node.getValue();
    }

    /**
     * Método que permite eliminar un nodo de la lista.
     *
     * @param index Posición del nodo que se desea eliminar.
     * @throws IndexOutOfBoundsException Si el índice no se encuentra en la lista.
     */
    private void checkIndex(int index) throws IndexOutOfBoundsException, NullPointerException {
        if (size==0){
            throw new NullPointerException (
                    "La lista está vacía."
            );
        }else if (index >= this.size) {
            throw new IndexOutOfBoundsException(
                    String.format("No existe un elemento en la posición %d. El tamaño actual de la lista es %d.", index, this.size));
        } else if (index < 0) {
            throw new IndexOutOfBoundsException(
                    String.format("La posición ingresada(%d) no es válida. Asegúrese de ingresar valores positivos.", index)
            );
        }
    }

    /**
     * Método para actualizar el valor de un nodo utilizando su posición en la lista.
     *
     * @param value Nuevo valor del nodo.
     * @param index Posición del nodo que se desea actualizar.
     * @throws IndexOutOfBoundsException Si el índice no se encuentra en la lista.
     */
    public void update(T value, int index) throws IndexOutOfBoundsException, NullPointerException {
        checkIndex(index);
        handleUpdate(head, value, 0, index);
    }

    /**
     * Método de ayuda para actualizar el valor de un nodo utilizando su posición en la lista.
     *
     * @param node         Nodo actual.
     * @param value        Nuevo valor del nodo.
     * @param indexCurrent Posición actual del nodo.
     * @param indexSearch  Posición del nodo que se desea actualizar.
     */
    private void handleUpdate(Node<T> node, T value, int indexCurrent, int indexSearch) {
        if (indexCurrent != indexSearch) {
            handleUpdate(node.getNext(), value, ++indexCurrent, indexSearch);
        }
        node.setValue(value);
    }

    /**
     * Método para actualizar la primera ocurrencia de un valor en la lista.
     *
     * @param oldValue Valor que se desea actualizar.
     * @param newValue Nuevo valor del nodo.
     * @throws IllegalArgumentException Si el valor que se desea actualizar no existe en la lista.
     */
    public void updateFirst(T oldValue, T newValue) throws NullPointerException {
        handleUpdateFirst(head, oldValue, newValue);
    }

    /**
     * Método de ayuda para actualizar la primera ocurrencia de un valor en la lista.
     *
     * @param node     Nodo actual.
     * @param oldValue Valor que se desea actualizar.
     * @param newValue Nuevo valor del nodo.
     * @throws NullPointerException Si el valor que se desea actualizar no existe en la lista.
     */
    private void handleUpdateFirst(Node<T> node, T oldValue, T newValue) throws NullPointerException {
        if (node.getValue().equals(oldValue)) {
            node.setValue(newValue);
        } else if (node.hasNext()) {
            handleUpdateFirst(node.getNext(), oldValue, newValue);
        } else {
            throw new NullPointerException(String.format("El valor que desea actualizar(%s) no existe en la lista.", oldValue));
        }
    }

    /**
     * Método para eliminar la primera ocurrencia de un valor en la lista.
     *
     * @param index Posición del nodo que se desea eliminar.
     * @throws IndexOutOfBoundsException Si el índice no se encuentra en la lista.
     */
    public void delete(int index) throws IndexOutOfBoundsException, NullPointerException {
        checkIndex(index);
        handleDelete(head, 0, index);
        this.size = this.size-1;
    }

    /**
     * Método de ayuda para eliminar la primera ocurrencia de un valor en la lista.
     *
     * @param node         Nodo actual.
     * @param indexCurrent Posición actual del nodo.
     * @param indexSearch  Posición del nodo que se desea eliminar.
     */
    private void handleDelete(Node<T> node, int indexCurrent, int indexSearch) {
        if (indexCurrent != indexSearch) {
            handleDelete(node.getNext(), ++indexCurrent, indexSearch);
        } else if (indexCurrent == 0){
            if (head.hasNext()){
                this.head = node.getNext();
            } else {
                this.head = null;
            }
        } else {
            node.setNext(node.getNext().getNext());
            node.setValue(node.getNext().getValue());
        }
    }

    /**
     * Método para eliminar la primera ocurrencia de un valor en la lista.
     *
     * @param value Valor que se desea eliminar.
     * @throws NullPointerException Si el valor que se desea eliminar no existe en la lista.
     */
    public void deleteFirst(T value) throws NullPointerException {
        handleDeleteFirst(head, value);
        this.size = this.size-1;
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

    /**
     * Método que permite comparar la lista actual con otra lista.
     *
     * @param List Lista a comparar.
     * @return Booleano que indica si las listas son iguales.
     */
    public boolean equals(SimpleLinkedList<T> List) {
        if (List.size == this.size) {
            return handleEquals(this.head, List.head);
        }

        return false;
    }

    /**
     * Método de ayuda que permite comparar la lista actual con otra lista.
     *
     * @param node  Nodo actual.
     * @param node2 Nodo actual de la lista a comparar.
     * @return Booleano que indica si las listas son iguales.
     */
    private boolean handleEquals(Node<T> node, Node<T> node2) {
        if (node.hasNext != node2.hasNext) {
            return false;
        } else if (node.getValue() != node2.getValue()) {
            return false;
        } else if (node.hasNext) {
            return handleEquals(node.getNext(), node2.getNext());
        }

        return true;
    }

    /**
     * Método que permite obtener el primer nodo que coincida con el elemento recibido.
     * @param value Valor que se desea obtener.
     * @return Primera aparición de value.
     * @throws NullPointerException Si el valor que se desea obtener no existe en la lista.
     */
    public T getFirst(T value) throws NullPointerException {
        return handleGetFirst(head, value);
    }

    /**
     * Método de ayuda que permite obtener el primer nodo que coincida con el elemento recibido.
     * @param node Nodo actual.
     * @param value Valor que se desea obtener.
     * @return Primera aparición de value.
     */
    private T handleGetFirst(Node<T> node, T value){
        if (!node.getValue().equals(value)){
            return handleGetFirst(node.getNext(), value);
        }

        return node.getValue();
    }

}