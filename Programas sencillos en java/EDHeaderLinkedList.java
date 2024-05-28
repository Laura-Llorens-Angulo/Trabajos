package Practica4;

import java.util.*;

/**
 * Implementación de la interface List<T> usando nodos doblemenete enlazados de
 * forma circular y con nodo cabecera.
 * 
 * La clase admite nulls como elementos de la lista, por lo tanto deberá tratar
 * correctamente su inserción, búsqueda y borrado.
 *
 * @param <T> Tipo básico de la lista
 */
public class EDHeaderLinkedList<T> implements List<T> {

	/**
	 * Definición de nodo
	 */
	private class Node {
		public T data;
		public Node next;
		public Node prev;

		public Node(T element) {
			data = element;
			next = null;
			prev = null;
		}
	}

	// Enlace al nodo cabecera. A él se enlazan el primero y el último
	private Node header;
	// Número de elementos de la clase
	private int size;

	/**
	 * Constructor por defecto
	 */
	public EDHeaderLinkedList() {
		size=0;
		header=new Node(null);
		header.next=header;
		header.prev=header;
	}

	/**
	 * Constructor que copía todos los elementos de otra clase
	 * 
	 * @param c
	 */
	public EDHeaderLinkedList(Collection<T> c) {
		this();
		addAll(c);
	}

	/**
	 * Compara si dos elementos son iguales teniendo en cuenta que uno o ambos
	 * pueden ser null. 
	 *
	 * @return Si ambos elementos son iguales
	 */
	private boolean equalsNull(T one, Object item) {
		if (one == null)
			return one == item;
		else
			return one.equals(item);
	}


	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return header.next == header;
	}

	@Override
	public void clear() {
		header.next = header.prev = header;
		size = 0;
	}

	@Override
	public boolean add(T item) {
		Node elem = new Node(item);
		elem.prev=header.prev;
		elem.next=header;
		header.prev.next=elem;
		header.prev=elem;
		size++;
		return true;
	}

	@Override
	public void add(int index, T element) {
		if(index<0 || index>size)
			throw new IndexOutOfBoundsException();
		Node nuevo = new Node(element);
		Node aux=header.next;
		for(int i=0;i<index;i++)
			aux=aux.next;
		nuevo.prev=aux.prev;
		nuevo.next=aux;
		aux.prev.next = nuevo;
		aux.prev=nuevo;
		size++;
	}

	@Override
	public boolean remove(Object item) {
		boolean borrado = false;
		if(contains(item)){
			remove(indexOf(item));
			borrado=true;
		}
		return borrado;
	}

	@Override
	public T remove(int index) {
		if(index<0 || index>=size)
			throw new IndexOutOfBoundsException();
		Node aux=header.next;
		T viejo;
		for(int i=0;i<index;i++)
			aux=aux.next;
		viejo = aux.data;
		aux.prev.next=aux.next;
		aux.next.prev=aux.prev;
		size--;

		return viejo;
	}


	@Override
	public boolean contains(Object item) {
		boolean contenido=false;
		Node aux = header;
		for(int i=0;i<size;i++){
			aux=aux.next;
			if(equalsNull(aux.data, item))
				contenido=true;
		}
		return contenido;
	}

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
	public T get(int index) {
		if(index<0 || index>=size)
			throw new IndexOutOfBoundsException();
		Node aux = header.next;
		for(int i=0;i<index;i++)
			aux=aux.next;

		return aux.data;
	}

	@Override
	public T set(int index, T element) {
		if(index<0 || index>=size)
			throw new IndexOutOfBoundsException();
		T viejo;
		viejo=remove(index);
		add(index,element);

		return viejo;
	}

	@Override
	public int indexOf(Object item) {
		Node aux = header.next;
		int i = 0;

		while (aux != header) {
			if (equalsNull(aux.data, item))
				return i;
			aux = aux.next;
			i++;
		}

		return -1;
	}

	@Override
	public int lastIndexOf(Object item) {
		Node aux=header;
		int indice=-1;
		for(int i=0;i<size;i++){
			aux=aux.next;
			if(equalsNull(aux.data, item))
				indice=i;
		}
		return indice;
	}

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
    throw new UnsupportedOperationException();
	}

    @Override
	public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

	@Override
	public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean cambios=false;
		Node aux=header;
		int taminicial=size;
		for(int i=0;i<taminicial;i++){
			aux=aux.next;
			if(!c.contains(aux.data)){
				remove(aux.data);
				cambios=true;
			}
		}
		return cambios;
	}
	
	@Override
	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append("[" );
		if (header.next != header) {
			Node aux = header.next;
			str.append(aux.data == null ? "null" : aux.data.toString());
			while (aux.next != header) { 
				aux = aux.next;
				str.append(", ");
				str.append(aux.data == null ? "null" : aux.data.toString());
			}
		}
		str.append("],compar size = ");
		str.append(size);
		
		return str.toString();
	}

	@Override
	public Object[] toArray() {
		Object retVal[] = new Object[size];

		Node aux = header.next;
		int i = 0;
		while (aux != header)
			retVal[i++] = aux.data;
		return retVal;
	}

	@Override
	public <U> U[] toArray(U[] a) {
		U[] retVal;
		if (a.length > size)
			retVal = a;
		else
			retVal = (U[]) new Object[size];

		Node aux = header.next;
		int i = 0;
		while (aux != header)
			retVal[i++] = (U) aux.data;

		return retVal;
	}



}
