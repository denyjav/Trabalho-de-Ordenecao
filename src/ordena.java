import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/*
 * Wagner de Sousa
 * Jorge Luis
 * Eduardo Luiz Braid
 * Paulo Gurgel
 */

public class ordena {
	public static void main(String[] args) throws Exception {
		int tamanhoDoVetor = Integer.parseInt(args[0]);
		int tipoDeOrdenacao = Integer.parseInt(args[1]);
		int[] vetor = new int[tamanhoDoVetor];
		boolean geraArquivoTxt = false;

		if (tamanhoDoVetor < 100)
			geraArquivoTxt = true;

		String ordenacao = geraLista(vetor, tipoDeOrdenacao);

		int[] vetorCopia = vetor.clone();
		long tempo;

		System.out.println("CountingSort (" + tamanhoDoVetor + " - " + ordenacao + ")");
		tempo = System.nanoTime();
		countingSort(vetorCopia);
		System.out.println("Tempo: " + (System.nanoTime() - tempo) / 1000000.0 + " ms \n");
		if (geraArquivoTxt)
			escreveVetorEmUmArquivo("CountingSort", vetor, vetorCopia);

		vetorCopia = vetor.clone();
		System.out.println("HeapSort (" + tamanhoDoVetor + " - " + ordenacao + ")");
		tempo = System.nanoTime();
		heapSort(vetorCopia);
		System.out.println("Tempo: " + (System.nanoTime() - tempo) / 1000000.0 + " ms \n");
		if (geraArquivoTxt)
			escreveVetorEmUmArquivo("HeapSort", vetor, vetorCopia);

		vetorCopia = vetor.clone();
		System.out.println("RadixSort (" + tamanhoDoVetor + " - " + ordenacao + ")");
		tempo = System.nanoTime();
		radixSort(vetorCopia);
		System.out.println("Tempo: " + (System.nanoTime() - tempo) / 1000000.0 + " ms \n");
		if (geraArquivoTxt)
			escreveVetorEmUmArquivo("RadixSort", vetor, vetorCopia);
		
		vetorCopia = vetor.clone();
		System.out.println("BucketSort (" + tamanhoDoVetor + " - " + ordenacao + ")");
		tempo = System.nanoTime();
		bucketSort(vetorCopia);
		System.out.println("Tempo: " + (System.nanoTime() - tempo) / 1000000.0 + " ms \n");
		if (geraArquivoTxt)
			escreveVetorEmUmArquivo("BucketSort", vetor, vetorCopia);
	}

	public static void countingSort(int[] vetor) {
		if (vetor.length == 0)
			return;

		int max = vetor[0], min = vetor[0];
		for (int i = 1; i < vetor.length; i++) {
			if (vetor[i] > max) {
				max = vetor[i];

			} else if (vetor[i] < min) {
				min = vetor[i];
			}
		}
		int numValues = max - min + 1;
		int[] counts = new int[numValues];
		for (int i = 0; i < vetor.length; i++) {
			counts[vetor[i] - min]++;
		}
		int outputPos = 0;
		for (int i = 0; i < numValues; i++) {
			for (int j = 0; j < counts[i]; j++) {
				vetor[outputPos] = i + min;
				outputPos++;
			}
		}
	}

	public static void heapSort(int[] vetor) throws Exception {
		int tamanho = vetor.length + 1; 
		Heap heap = new Heap(new int[tamanho],tamanho , 0);
		for (int i = 0; i < vetor.length; i++)
			insereHBC(heap, vetor[i]);
		for (int i = 0; i < vetor.length; i++)
			vetor[i] = removeMenorHBC(heap);
	}

	public static void radixSort(int[] vetor) {
		int[] vetorAux = new int[vetor.length];
		int[] b_orig = vetorAux;
		int bits = 4;

		int rshift = 0;
		for (int mask = ~(-1 << bits); mask != 0; mask <<= bits, rshift += bits) {
			int[] cntarray = new int[1 << bits];
			for (int p = 0; p < vetor.length; ++p) {
				int key = (vetor[p] & mask) >> rshift;
				++cntarray[key];
			}

			for (int i = 1; i < cntarray.length; ++i)
				cntarray[i] += cntarray[i - 1];

			for (int i = vetor.length - 1; i >= 0; --i) {
				int key = (vetor[i] & mask) >> rshift;
				--cntarray[key];
				vetorAux[cntarray[key]] = vetor[i];
			}

			int[] temp = vetorAux;
			vetorAux = vetor;
			vetor = temp;
		}

		if (vetor == b_orig) {
			System.arraycopy(vetor, 0, vetorAux, 0, vetor.length);
		}
	}

