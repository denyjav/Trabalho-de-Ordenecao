public class Teste {
	public static void main(String[] args) {
		
		int[] a = new int[10];
		for (int i = 0; i < a.length; i++) {
			a[i] = (int)(Math.random() * 10);
		}
		
		System.out.println(a.toString());
		cocktailSort(a);
		System.out.println(a.toString());
		
	}
	
	public static int[] cocktailSort(int[] vetor) {
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
		return vetor;
	}
}