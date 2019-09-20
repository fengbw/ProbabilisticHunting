package probablisticHunting;

import java.util.Random;



public class Map {
	public Cell[][] map;
	public int dim = 50;
	
	public  Map() {
		resetMap();
		//printBelief();
	}
	
	public void resetMap(){
		map = new Cell[50][50];
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++){
				Random r = new Random(); 
				double random = r.nextDouble();
				if(random < 0.2) {
					// 0 means flat
					map[i][j] = new Cell(0, false);
					continue;
				}
				if(random < 0.5){
					// 1 means hill
					map[i][j] = new Cell(1, false);
					continue;
				}
				if(random < 0.8){
					// 1 means hill
					map[i][j] = new Cell(2, false);
					continue;
				}
				if(random < 1){
					// 1 means hill
					map[i][j] = new Cell(3, false);
					continue;
				}
				
			}
		}
		Random r = new Random(); 
		int row = r.nextInt(dim);
		int col = r.nextInt(dim);
		map[row][col].setTarget();
	}
	
	public void printMap() {
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++){
				System.out.print(map[i][j].type + " ");
			}
			System.out.println();
		}
	}
	
	public void printBelief() {
		System.out.println("================Belief====================");
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++){
				System.out.print(map[i][j].belief + " ");
			}
			System.out.println();
		}
	}
	
}

class Test{
	public static void main(String[] args){
		Agent agent = new Agent();
		int count = 0;
		double sum = 0;
		while(count < 100){
			count++;
			sum += agent.searchMovingTarget(100000);
		}
		System.out.println(sum / count);
		//agent.simulate(100);
		//agent.simulateAround(50);
//		System.out.println(agent.searchAround(100000));
//		System.out.println(agent.searchTarget(100000));
//		System.out.println(agent.searchTargetRule2(100000));	
	}
}
