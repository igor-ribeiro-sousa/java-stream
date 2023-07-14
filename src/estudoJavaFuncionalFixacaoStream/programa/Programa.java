package estudoJavaFuncionalFixacaoStream.programa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import estudoJavaFuncionalFixacaoStream.entidade.Produto;

public class Programa {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner scan = new Scanner(System.in);

		System.out.print("Digite o caminho do arquivo : ");
		String path = scan.nextLine();

		try (BufferedReader buffer = new BufferedReader(new FileReader(path))) {

			List<Produto> lista = new ArrayList<>();
			String linha = buffer.readLine();

			while (linha != null) {
				String[] filhos = linha.split(",");
				lista.add(new Produto(filhos[0], Double.parseDouble(filhos[1])));
				linha = buffer.readLine();
			}
			lista.forEach(System.out::println);
			
			double media = lista.stream()
					.map(p -> p.getPreco())
					.reduce(0.0, (p1, p2) -> p1 + p2)/lista.size();
			
			System.out.println();
			System.out.println("Media do preço dos produtos da lista :" + String.format("%.2f", media));
			
			Comparator<String> comp = (x, y) -> x.toUpperCase().compareTo(y.toUpperCase());
			
			List<String> nomes = lista.stream()
					.filter(p -> p.getPreco() < media)
					.map(p -> p.getNome())
					.sorted(comp.reversed())
					.collect(Collectors.toList());
			
			nomes.forEach(System.out::println);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			
		} finally {
			scan.close();
		}

	}
}