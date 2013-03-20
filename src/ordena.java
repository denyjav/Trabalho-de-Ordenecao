import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Wagner de Sousa
 * Jorge Lima
 * 
 */

public class ordena {
	public static void main(String[] args) throws IOException {
		int tamanhoDoVetor = Integer.parseInt(args[0]);
		int tipoDeOrdenacao = Integer.parseInt(args[1]);
		int[] vetor = new int[tamanhoDoVetor];
		boolean geraArquivoTxt = false;
		
		if(tamanhoDoVetor < 100)
			geraArquivoTxt = true;
		
		String ordenacao = geraLista(vetor, tipoDeOrdenacao);

		int[] vetorCopia = vetor.clone();
		long tempo;

		if(tamanhoDoVetor < 50000){
			System.out.println("Insercao (" + tamanhoDoVetor + " - " + ordenacao + ")");
			tempo = System.nanoTime();
			insercao(vetorCopia);
			System.out.println("Tempo: " + (System.nanoTime() - tempo) / 1000000.0 + " ms \n");
			if(geraArquivoTxt)
				escreveVetorEmUmArquivo("Insercao", vetor, vetorCopia);

			vetorCopia = vetor.clone();
			System.out.println("Selecao (" + tamanhoDoVetor + " - " + ordenacao + ")");
			tempo = System.nanoTime();
			selecao(vetorCopia);
			System.out.println("Tempo: " + (System.nanoTime() - tempo) / 1000000.0 + " ms \n");
			if(geraArquivoTxt)
				escreveVetorEmUmArquivo("Selecao", vetor, vetorCopia);
		}
		
		vetorCopia = vetor.clone();
		System.out.println("CocktailSort (" + tamanhoDoVetor + " - " + ordenacao + ")");
		tempo = System.nanoTime();
		cocktailSort(vetorCopia);
		System.out.println("Tempo: " + (System.nanoTime() - tempo) / 1000000.0 + " ms \n");
		if (geraArquivoTxt)
			escreveVetorEmUmArquivo("CocktailSort", vetor, vetorCopia);
		
		vetorCopia = vetor.clone();
		System.out.println("ShellSort (" + tamanhoDoVetor + " - " + ordenacao + ")");
		tempo = System.nanoTime();
		shellSort(vetorCopia);
		System.out.println("Tempo: " + (System.nanoTime() - tempo) / 1000000.0 + " ms \n");
		if(geraArquivoTxt)
			escreveVetorEmUmArquivo("ShellSort", vetor, vetorCopia);

		vetorCopia = vetor.clone();
		System.out.println("MergeSort (" + tamanhoDoVetor + " - " + ordenacao + ")");
		tempo = System.nanoTime();
		mergeSort(vetorCopia, 0, vetorCopia.length - 1);
		System.out.println("Tempo: " + (System.nanoTime() - tempo) / 1000000.0 + " ms \n");
		if (geraArquivoTxt)
			escreveVetorEmUmArquivo("MergeSort", vetor, vetorCopia);
		
		vetorCopia = vetor.clone();
		System.out.println("QuickSort (" + tamanhoDoVetor + " - " + ordenacao + ")");
		tempo = System.nanoTime();
		quickSort(vetorCopia, 0, vetorCopia.length - 1);
		System.out.println("Tempo: " + (System.nanoTime() - tempo) / 1000000.0 + " ms \n");
		if (geraArquivoTxt)
			escreveVetorEmUmArquivo("QuickSort", vetor, vetorCopia);		
	}

	public static void cocktailSort(int[] vetor) {
		boolean swap = true;
		int i = 0, j = vetor.length - 1;
		int comparacoes = 0, movimentacoes = 0;
		while (i < j && swap) {
			swap = false;
			for (int k = i; k < j; k++) {
				if (vetor[k] > vetor[k + 1]) {
					int temp = vetor[k];
					vetor[k] = vetor[k + 1];
					vetor[k + 1] = temp;
					swap = true;
					
					movimentacoes = movimentacoes + 3;
				}
			}
			j--;
			if (swap) {
				swap = false;
				for (int k = j; k > i; k--) {
					if (vetor[k] < vetor[k - 1]) {
						int temp = vetor[k];
						vetor[k] = vetor[k - 1];
						vetor[k - 1] = temp;
						swap = true;

						movimentacoes = movimentacoes + 3;
					}
				}
			}
			i++;
		}

		System.out.print("Comparacoes: " + comparacoes + "   ");
		System.out.print("Movimentacoes: " + movimentacoes + "   ");
	}

