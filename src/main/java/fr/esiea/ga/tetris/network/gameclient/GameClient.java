package fr.esiea.ga.tetris.network.gameclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GameClient {

	Socket clientSocket;
	
	BufferedReader in;
	PrintWriter out;
	
	ClientReaderThread crt;
	ClientWriterThread cwt;

	final Scanner sc = new Scanner(System.in);
	
	public Socket getClientSocket() {
		return clientSocket;
	}

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public ClientReaderThread getCrt() {
		return crt;
	}

	public void setCrt(ClientReaderThread crt) {
		this.crt = crt;
	}

	public ClientWriterThread getCwt() {
		return cwt;
	}

	public void setCwt(ClientWriterThread cwt) {
		this.cwt = cwt;
	}
	
	
	public GameClient(String adr, int port) {
		try {
			clientSocket = new Socket(adr,port);		// On tente de se connecter au serveur
			System.out.println("System - Connexion etablie");
			
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));	// On recupere les flux d'entree et sortie
			out = new PrintWriter(clientSocket.getOutputStream());
			
			crt = new ClientReaderThread (clientSocket,in);	// On lance les threads de lecture et d'ecriture
			cwt = new ClientWriterThread(clientSocket,out);
			
			new Thread(crt).start();	// On lance le thread d'ecoute
			new Thread(cwt).start();	// On lance le thread d'ecriture
			
		} catch (UnknownHostException e) {
			System.out.println("Cet hote est inconnu");
		} catch (IOException e) {
			System.out.println("Pas de serveur actif a cette adresse et/ou port.");
		}
	}
}
