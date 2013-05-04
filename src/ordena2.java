
public class ordena2 {
	public static void main(String[] args) {
		
	}
	
	public static void countingSort(int[] vetor, int maiorValor) {
		int[] vetorContagem = new int[maiorValor];
		
		for (int i = 0; i < vetor.length; i++)
			vetorContagem[vetor[i]] = vetorContagem[vetor[i]] + 1;
		
		for (int i = 1; i < maiorValor; i++)
			vetorContagem[i] = vetorContagem[i] + vetorContagem[i - 1];
		
		int[] vetorAux = new int[vetor.length - 1];
		for (int i = vetor.length; i > 0; i--) {
			vetorContagem[vetor[i]] = vetorContagem[vetor[i]] - 1;
			vetorAux[vetorContagem[vetor[i]]] = vetor[i];
		}
		
		for (int i = 0; i < vetor.length; i++)
			vetor[i] = vetorAux[i];
	}
	
	public static void heapSort(int[] vetor) throws Exception {
		Heap heap = new Heap(vetor, vetor.length, 0);		
		
		for (int i = 0; i < vetor.length; i++)
			insereHBC(heap, vetor[i]);
		for (int i = 0; i < vetor.length; i++)
			vetor[i] = removeMenorHBC(heap);
	}
	
	public static void radixSort(int[] vetor) {
		String[] vetorAux = new String[vetor.length - 1]; 
		for (int i = 0; i < vetor.length; i++) {
			
		}
	}
	
	private static void insereHBC(Heap heap, int x) throws Exception{
		if(heap.ultima == heap.tamanho)
			throw new Exception("HEAP OVERFLOW");

		heap.ultima++;
		int i = heap.ultima;
		while (i > 1 && Math.floor(i/2) > x) {
			heap.vetor[i] = heap.vetor[(int) Math.floor(i/2)];
			i = (int) Math.floor(i/2);
		}
		heap.vetor[i] = x;
	}
	
	private static int removeMenorHBC(Heap heap) throws Exception{
		if (heap.ultima == 0)
			throw new Exception("HEAP UNDERFLOW");

		int valor = heap.vetor[1];
		heap.vetor[1] = heap.vetor[heap.ultima];
		heap.ultima = heap.ultima - 1;
		int i = 1;
		int menor;
		while((2*i <= heap.ultima && heap.vetor[i] > heap.vetor[2*i]) ||
				(2*i < heap.ultima && heap.vetor[i] > heap.vetor[2*i+1]))	{
			menor = 2*i;
			if(2*i < heap.ultima && heap.vetor[2*i+1] <= heap.vetor[2*i])
				menor++;

			int swap = heap.vetor[i];
			heap.vetor[i] = heap.vetor[menor];
			heap.vetor[menor] = swap;
			
			i = menor;
		}

		return valor;
	}
}

class Heap {
	public Heap(int[] vetor, int tamanho, int ultima) {
		this.vetor = vetor;
		this.tamanho = tamanho;
		this.ultima = ultima;
	}

	public int[] vetor;
	public int tamanho, ultima;
}