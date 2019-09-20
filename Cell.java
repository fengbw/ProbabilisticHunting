package probablisticHunting;

public class Cell {
	public int type;
	public double belief;
	public double probForFind;
	public boolean isTarget;
	//public void dim = 50;
	public Cell(int t, boolean i){
		this.type = t;
		this.isTarget = i;
		this.belief = 1.0 / 2500;
		probForFind = 0;
	}
	
	public void cancelTarget() {
		isTarget = false;
	}
	
	public void setTarget() {
		isTarget = true;
	}
	
	public void setBelief(double b) {
		belief = b;
	}
	
	public void setProbFind(double p) {
		probForFind = p;
	}
	
	public double getProForSearch(){
		if(type == 0) return 0.9;
		else if(type == 1) return 0.7;
		else if(type == 2) return 0.3;
		else return 0.1;
	}
}