	public static void bucketSort(int[] copia) {
		int numBuckets = copia.length;

		LinkedList[] linkedLists = new LinkedList[numBuckets];

		for (int i = 0; i < numBuckets; i++)
			linkedLists[i] = new LinkedList<Integer>();

		// Coloca os valores no bucket respectivo:
		for (int i = 0; i < copia.length; i++) {
			int j = numBuckets - 1;
			while (true) {
				if (j < 0) {
					break;
				}
				if (copia[i] >= (j * 5)) {
					linkedLists[j].add(copia[i]);
					break;
				}
				j--;

			}
		}

		int indice = 0;
		for (int i = 0; i < numBuckets; i++) {

			int[] aux = new int[linkedLists[i].size()];

			// Coloca cada bucket num vetor:
			for (int j = 0; j < aux.length; j++)
				aux[j] = (Integer) linkedLists[i].get(j);

			countingSort(aux); // algoritmo escolhido para ordenação.

			for (int j = 0; j < aux.length; j++, indice++)
				copia[indice] = aux[j];
		}
	}

	private static void insereHBC(Heap heap, int x) throws Exception {
		if (heap.ultima == heap.tamanho)
			throw new Exception("HEAP OVERFLOW");

		heap.ultima++;
		int i = heap.ultima;
		while (i > 1 && heap.vetor[(int) Math.floor(i / 2)] > x) {
			heap.vetor[i] = heap.vetor[(int) Math.floor(i / 2)];
			i = (int) Math.floor(i / 2);
		}
		heap.vetor[i] = x;
	}

	private static int removeMenorHBC(Heap heap) throws Exception {
		if (heap.ultima == 0)
			throw new Exception("HEAP UNDERFLOW");

		int valor = heap.vetor[1];
		heap.vetor[1] = heap.vetor[heap.ultima];
		heap.ultima = heap.ultima - 1;
		int i = 1;
		int menor;
		while ((2 * i <= heap.ultima && heap.vetor[i] > heap.vetor[2 * i])
				|| (2 * i < heap.ultima && heap.vetor[i] > heap.vetor[2 * i + 1])) {
			menor = 2 * i;
			if (2 * i < heap.ultima
					&& heap.vetor[2 * i + 1] <= heap.vetor[2 * i])
				menor++;

			int swap = heap.vetor[i];
			heap.vetor[i] = heap.vetor[menor];
			heap.vetor[menor] = swap;

			i = menor;
		}

		return valor;
	}

	private static String geraLista(int vetor[], int opcao) {
		if (opcao == 1) {
			for (int i = 0; i < vetor.length; i++) {
				vetor[i] = (int) (Math.random() * vetor.length) * 10; // gerando numeros aleatorios de 1 ate o tamanho do vetor
			}
			return "Aleatoria";
		} else if (opcao == 2) {
			int j = 0;
			for (int i = vetor.length - 1; i > 0; i--) {
				vetor[j] = i * 10; // gerando lista em ordem decrescente
				j++;
			}
			return "Decrescente";
		} else {
			for (int i = 0; i < vetor.length; i++) {
				vetor[i] = i * 10; // gerando lista em ordem crescente
			}
			return "Crescente";
		}
	}

	private static void escreveVetorEmUmArquivo(String metodoDeOrdenacao,
			int[] vetorEmPossivelDesordem, int[] vetorOrdenado)
			throws IOException {
		File saida = new File("saida.txt");
		saida.createNewFile();

		FileWriter fileWriter = new FileWriter(saida, true);

		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

		bufferedWriter.write(metodoDeOrdenacao);
		bufferedWriter.newLine();
		bufferedWriter.write("Antes: ");

		for (int i = 0; i < vetorEmPossivelDesordem.length; i++) {
			bufferedWriter.write(vetorEmPossivelDesordem[i] + " ");
		}

		bufferedWriter.newLine();
		bufferedWriter.write("Depois: ");

		for (int i = 0; i < vetorOrdenado.length; i++) {
			bufferedWriter.write(vetorOrdenado[i] + " ");
		}
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		bufferedWriter.close();
		fileWriter.close();
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