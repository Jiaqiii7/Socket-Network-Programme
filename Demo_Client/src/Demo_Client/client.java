package Demo_Client;

//IDE : IntelliJ IDEA Ultimate

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class client {
	public static void main(String[] args) throws IOException {

		// D¨¦clarations
		BufferedReader bf;
		InputStreamReader dataIn;
		PrintWriter dataOut;
		Scanner scanner1;
		Scanner scanner2;
		Scanner scanner3;
		Scanner scanner4;
		String str;
		String ID_LIVRE;
		String CHOIX;
		String ID_LIVRE_EMPRUNTER;
		String ID_PERSONNE;

		// Connexion
		try {
			// Cr¨¦er un socket (Se connecter au serveur)
			Socket sock = new Socket("127.0.0.1", 60000);
			System.out.println("[+] Connected.");
			// Modifier timeout par d¨¦faut ¨¤ 30s = 30000 ms
			sock.setSoTimeout((30000));

			// Envoyer le CHOIX apr¨¨s la demande du serveur
			// Introduire le CHOIX ¨¤ l'aide du clavier
			scanner1 = new Scanner(System.in);
			System.out.println("Client - Choix Emprunter ou Renseigner :");
			CHOIX = scanner1.next();
			boolean integerOrNot = CHOIX.matches("Emprunter|Renseigner");

			// V¨¦rifier si le CHOIX correspond ¨¤ import¨¦
			while (integerOrNot == false) {
				System.err.println("ERREUR FORMAT : CHOIX INVALIDE ");
				scanner1 = new Scanner(System.in);
				System.out.println("Client - Veuillez saisir ¨¤ nouveau :");
				CHOIX = scanner1.next();
				integerOrNot = CHOIX.matches("Emprunter|Renseigner");
			}

			switch (CHOIX) {
			case "Emprunter":

				// Envoyer l'ID_PERSONNE apr¨¨s la demande du serveur
				// Introduire l'ID_PERSONNE ¨¤ l'aide du clavier
				scanner2 = new Scanner(System.in);
				System.out.println("Client - Introduisez ID Personne :");
				ID_PERSONNE = scanner2.next();
				boolean integerOrNot_1 = ID_PERSONNE.length() == 10 ? true : false;

				// V¨¦rifier si la longueur de l'ID_PERSONNE introduit est de 10
				while (integerOrNot_1 == false) {
					System.err.println("ERREUR FORMAT : ID_PERSONNE INVALIDE ");
					scanner2 = new Scanner(System.in);
					System.out.println("Client - Veuillez saisir ¨¤ nouveau :");
					ID_PERSONNE = scanner2.next();
					integerOrNot_1 = ID_PERSONNE.length() == 10 ? true : false;
				}

				// Introduire l'ID_LIVRE_EMPRUNTER ¨¤ l'aide du clavier
				scanner3 = new Scanner(System.in);
				System.out.println("Client - Introduisez ID Livre Emprunter :");
				ID_LIVRE_EMPRUNTER = scanner3.next();
				boolean integerOrNot_2 = ID_LIVRE_EMPRUNTER.matches("Livre\\d{5}");

				// V¨¦rifier si l'ID_LIVRE_EMPRUNTER correspond ¨¤ import¨¦
				while (integerOrNot_2 == false) {
					System.err.println("ERREUR FORMAT : ID_LIVRE_EMPRUNTER INVALIDE ");
					scanner3 = new Scanner(System.in);
					System.out.println("Client - Veuillez saisir ¨¤ nouveau :");
					ID_LIVRE_EMPRUNTER = scanner3.next();
					integerOrNot_2 = ID_LIVRE_EMPRUNTER.matches("Livre\\d{5}");
				}

				// Envoyer deux donnees (CHOIX et ID_PERSONNE et ID_LIVRE_EMPRUNTER) au serveur
				// concat¨¦n¨¦es dans un string
				dataOut = new PrintWriter(sock.getOutputStream());
				dataOut.println(CHOIX + "||" + ID_PERSONNE + "||" + ID_LIVRE_EMPRUNTER);
				dataOut.flush();

				// Recevoir une r¨¦ponse du serveur
				dataIn = new InputStreamReader(sock.getInputStream(), "utf-8");
				bf = new BufferedReader((dataIn));
				str = bf.readLine();
				System.out.println(str);
				// Si le message contient ERREUR donc c'est un message d'erreur on arr¨ºte le
				// processus
				if (str.indexOf("ERREUR") != (-1)) {
					return;
				}

				break;

			case "Renseigner":

				// Introduire l'ID_LIVRE ¨¤ l'aide du clavier
				scanner4 = new Scanner(System.in);
				System.out.println("Client - Introduisez ID Livre :");
				ID_LIVRE = scanner4.nextLine();
				boolean integerOrNot_3 = ID_LIVRE.matches("Livre\\d{5}");

				// V¨¦rifier si l'ID_LIVRE correspond ¨¤ import¨¦
				while (integerOrNot_3 == false) {
					System.err.println("ERREUR FORMAT : ID_LIVRE INVALIDE ");
					scanner4 = new Scanner(System.in);
					System.out.println("Client - Veuillez saisir ¨¤ nouveau :");
					ID_LIVRE = scanner4.nextLine();
					integerOrNot_3 = ID_LIVRE.matches("Livre\\d{5}");
				}

				// Envoyer deux donnees (CHOIX et ID_LIVRE) au serveur concat¨¦n¨¦es dans un
				// string
				dataOut = new PrintWriter(sock.getOutputStream());
				dataOut.println(CHOIX + "||" + ID_LIVRE);
				dataOut.flush();

				// Recevoir une r¨¦ponse du serveur
				dataIn = new InputStreamReader(sock.getInputStream(), "utf-8");
				bf = new BufferedReader((dataIn));
				str = bf.readLine();
				System.out.println(str);
				// Si le message contient ERREUR donc c'est un message d'erreur on arr¨ºte le
				// processus
				if (str.indexOf("ERREUR") != (-1)) {
					return;
				}

				break;

			}

			// Fermer la socket (D¨¦connexion)
			System.out.println("Client - Au revoir. Bonne journee !");
			sock.close();

		}
		// Si timeout (temps d¨¦pass¨¦) on affiche l'erreur
		catch (InterruptedIOException e) {
			System.err.println(e);
			System.err.println("Le server a mis du temps !");
		}
		// Si il y'aurait un probl¨¨me de connexion on affiche l'erreur
		catch (IOException e) {
			System.err.println(e);
			System.err.println("Probl¨¨me de connection !");
		}
	}
}
