import java.util.*;

public class Node{
	private Node left_child;
	private Node right_child;
	private int points;
	public double alpha;
	public double beta;
	
	public Node(){
		this.points=0;
		this.alpha=Double.POSITIVE_INFINITY;
		this.beta=Double.NEGATIVE_INFINITY;
		}
	public Node(int points){
		this.points=points;
		this.alpha=Double.POSITIVE_INFINITY;
		this.beta=Double.NEGATIVE_INFINITY;
	}
	
	public Node left(){
		return left_child;
	}
	public Node right(){
		return right_child;
	}
	public void setLeft(Node n){
		this.left_child=n;
	}
	public void setRight(Node n){
		this.right_child=n;
	}
	
	public int score(){
		return points;
	}
	
}