package probablisticHunting;

import java.nio.charset.MalformedInputException;
import java.util.Random;
import java.util.function.IntPredicate;

import javax.naming.directory.SearchControls;
import javax.xml.stream.events.StartDocument;

public class Agent {
	public int dim = 50;
	public Map m;
	public Cell[][] map;
	public  Agent() {
		m = new Map();
		map = m.map;
		//printBelief;
	}
	
	public int searchMovingTarget(int mstep){
		m.resetMap();
		map = m.map;
		int stepCount = 0;
		boolean[][] flag = new boolean[dim][dim];
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++) flag[i][j] = true;
		}
		while(stepCount < mstep){
			stepCount++;
			int[] types = targetMoving();
			Point position = nextMove();
			//Point position = ruleTwoForNext();
			Cell curr = map[position.x][position.y];
			if(checkTarget(curr)){
				//System.out.println("Found target!!!!  at X:" + position.x + " Y:" + position.y);
//				for(int i = 0 ; i < dim; i++){
//					for(int j = 0; j < dim; j++){
//						System.out.print(map[i][j].belief + " ");
//					}
//					System.out.println();
//				}
				return stepCount;
			}
			else{
				//printBelief();
				calBeliefForMovingTarget(types, flag);;
			}
		}
		
		return -1;
	}
	
	public void calBeliefForMovingTarget(int[] types, boolean[][] flag){
		//boolean[][] flag = new boolean[dim][dim];
		
//		for(int i = 0; i < dim; i++){
//			for(int j = 0; j < dim; j++){
//				System.out.print(map[i][j].belief + " ");
//			}
//			System.out.println();
//		}
		
		//if(!flag[0][1]) System.out.println("initial is false");
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++){
				if(map[i][j].type == types[0]){
					//up
					if(i - 1 >= 0 && flag[i - 1][j] && map[i - 1][j].type == types[1])	flag[i][j] = true;
					if(i + 1 < dim && flag[i + 1][j] && map[i + 1][j].type == types[1]) flag[i][j] = true;
					if(j + 1 < dim && flag[i][j + 1] && map[i][j + 1].type == types[1]) flag[i][j] = true;
					if(j - 1 >= 0 && flag[i][j - 1] && map[i][j - 1].type == types[1]) flag[i][j] = true;
				}
				else if(map[i][j].type == types[1]){
					if(i - 1 >= 0 && flag[i - 1][j] && map[i - 1][j].type == types[0])	flag[i][j] = true;
					if(i + 1 < dim && flag[i + 1][j] &&  map[i + 1][j].type == types[0]) flag[i][j] = true;
					if(j + 1 < dim && flag[i][j + 1] &&  map[i][j + 1].type == types[0]) flag[i][j] = true;
					if(j - 1 >= 0 && flag[i][j - 1] &&  map[i][j - 1].type == types[0]) flag[i][j] = true;
				}
				else flag[i][j] = false;
			}
		}
		
		double count = 0.0;
		double proSum = 0.0;
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++){
				if(!flag[i][j]) {
					proSum += map[i][j].belief;
					map[i][j].setBelief(0.0);
				}
				else count++;
			}
		}
		
		double increase = proSum / (1 + count);
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++){
				if(flag[i][j]){
					double newBelief = map[i][j].belief + increase;
					map[i][j].setBelief(newBelief);
				}
			}
		}
	}
	
	public int searchTarget(int mstep){
		int maximumStep = mstep;
		int stepCount = 0;
		while(stepCount < maximumStep){
			stepCount++;
			Point position = (stepCount == 1) ? randomMove() : nextMove();
			Cell curr = map[position.x][position.y];
			if(checkTarget(curr)){
				System.out.println("Found target!!!!  at X:" + position.x + " Y:" + position.y);
				return stepCount;
			}
			else{
				//printBelief();
				calBelief(position.x, position.y);
			}
		}
		//printBelief();
		System.out.println("no found");
		return -1;
	}
	
	public int searchTargetRule2(int mstep){
		int maximumStep = mstep;
		int stepCount = 0;
		while(stepCount < maximumStep){
			stepCount++;
			Point position = (stepCount == 1) ? randomMove() : ruleTwoForNext();
			Cell curr = map[position.x][position.y];
			if(checkTarget(curr)){
				System.out.println("Found target!!!!  at X:" + position.x + " Y:" + position.y);
				return stepCount;
			}
			else{
				//printBelief();
				calBelief(position.x, position.y);
			}
		}
		//printBelief();
		System.out.println("no found");
		return -1;
	}
	
