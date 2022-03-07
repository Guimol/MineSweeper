package com.ivory.TesteEstagio.CampoMinado;

public class Checker {
	
	String mat_game[][];
		
	public Checker(String[][] mat_game) {
		this.mat_game = mat_game;
	}
	
	public String[][] checkPlace(int qntd, int l, int c, String[][] mat_prob, int[] spaces, String tipo, int qntd_bombas) {
		int new_l = l, new_c = c, hits = 0;
		
		//System.out.println("Mat_prob[" + l + "][" + c + "] = " + this.mat_game[l][c]);
		//System.out.println("Tipo = " + tipo);
		while(hits != qntd) {
			for(int i = 0; i < spaces.length; i++) {
				switch(String.valueOf(spaces[i])) {
				case "1":
					new_l = l - 1;
					new_c = c - 1;
					break;
				case "2":
					new_l = l - 1;
					new_c = c;
					break;
				case "3":
					new_l = l - 1;
					new_c = c + 1;
					break;
				case "4":
					new_l = l;
					new_c = c - 1;
					break;
				case "5":
					new_l = l;
					new_c = c + 1;
					break;
				case "6":
					new_l = l + 1;
					new_c = c - 1;
					break;
				case "7":
					new_l = l + 1;
					new_c = c;
					break;
				case "8":
					new_l = l + 1;
					new_c = c + 1;
					break;
				}
				if(tipo.equals("bombas")) {
					if(this.mat_game[new_l][new_c].equals("-")) {
						mat_prob[new_l][new_c] = "*";
						hits++;
					}
				}else {
					if(hits < qntd_bombas) {
						if(!(mat_prob[new_l][new_c].equals("*") || mat_prob[new_l][new_c].equals("-"))) {
							mat_prob[new_l][new_c] = "-1";
							hits++;
						}
					}
				}
			}
		}
		return mat_prob;
	}
	
	public int check(int l, int c, String[][] mat_prob, int[] spaces, String tipo) {
		int sum = 0, new_l = l, new_c = c;
		
		for(int i = 0; i < spaces.length; i++) {
			switch(String.valueOf(spaces[i])) {
			case "1":
				new_l = l - 1;
				new_c = c - 1;
				break;
			case "2":
				new_l = l - 1;
				new_c = c;
				break;
			case "3":
				new_l = l - 1;
				new_c = c + 1;
				break;
			case "4":
				new_l = l;
				new_c = c - 1;
				break;
			case "5":
				new_l = l;
				new_c = c + 1;
				break;
			case "6":
				new_l = l + 1;
				new_c = c - 1;
				break;
			case "7":
				new_l = l + 1;
				new_c = c;
				break;
			case "8":
				new_l = l + 1;
				new_c = c + 1;
				break;
			}
			if(tipo.equals("empty")) {
				if(this.mat_game[new_l][new_c].equals("-")) {
					sum++;
				}
			}else {
				if(mat_prob[new_l][new_c].equals("*")) {
					sum++;
				}
			}
				
		}
		return sum;
	}
}
