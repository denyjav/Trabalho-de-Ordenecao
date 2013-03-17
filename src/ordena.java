import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ordena {
	public static void main(String[] args) throws IOException {
		int tamanhoDoVetor = Integer.parseInt(args[0]);
		int tipoDeOrdenacao = Integer.parseInt(args[1]);
		int[] vetor = new int[tamanhoDoVetor];

		String ordenacao = geraLista(vetor, tipoDeOrdenacao);

		int[] vetorCopia = vetor.clone();
		System.out.println("Insercao (" + vetorCopia.length + " - " + ordenacao + ")");
		long tempo = System.nanoTime();
		insercao(vetorCopia);
		System.out.println("Tempo: " + (System.nanoTime() - tempo) / 1000000.0 + " ms");

		vetorCopia = vetor.clone();
		System.out.println("Selecao (" + vetorCopia.length + " - " + ordenacao + ")");
		tempo = System.nanoTime();
		selecao(vetorCopia);
		System.out.println("Tempo: " + (System.nanoTime() - tempo) / 1000000.0 + " ms");

		vetorCopia = vetor.clone();
		System.out.println("ShellSort (" + vetorCopia.length + " - " + ordenacao + ")");
		tempo = System.nanoTime();
		shellSort(vetorCopia);
		System.out.println("Tempo: " + (System.nanoTime() - tempo) / 1000000.0 + " ms");

		vetorCopia = vetor.clone();
		System.out.println("MergeSort (" + vetorCopia.length + " - " + ordenacao + ")");
		tempo = System.nanoTime();
		mergeSort(vetorCopia, 0, vetorCopia.length - 1);
		System.out.println("Tempo: " + (System.nanoTime() - tempo) / 1000000.0 + " ms");
	}

	public static void cocktailSort(int[] vetor) {
		boolean swap = true;
		int i = 0;
		int j = vetor.length - 1;
		while (i < j && swap) {
			swap = false;
			for (int k = i; k < j; k++) {
				if (vetor[k] > vetor[k + 1]) {
					int temp = vetor[k];
					vetor[k] = vetor[k + 1];
					vetor[k + 1] = temp;
					swap = true;
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
					}
				}
			}
			i++;
		}
	}

	public static void insercao(int[] vetor) {
		for (int i = 0; i < vetor.length; i++) {
			int pivo = vetor[i];

			int j = i - 1;

			while (j >= 0 && vetor[j] > pivo) {
				vetor[j + 1] = vetor[j];
				j--;
			}
			vetor[j + 1] = pivo;
		}
	}

	public static void selecao(int[] vetor) {
		for (int i = 0; i < vetor.length - 1; i++) {
			int min = i;

			for (int j = i + 1; j < vetor.length; j++) {
				if (vetor[j] < vetor[min])
					min = j;
			}
			int swap = vetor[i];
			vetor[i] = vetor[min];
			vetor[min] = swap;
		}
	}

	public static void shellSort(int[] vetor) {
		int hlistas = 1;

		while (hlistas < vetor.length)
			hlistas = 3 * hlistas + 1;
		do {
			hlistas = (int) Math.floor(hlistas / 3d);
			for (int i = 0; i < vetor.length; i++) {
				int pivo = vetor[i];
				int j = i - hlistas;

				while (j >= 0 && vetor[j] > pivo) {
					vetor[j + hlistas] = vetor[j];
					j = j - hlistas;
				}
				vetor[j + hlistas] = pivo;
			}
		} while (hlistas > 1);
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
		int vetorAux[] = new int[fim - inicio];

		while (i <= meio && j <= fim) {
			if (vetor[i] <= vetor[j]) {
				vetorAux[k] = vetor[i];
				i++;
			} else {
				vetorAux[k] = vetor[j];
				j++;
			}
			k++;
		}
		if (i <= meio) {
			for (j = meio; j >= i; j--)
				vetor[fim - meio + j] = vetor[j];
		}
		for (i = 0; i < k; i++)
			vetor[inicio + i] = vetorAux[i];
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
			return "Aleat�ria";
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

		FileWriter fileWriter = new FileWriter(saida);

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

		bufferedWriter.close();
		fileWriter.close();
	}
}