//	public int searchAround(int mstep){
//		int maximumStep = mstep;
//		int stepCount = 0;
//		while(stepCount < maximumStep){
//			stepCount++;
//			Point position = (stepCount == 1) ? randomMove() : ruleTwoForNext();
//			Cell curr = map[position.x][position.y];
//			if(checkTarget(curr)){
//				System.out.println("Found target!!!!  at X:" + position.x + " Y:" + position.y);
//				return stepCount;
//			}
//			else{
//				//printBelief();
//				calBelief(position.x, position.y);
//			}
//		}
//		//printBelief();
//		System.out.println("no found");
//		return -1;
//	}
	
	 
	
	
	
	public Point randomMove(){
		Random r = new Random(); 
		int row = r.nextInt(dim);
		int col = r.nextInt(dim);
		return new Point(row, col);
	}
	
	public void calBelief(int row,  int col){
		Cell curr = map[row][col];
		double currBelief = curr.belief;
		
	
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++){
				if(row != i && col != j){
					double newBelief = map[i][j].belief + (currBelief * curr.getProForSearch() * map[i][j].belief) / (1 - currBelief);
					map[i][j].setBelief(newBelief);
				}
			}
		}
		
		curr.setBelief((1 - curr.getProForSearch()) * currBelief);
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
	
	public void printProForFind() {
		System.out.println("================Belief====================");
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++){
				System.out.print(map[i][j].probForFind + " ");
			}
			System.out.println();
		}
	}
	
	public FoundTarget ruleOne(){
		m.resetMap();
		map = m.map;
		int maximumStep = 50 * 50 * 100;
		int stepCount = 0;
		while(stepCount < maximumStep){
			stepCount++;
			Point position = nextMove();
			Cell curr = map[position.x][position.y];
			if(checkTarget(curr)){
				//System.out.println("Found target!!!!  at X:" + position.x + " Y:" + position.y);
				return new FoundTarget(position.x, position.y, stepCount);
			}
			else{
				//printBelief();
				calBelief(position.x, position.y);
			}
		}
		//printBelief();
		//System.out.println("no found");
		return new FoundTarget(-1, -1, stepCount);
	}
	
	public FoundTarget ruleTwo(){
		m.resetMap();
		map = m.map;
		int maximumStep = 50 * 50 * 100;
		int stepCount = 0;
		while(stepCount < maximumStep){
			stepCount++;
			Point position = (stepCount == 1) ? randomMove() : ruleTwoForNext();
			Cell curr = map[position.x][position.y];
			if(checkTarget(curr)){
				//System.out.println("Found target!!!!  at X:" + position.x + " Y:" + position.y);
				return new FoundTarget(position.x, position.y, stepCount);
			}
			else{
				//printBelief();
				calBelief(position.x, position.y);
			}
		}
		//printBelief();
		//System.out.println("no found");
		return new FoundTarget(-1, -1, stepCount);
	}
	
	public Point ruleTwoForNext(){
		Point position = new Point(0, 0);
		double maximumHope = Double.MIN_VALUE;
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++){
				if(maximumHope < map[i][j].belief){
					map[i][j].setProbFind(map[i][j].belief * map[i][j].getProForSearch());
					if(maximumHope < map[i][j].probForFind) {
						position.setPostion(i, j);
						maximumHope = map[i][j].probForFind;
					}
				}
			}
		}
		
		return position;
	}
	
	public void simulate(int rep){
		int count = 0;
		long[] sumOne = new long[4];
		int[] typesOne = new int[4];
		while(count < rep){
			count++;
			FoundTarget ans = ruleOne();
			typesOne[map[ans.x][ans.y].type]++; 
			sumOne[map[ans.x][ans.y].type] += ans.stepCount;
		}
		System.out.println("========Rule One=======");
		for(int i = 0; i < 4; i++){
			sumOne[i] = sumOne[i] / typesOne[i];
			if(i == 0) System.out.print("Flat :" + sumOne[i] + "  ");
			else if(i == 1) System.out.print("hilly :" + sumOne[i] + "  ");
			else if(i == 2) System.out.print("forest :" + sumOne[i]+ "  ");
			else System.out.print("caves :" + sumOne[i]+ "\n");
		}
		
		count = 0;
		long[] sumTwo = new long[4];
		int[] typesTwo = new int[4];
		while(count < rep){
			count++;
			FoundTarget ans = ruleTwo();
			typesTwo[map[ans.x][ans.y].type]++; 
			sumTwo[map[ans.x][ans.y].type] += ans.stepCount;
		}
		System.out.println("========Rule Two=======");
		for(int i = 0; i < 4; i++){
			sumTwo[i] = sumTwo[i] / typesTwo[i];
			if(i == 0) System.out.print("Flat :" + sumTwo[i] + "  ");
			else if(i == 1) System.out.print("hilly :" + sumTwo[i] + "  ");
			else if(i == 2) System.out.print("forest :" + sumTwo[i]+ "  ");
			else System.out.print("caves :" + sumTwo[i]+ "\n");
		}
	}
	
	public void simulateAround(int rep){
		int count = 0;
		long[] sumOne = new long[4];
		int[] typesOne = new int[4];
		while(count < rep){
			count++;
			FoundTarget ans = searchAround(50 * 50 * 50);
			typesOne[map[ans.x][ans.y].type]++; 
			sumOne[map[ans.x][ans.y].type] += ans.stepCount;
		}
//		System.out.println("h: " + sumOne[0] + "  times:" + typesOne[0]);
		System.out.println("========Rule One=======");
		for(int i = 0; i < 4; i++){
			sumOne[i] = sumOne[i] / typesOne[i];
			if(i == 0) System.out.print("Flat :" + sumOne[i] + "  ");
			else if(i == 1) System.out.print("hilly :" + sumOne[i] + "  ");
			else if(i == 2) System.out.print("forest :" + sumOne[i]+ "  ");
			else System.out.print("caves :" + sumOne[i]+ "\n");
		}
		
		count = 0;
		long[] sumTwo = new long[4];
		int[] typesTwo = new int[4];
		while(count < rep){
			count++;
			FoundTarget ans = searchAroundRuleTwo(50 * 50 * 50);
			typesTwo[map[ans.x][ans.y].type]++; 
			sumTwo[map[ans.x][ans.y].type] += ans.stepCount;
		}
		System.out.println("========Rule Two=======");
		for(int i = 0; i < 4; i++){
			sumTwo[i] = sumTwo[i] / typesTwo[i];
			if(i == 0) System.out.print("Flat :" + sumTwo[i] + "  ");
			else if(i == 1) System.out.print("hilly :" + sumTwo[i] + "  ");
			else if(i == 2) System.out.print("forest :" + sumTwo[i]+ "  ");
			else System.out.print("caves :" + sumTwo[i]+ "\n");
		}
	}
	
	public Point nextMove(){
		//int[] position = {0,0};
		Point position = new Point(0, 0);
		double maximumHope = map[0][0].belief;
		//Cell maxCell = map[0][0];
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++){
				if(maximumHope < map[i][j].belief){
					maximumHope = map[i][j].belief;
					position.setPostion(i, j);
				}
			}
		}
		return position;
	}
	
	
	
	public FoundTarget searchAround(int mstep){
		m.resetMap();
		map = m.map;
		int maximumStep = mstep;
		int stepCount = 0;
		Point currentPoint = new Point(0, 0);
		while(stepCount < maximumStep){
			stepCount++;
			currentPoint = neighborMoveRuleOne(currentPoint);
			Cell curr = map[currentPoint.x][currentPoint.y];
			if(checkTarget(curr)){
				//System.out.println("Found target!!!!  at X:" + currentPoint.x + " Y:" + currentPoint.y);
				return new FoundTarget(currentPoint.x, currentPoint.y, stepCount);
			}
			else{
				//printBelief();
				calBelief(currentPoint.x, currentPoint.y);
			}
		}
		//printBelief();
		//System.out.println("no found");
		return new FoundTarget(-1, -1, stepCount);
	}
	
	public FoundTarget searchAroundRuleTwo(int mstep){
		m.resetMap();
		map = m.map;
		int maximumStep = mstep;
		int stepCount = 0;
		Point currentPoint = new Point(0, 0);
		while(stepCount < maximumStep){
			stepCount++;
			currentPoint = neighborMoveRuleTwo(currentPoint);
			Cell curr = map[currentPoint.x][currentPoint.y];
			if(checkTarget(curr)){
				//System.out.println("Found target!!!!  at X:" + currentPoint.x + " Y:" + currentPoint.y);
				return new FoundTarget(currentPoint.x, currentPoint.y, stepCount);
				
			}
			else{
				//printBelief();
				calBelief(currentPoint.x, currentPoint.y);
			}
		}
		//printBelief();
		//›System.out.println("no found");
		return new FoundTarget(-1, -1, stepCount);
	}
	
	public Point neighborMoveRuleOne(Point curr){
		// Like what we did in AStar, use h = cost + belief  
		Point position = new Point(0, 0);
		int currX = curr.x;
		int currY = curr.y;
		double offset = 1.0;
		double maximumHope = Double.MIN_VALUE;
		double expect = Double.MIN_VALUE;
		int maxX = 0;
		int maxY = 0;
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++){
				//double h = map[i][j].belief * map[i][j].getProForSearch() / (offset);
				if(maximumHope < map[i][j].belief){
					maximumHope = map[i][j].belief;
					maxX = i;
					maxY = j;
				}
			}
		}
		
		//current
		if(expect < map[currX][currY].belief) {
			expect = map[currX][currY].belief ;
			maxX = currX;
			maxY = currY;
			position.setPostion(currX, currY);
		}
		
		//up
		if(currX - 1 >= 0){
			double h = map[currX - 1][currY].belief / (offset + manhattan(currX - 1, currY, maxX, maxY));
			if(expect < h){
				expect = h;
				position.setPostion(currX - 1, currY);
			}
		}
		
		//left
		if(currY - 1 >= 0){
			double h = map[currX][currY - 1].belief  / (offset + manhattan(currX, currY - 1, maxX, maxY));
			if(expect < h){
				expect = h;
				position.setPostion(currX, currY - 1);
			}
		}
		
		//down
		if(currX + 1 < dim){
			double h = map[currX + 1][currY].belief / (offset + manhattan(currX + 1, currY, maxX, maxY));
			if(expect < h){
				expect = h;
				position.setPostion(currX + 1, currY);
			}
		}
		
		//right
		if(currY + 1 < dim){
			double h = map[currX][currY + 1].belief / (offset + manhattan(currX, currY + 1, maxX, maxY));
			if(expect < h){
				expect = h;
				position.setPostion(currX, currY + 1);
			}
		}
		
		
		return position;
	}
	
	
	public Point neighborMoveRuleTwo(Point curr){
		// Like what we did in AStar, use h = cost + belief  
		
		Point position = new Point(0, 0);
		int currX = curr.x;
		int currY = curr.y;
		double offset = 1.0;
		double maximumHope = Double.MIN_VALUE;
		double expect = Double.MIN_VALUE;
		int maxX = 0;
		int maxY = 0;
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++){
				map[i][j].setProbFind(map[i][j].belief * map[i][j].getProForSearch());
				if(maximumHope < map[i][j].probForFind){
					maximumHope = map[i][j].probForFind;
					maxX = i;
					maxY = j;
				}
			}
		}
		
		//current
		if(expect < map[currX][currY].belief * map[currX][currY].getProForSearch()) {
			expect = map[currX][currY].belief * map[currX][currY].getProForSearch();
			maxX = currX;
			maxY = currY;
			position.setPostion(currX, currY);
		}
		
		//up
		if(currX - 1 >= 0){
			double h = map[currX - 1][currY].belief * map[currX - 1][currY].getProForSearch() / (offset + manhattan(currX - 1, currY, maxX, maxY));
			if(expect < h){
				expect = h;
				position.setPostion(currX - 1, currY);
			}
		}
		
		//left
		if(currY - 1 >= 0){
			double h = map[currX][currY - 1].belief * map[currX][currY - 1].getProForSearch() / (offset + manhattan(currX, currY - 1, maxX, maxY));
			if(expect < h){
				expect = h;
				position.setPostion(currX, currY - 1);
			}
		}
		
		//down
		if(currX + 1 < dim){
			double h = map[currX + 1][currY].belief * map[currX + 1][currY].getProForSearch() / (offset + manhattan(currX + 1, currY, maxX, maxY));
			if(expect < h){
				expect = h;
				position.setPostion(currX + 1, currY);
			}
		}
		
		//right
		if(currY + 1 < dim){
			double h = map[currX][currY + 1].belief * map[currX][currY + 1].getProForSearch() / (offset + manhattan(currX, currY + 1, maxX, maxY));
			if(expect < h){
				expect = h;
				position.setPostion(currX, currY + 1);
			}
		}
		
		
		return position;
	}
	
	
	
	public int[] targetMoving(){
		int[] position = new int[2];
		int preX = 0;
		int preY = 0;
		
		// find the target!
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++){
				if(map[i][j].isTarget){
					position[0] = map[i][j].type;
					preX = i;
					preY = j;
					break;
				}
			}
		}
		
		//randomly move
		Random r = new Random();
		int row = dim;
		int col = dim;
		while(row < 0 || col < 0 || row >= dim || col >= dim){
			double random = r.nextDouble();
			//System.out.println("random: " + random);
			row = preX;
			col = preY;
			if(random < 0.25){
				row = row + 1;
			}
			else if(random >= 0.25 && random < 0.5){
				row = row - 1;
			}
			else if(random >= 0.5 && random < 0.75){
				col = col + 1;
			}
			else {
				col = col - 1;
			}
		}
		map[preX][preY].cancelTarget();
		map[row][col].setTarget();
		position[1] = map[row][col].type;
//		System.out.println("PrevX: " + preX + "  PrevY:" + preY );
//		System.out.println("CurrX: " + row + "  CurrY:" + col );
		return position;
	}
	
	public double manhattan(int x, int y, int currX, int currY){
		return Math.abs(x - currX) + Math.abs(y - currY);
	}
	
	public boolean checkTarget(Cell cell){
		return cell.isTarget;
	}
}

class FoundTarget{
	public int x;
	public int y;
	public int stepCount;
	
	public FoundTarget(int x, int y, int step){
		this.x = x;
		this.y = y;
		this.stepCount = step;
	}
}
