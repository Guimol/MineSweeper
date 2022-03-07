package com.ivory.TesteEstagio.CampoMinado;

public class Program {
	
	public void executar() {
		
		CampoMinado campoMinado = new CampoMinado();
		
		System.out.println("Início do jogo\n=======");
		System.out.println(campoMinado.Tabuleiro());
		
		String[][] mat_game = to_matrix(campoMinado.Tabuleiro());
		String[][] mat_prob = probability_matrix(mat_game);
		
		campoMinado = solution(mat_game, mat_prob, campoMinado);
		
		System.out.println("Final do jogo\n=======");
		System.out.println(campoMinado.Tabuleiro());
		
		System.out.println("\nStatus: " + campoMinado.JogoStatus());		
	}
	
	private String[][] to_matrix(String tabuleiro) {
		String mat[][] = new String[9][9];
		String linhas[];
		
		linhas = tabuleiro.split("\n");
		
		for(int l = 0; l < 9; l++) {
				mat[l] = linhas[l].split("");
		}
		
		return mat;
	}
	
	private String[][] probability_matrix(String[][] tabuleiro) {
			
			String temp_matrix[][] = new String[9][9];
			
			for(int l = 0; l < 9; l++) {
				for(int c = 0; c < 9; c++) {
					switch(tabuleiro[l][c]) {
						case "-":
							temp_matrix[l][c] = "0";
							break;
						default:
							temp_matrix[l][c] = "-";
							break;						
					}
				}
			
			}
		return temp_matrix;
	}

	private CampoMinado solution(String[][] mat_game, String[][] mat_prob, CampoMinado campoMinado) {
		int qntd_vazios;
		boolean achei_verde = false;
		
		while(!campoMinado.JogoStatus().toString().equals("Vitoria")) {
			int l, c;
			
			//procura bombas
			for(l = 0; l < 9; l++) {
				for(c = 0; c < 9; c++) {
					if(!(mat_game[l][c].equals("-") || mat_game[l][c].equals("0"))) {
						qntd_vazios = count(mat_game, mat_prob,l, c, "empty");
						if(qntd_vazios == Integer.valueOf(mat_game[l][c])) {
							mat_prob = procura(l, c, mat_game, mat_prob, qntd_vazios, "bombas");
						}
					}
				}
			}
			
			//procura verdes
			if(!achei_verde) {
				for(l = 0; l < 9; l++) {
					for(c = 0; c < 9; c++) {
						if(!(mat_game[l][c].equals("-") || mat_game[l][c].equals("0"))) {
							qntd_vazios = count(mat_game, mat_prob, l, c, "empty");
							if(qntd_vazios - 1 == Integer.valueOf(mat_game[l][c])) {
								mat_prob = procura(l, c, mat_game, mat_prob, qntd_vazios - Integer.valueOf(mat_game[l][c]), "verdes");
								achei_verde = true;
							}
						}
					}
				}
			}else {
				//acessa verdes
				for(l = 0; l < 9; l++) {
					for(c = 0; c < 9; c++) {
						if(mat_prob[l][c].equals("-1")) {
							campoMinado.Abrir(l, c);
							mat_game = to_matrix(campoMinado.Tabuleiro());
						}
					}
				}
				achei_verde = false;
			}
			
			//TestePrint(mat_game);
			//TestePrint(mat_prob);
			
			if(campoMinado.JogoStatus().equals("GameOver")) {
				System.out.println("GAME OVER");
				System.exit(0);
			}
						
		}
		
		return campoMinado;
	}
	
	public int count(String[][] mat_game, String[][] mat_prob, int l, int c, String tipo) {
		
		Checker ch = new Checker(mat_game);
		int sum;
		
		if(l == 0) {
			if(c != 0 && c != 8) {
				sum = ch.check(l, c, mat_prob, new int[] {4, 5, 6, 7, 8}, tipo);
			}else {
				if(c == 0) {
					sum = ch.check(l, c, mat_prob, new int[] {5, 7, 8}, tipo);
				}else {
					sum = ch.check(l, c, mat_prob, new int[] {4, 6, 7}, tipo);
				}
			}
		}else {
			if(l == 8) {
				if(c != 0 && c != 8) {
					sum = ch.check(l, c, mat_prob, new int[] {1, 2, 3, 4, 5}, tipo);
				}else {
					if(c == 0) {
						sum = ch.check(l, c, mat_prob, new int[] {2, 3, 5}, tipo);
					}else {
						sum = ch.check(l, c, mat_prob, new int[] {1, 2, 4}, tipo);
					}
				}
			}else {
				if(c == 0) {
					sum = ch.check(l, c, mat_prob, new int[] {2, 3, 5, 7, 8}, tipo);
				}else {
					if(c == 8) {
						sum = ch.check(l, c, mat_prob, new int[] {1, 2, 4, 6, 7}, tipo);
					}else {
						sum = ch.check(l, c, mat_prob, new int[] {1, 2, 3, 4, 5, 6, 7, 8}, tipo);
					}
				}
			}
		}
		
		return sum;
	}
	
public String[][] procura(int l, int c, String[][] mat_game, String[][] mat_prob, int target, String tipo) {
		Checker ch = new Checker(mat_game);
		
		int qntd_bombas;
		
		if(tipo.equals("bombas")) {
			qntd_bombas = 0;
		}else {
			qntd_bombas = count(mat_game, mat_prob, l, c, "bombas");
		}
	
		if(l == 0) {
			if(c != 0 && c != 8) {
				mat_prob = ch.checkPlace(target, l, c, mat_prob, new int[] {4, 5, 6, 7, 8}, tipo, qntd_bombas);
			}else {
				if(c == 0) {
					mat_prob = ch.checkPlace(target, l, c, mat_prob, new int[] {5, 7, 8}, tipo, qntd_bombas);
				}else {
					mat_prob = ch.checkPlace(target, l, c, mat_prob, new int[] {4, 6, 7}, tipo, qntd_bombas);
				}
			}
		}else {
			if(l == 8) {
				if(c != 0 && c != 8) {
					mat_prob = ch.checkPlace(target, l, c, mat_prob, new int[] {1, 2, 3, 4, 5}, tipo, qntd_bombas);
				}else {
					if(c == 0) {
						mat_prob = ch.checkPlace(target, l, c, mat_prob, new int[] {2, 3, 5}, tipo, qntd_bombas);
					}else {
						mat_prob = ch.checkPlace(target, l, c, mat_prob, new int[] {1, 2, 4}, tipo, qntd_bombas);
					}
				}
			}else {
				if(c == 0) {
					mat_prob = ch.checkPlace(target, l, c, mat_prob, new int[] {2, 3, 5, 7, 8}, tipo, qntd_bombas);
				}else {
					if(c == 8) {
						mat_prob = ch.checkPlace(target, l, c, mat_prob, new int[] {1, 2, 4, 6, 7}, tipo, qntd_bombas);
					}else {
						mat_prob = ch.checkPlace(target, l, c, mat_prob, new int[] {1, 2, 3, 4, 5, 6, 7, 8}, tipo, qntd_bombas);
					}
				}
			}
		}
		
		return mat_prob;
	}

	public void TestePrint(String[][] mat) {
		System.out.println("\nTeste\n");
		
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				System.out.print(mat[i][j]);
			}
			System.out.println();
		}
	}
}
