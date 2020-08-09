import java.awt.Window.Type;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.net.ssl.HostnameVerifier;
import javax.swing.text.MaskFormatter;

public class Server {
	private static int turn = 2;
	private static char count2 = 1;
	static int count = 2;
	public static char[][] board = new char[3][3];
	static Scanner scanner = new Scanner(System.in);
	static int dimension;
	static int dimension2;

	public static void main(String[] args) throws UnknownHostException, IOException {
		int choice = 0;
		System.out.println("(1)Host a new game");
		System.out.println("(2)join a host");
		choice = scanner.nextInt();
		if (choice == 1) {
			host();
		} else {
			join();
		}
	}

	public static void host() throws IOException {
		ServerSocket serverSocket = new ServerSocket(9999);
		System.out.println("Server is running");
		Socket clientSocket = serverSocket.accept();
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		board();
		while (true) {

			System.out.println("Enter row between 0-2:");
			dimension = scanner.nextInt();
			System.out.println("Enter column between 0-2:");
			dimension2 = scanner.nextInt();

			board[dimension][dimension2] = 'X';

			turn++;
			board();
			out.println(dimension + " " + dimension2);
			if (winCases()) {
				if (turn % 2 == 1) {
					System.out.println("You win!");
					break;
				}

			}  if (turn == 11) {
				System.out.println(turn);
				System.out.println("Draw!");
				break;
			}

			String msg = in.readLine();

			board[Integer.parseInt(msg.substring(0, 1))][Integer.parseInt(msg.substring(2, 3))] = 'O';
			board();
			if (winCases()) {
				if (turn % 2 == 0) {
					System.out.println("You lost!");
					break;
				}

			}  if (turn == 11) {
				System.out.println("Draw!");
				break;
			}
			turn++;

		}

	}

	public static void join() throws UnknownHostException, IOException {
		System.out.println("Enter the IP adress :");
		String ip = scanner.next();
		Socket clientSocket = new Socket(ip, 9999);
		System.out.println("Connected");
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		board();

		while (true) {

			String msg = in.readLine();
			board[Integer.parseInt(msg.substring(0, 1))][Integer.parseInt(msg.substring(2, 3))] = 'X';
			board();
			turn++;
			if (winCases()) {
				if (turn % 2 == 0) {
					System.out.println("You lost!");
					break;
				}

			} if (turn == 11) {
				System.out.println("Draw!");
				break;
			}
			
			System.out.println("Enter row between 0-2:");
			dimension = scanner.nextInt();
			System.out.println("Enter column between 0-2:");
			dimension2 = scanner.nextInt();
			board[dimension][dimension2] = 'O';

			turn++;
			board();
			out.println(dimension + " " + dimension2);
			if (winCases()) {
				if (turn % 2 == 1) {
					System.out.println("You win!");
					break;
				}

			} if (turn == 11) {
				System.out.println("Draw!");
				break;
			}
			

		}

	}


	public static void board() {

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (turn % 2 == 0) {
					// board[dimension][dimension2] = 'X';
					System.out.print(board[i][j] + " | ");
				} else {
					// board[dimension][dimension2] = 'O';
					System.out.print(board[i][j] + " | ");
				}
			}

			System.out.println();
			System.out.println("-----------");

		}

	}

	public static boolean winCases() {

		if (board[0][0] == board[0][1] && board[0][0] == board[0][2] && board[0][0] != '\0') {
			return true;
		} else if (board[1][0] == board[1][1] && board[1][0] == board[1][2] && board[1][0] != '\0') {
			return true;
		} else if (board[2][0] == board[2][1] && board[2][0] == board[2][2] && board[2][0] != '\0') {
			return true;
		} else if (board[0][0] == board[0][1] && board[0][0] == board[0][2] && board[0][0] != '\0') {
			return true;
		} else if (board[0][0] == board[1][0] && board[0][0] == board[2][0] && board[0][0] != '\0') {
			return true;
		} else if (board[0][1] == board[1][1] && board[0][1] == board[2][1] && board[0][1] != '\0') {
			return true;
		} else if (board[0][2] == board[1][2] && board[0][2] == board[2][2] && board[0][2] != '\0') {
			return true;
		} else if (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] != '\0') {
			return true;
		} else if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != '\0') {
			return true;
		} else {
			return false;
		}

	}

}