	public static void insercao(int[] vetor) {
		int comparacoes = 0, movimentacoes = 0;

		for (int i = 0; i < vetor.length; i++) {
			int pivo = vetor[i];
			movimentacoes++;

			int j = i - 1;

			while (j >= 0 && vetor[j] > pivo) {
				vetor[j + 1] = vetor[j];
				j--;
				movimentacoes++;
				comparacoes++;
			}
			vetor[j + 1] = pivo;
			movimentacoes++;
		}

		System.out.print("Comparacoes: " + comparacoes + "   ");
		System.out.print("Movimentacoes: " + movimentacoes + "   ");
	}

	public static void selecao(int[] vetor) {
		int comparacoes = 0, movimentacoes = 0;
		
		for (int i = 0; i < vetor.length - 1; i++) {
			int min = i;

			for (int j = i + 1; j < vetor.length; j++) {
				if (vetor[j] < vetor[min]) {
					min = j;
					comparacoes++;
				}
			}
			int swap = vetor[i];
			vetor[i] = vetor[min];
			vetor[min] = swap;
			movimentacoes = movimentacoes + 3;
		}

		System.out.print("Comparacoes: " + comparacoes + "   ");
		System.out.print("Movimentacoes: " + movimentacoes + "   ");
	}

	public static void shellSort(int[] vetor) {
		int hlistas = 1, comparacoes = 0, movimentacoes = 0;

		while (hlistas < vetor.length)
			hlistas = 3 * hlistas + 1;
		do {
			hlistas = (int) Math.floor(hlistas / 3d);
			for (int i = 0; i < vetor.length; i++) {
				int pivo = vetor[i];
				int j = i - hlistas;
				
				movimentacoes++;

				while (j >= 0 && vetor[j] > pivo) {
					vetor[j + hlistas] = vetor[j];
					j = j - hlistas;
					
					movimentacoes++;
					comparacoes++;
				}
				vetor[j + hlistas] = pivo;
				movimentacoes++;
			}
			comparacoes++;
		} while (hlistas > 1);
		
		System.out.print("Comparacoes: " + comparacoes + "   ");
		System.out.print("Movimentacoes: " + movimentacoes + "   ");
	}

	public static void mergeSort(int[] vetor, int inicio, int fim) {
		if (inicio < fim) {
			int meio = (int) Math.floor((inicio + fim) / 2d);
			if (inicio < meio)
				mergeSort(vetor, inicio, meio);
			if (meio + 1 < fim)
				mergeSort(vetor, meio + 1, fim);

			merge(vetor, inicio, meio, fim);
		}
	}

	private static void merge(int[] vetor, int inicio, int meio, int fim) {
		int i = inicio, j = meio + 1, k = 0;
		int aux[] = new int[fim - inicio];

		while (i <= meio && j <= fim) {
			if (vetor[i] <= vetor[j]) {
				aux[k] = vetor[i];
				i++;
			} else {
				aux[k] = vetor[j];
				j++;
			}
			k++;
		}
		if (i <= meio) {
			for (j = meio; j >= i; j--) {
				vetor[fim - meio + j] = vetor[j];
			}
		}
		for (i = 0; i < k; i++) {
			vetor[inicio + i] = aux[i];
		}
	}

	public static void quickSort(int[] vetor, int inicio, int fim) {
		if (inicio < fim) {
			int particao = particao(vetor, inicio, fim);
			if (inicio < particao - 1)
				quickSort(vetor, inicio, particao - 1);
			else if (particao + 1 < fim)
				quickSort(vetor, particao + 1, fim);
		}
	}

	private static int particao(int[] vetor, int inicio, int fim) {
		int pivo = vetor[inicio], i = inicio + 1, j = fim;
		while (i <= j) {
			while (i <= j && vetor[i] <= pivo)
				i++;
			while (vetor[j] > pivo)
				j--;
			if (i <= j) {
				int swap = vetor[i];
				vetor[i] = vetor[j];
				vetor[j] = swap;
				i++;
				j--;
			}
		}
		int troca = vetor[inicio];
		vetor[inicio] = vetor[j];
		vetor[j] = troca;

		return j;
	}

	private static String geraLista(int vetor[], int opcao) {
		if (opcao == 1) {
			for (int i = 0; i < vetor.length; i++) {
				vetor[i] = (int) (Math.random() * vetor.length) * 10; // gerando n�meros aleat�rios de 1 at� o tamanho do vetor
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

	private static void escreveVetorEmUmArquivo(String metodoDeOrdenacao, int[] vetorEmPossivelDesordem, int[] vetorOrdenado) throws IOException {
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