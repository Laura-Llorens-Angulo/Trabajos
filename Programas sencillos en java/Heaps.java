package practica7;

public class Heaps {
	public static String toString(int[] heap) {
		StringBuilder s = new StringBuilder();
		int enNivel = 1;
		int finNivel = 1;
		for (int i = 0; i < heap.length; i++) {
			s.append(heap[i]);

			if (i != heap.length -1)
				if (i == finNivel-1) {
					s.append("] [");
					enNivel *= 2;
					finNivel += enNivel;
				} else
					s.append(", ");
		}
		s.append("]");
		return "Heap: [" + s.toString() + " - size: " + heap.length;
	}

	/**
	 * @return -1 if the queue contains a MinHeap, +1 if it is a maxHeap, or = if its empty.
	 */
	public static int typeOfHeap(int [] data) {
		int ret=0;
		if(data.length==0)
			return ret;

		for(int i=1; i<data.length;i++){
			int padre, hijo;
			padre = data[(i-1)/2];
			hijo=data[i];
			if(padre>hijo){								//posible máximo
				if(ret==-1)								//esta desordenado
					return 0;
				ret=1;
			}else{
				if(ret==1)								//esta desordenado
					return 0;
				ret=-1;
			}
		}
		return ret;
	}
	
	/** Sinks the element in the position p, assuming that v is a maxHeap */
	private static void sink(int[] v, int p, int size) {
		int elemento, hijoIzq, hijoDer,pos=p;

		do{
			hijoIzq=0;
			hijoDer=0;
			elemento = v[pos];
			if((pos*2+1)<size)
				hijoIzq=v[pos*2+1];
			if((pos*2+2)<size)
				hijoDer=v[pos*2+2];

			if(hijoDer!=0 && hijoDer>elemento && (hijoIzq == 0 || hijoIzq < hijoDer)){
				v[pos]=hijoDer;
				v[pos*2+2]=elemento;
				pos=pos*2+2;
			}else{
				if(hijoIzq!=0 && hijoIzq>elemento && (hijoDer == 0 || hijoDer < hijoIzq)){
					v[pos]=hijoIzq;
					v[pos*2+1]=elemento;
					pos=pos*2+1;
				}
			}

		}while((hijoDer !=0 && elemento<hijoDer)||(hijoIzq!=0 && elemento<hijoIzq));
	}

	/**C onverts an unsorted vector into a maxHeap.**/
	public static void maxHeapify (int[] v) {
		int a,b;
		for(int i=1; i<= v.length ; i++){
			a=v[0];
			b=v[v.length-i];
			v[0]=b;
			v[v.length-i]=a;
			sink(v,v.length-i,v.length);
		}
	}
	
	/** Sorts the vector v using the heapsort algorithm **/
	static public void heapsort (int [] v) {
		int n = v.length;
		for (int i = 0; i < n-1; i++)
			for (int j = 0; j < n-i-1; j++)
				if (v[j] > v[j+1])
				{
					int temp = v[j];
					v[j] = v[j+1];
					v[j+1] = temp;
				}
	}
}
