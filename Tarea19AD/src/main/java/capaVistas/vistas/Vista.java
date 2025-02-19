package capaVistas.vistas;

import java.util.Scanner;

public class Vista implements InterfazVistas {
	private final Scanner abielto = new Scanner(System.in);

	@Override
	public void mostrarMensaje(String mensaje) {
		System.out.println(mensaje + " \n");
	}

	@Override
	public int chuparYGuardarInt(String mensaje) {
		mostrarMensaje(mensaje);
		int numeropedido = abielto.nextInt();
		abielto.nextLine();
		return numeropedido;
	}

	@Override
	public String chuparYGuardarString(String mensaje) {
		mostrarMensaje(mensaje);
		String textoPedido = abielto.nextLine();
		return textoPedido;
	}

}
