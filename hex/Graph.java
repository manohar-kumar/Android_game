
import java.util.*;


public class Graph {
	private int V;
	private int E;
	private boolean[][] mat_a;
	private LinkedList<Integer>[] adj_a;
	private LinkedList<Integer>[] adj_b;
	private boolean[][] mat_b;
	private boolean[][] dangerous_a;
	private boolean[][] dangerous_b;
	
	public Graph(int V){
		if (V<=1) throw new RuntimeException("Number of vertices must be > 1");
		this.V=V;
		this.E=0;
		this.mat_a=new boolean[V][V];
		this.mat_b=new boolean[V][V];
		this.dangerous_a=new boolean[V][V];
		this.dangerous_b=new boolean[V][V];
		adj_a = (LinkedList<Integer>[]) new LinkedList[V];
		adj_b = (LinkedList<Integer>[]) new LinkedList[V];
		for (int v=0;v<V;v++){
			adj_a[v]=new LinkedList<Integer>();
			adj_b[v]=new LinkedList<Integer>();
			}
		}
	
	public LinkedList<Pair<Integer, Integer>> validEdges(){
		LinkedList<Pair<Integer, Integer>> q=new LinkedList<Pair<Integer,Integer>>();
		for (int i=0;i<V;i++){
			for (int j=i+1;j<V;j++){
				if (!(mat_a[i][j]||mat_b[i][j])){
					q.add(new Pair<Integer,Integer>(i,j));
				}
			}
		}
		return q;
		}
	public void addEdgeA(int u,int v){
		if (u < 0 || v >= V) throw new IndexOutOfBoundsException();
        if (u < 0 || v >= V) throw new IndexOutOfBoundsException();
        if (dangerous_a[u][v]) 
        	throw new IndexOutOfBoundsException("triangle formed");
        if (mat_a[u][v]||mat_b[u][v]) 
        	throw new IndexOutOfBoundsException("edge already present");
        else {
        E++;
        adj_a[u].add(v);
        adj_a[v].add(u);
        mat_a[u][v]=true;
        mat_a[v][u]=true;
        if (adj_a[u].size()>=2){
        	for (int k=0;k<adj_a[u].size()-1;k++){
        		dangerous_a[adj_a[u].get(k)][v]=true;
        		dangerous_a[v][adj_a[u].get(k)]=true;
        	}
        }
        }
       }
	public void addEdgeB(int u,int v){
		if (u < 0 || v >= V) throw new IndexOutOfBoundsException();
        if (u < 0 || v >= V) throw new IndexOutOfBoundsException();
        if (dangerous_b[u][v]) 
        	throw new IndexOutOfBoundsException("triangle formed");
        if (mat_a[u][v]||mat_b[u][v]) 
        	throw new IndexOutOfBoundsException("edge already present");
        else {
        E++;
        adj_b[u].add(v);
        adj_b[v].add(u);
        mat_b[u][v]=true;
        mat_b[v][u]=true;
        if (adj_b[u].size()>=2){
        	for (int k=0;k<adj_b[u].size()-1;k++){
        		dangerous_b[adj_b[u].get(k)][v]=true;
        		dangerous_b[v][adj_b[u].get(k)]=true;
        	}
        }
        }
       }
	public int V(){
		return V;
	}
	public int E(){
		return E;
		}
	public LinkedList<Integer> adj_a(int v) {
        if (v < 0 || v >= V) throw new IndexOutOfBoundsException();
        return adj_a[v];
    }
	public LinkedList<Integer> adj_b(int v) {
        if (v < 0 || v >= V) throw new IndexOutOfBoundsException();
        return adj_b[v];
    }
	public Pair<Integer,Integer> FirstA(){
		
		int rand1=randInt(0,V-1);
		int rand2=randInt(0, V-1);
		while(rand2==rand1){
			rand2=randInt(0, V-1);
		}
		Pair<Integer,Integer> p=new Pair<Integer,Integer>(rand1,rand2);
		this.addEdgeA(rand1, rand2);
		return p;
		}
	
public Pair<Integer,Integer> SecondB(){
		
		int rand1=randInt(0,V-1);
		int rand2=randInt(0, V-1);
		
		while(rand2==rand1||!adj_a[rand1].isEmpty()||!adj_a[rand2].isEmpty()){
			rand1=randInt(0, V-1);
			rand2=randInt(0, V-1);
		}
		Pair<Integer,Integer> p=new Pair<Integer,Integer>(rand1,rand2);
		this.addEdgeB(rand1, rand2);
		return p;
		}
	
	
	public Pair<Integer,Integer> Amove(){
		LinkedList<Pair<Integer, Integer>> valid=new LinkedList<Pair<Integer,Integer>>();
		valid=this.validEdges();
		LinkedList<Integer> temp_index=new LinkedList<Integer>();
		LinkedList<Integer> temp_index_two=new LinkedList<Integer>();
		int[] count=new int[valid.size()];
		for (int i=0;i<count.length;i++) count[i]=0;
		if (valid.size()==1) return valid.get(0);
		int u,v;
		
		//ruin a safe move
		for (int i=0;i<valid.size();i++){
			u=valid.get(i).getL();
			v=valid.get(i).getR();
			if (!dangerous_b[u][v]){                             
				temp_index.add(i);
			}
		}
		if (temp_index.isEmpty()) {
			for (int j=0;j<valid.size();j++) temp_index.add(j);
		}
		if (temp_index.size()==1) return valid.get(temp_index.get(0));
		
		//creating a loser
		
		for (int j=0;j<temp_index.size();j++){
			u=valid.get(temp_index.get(j)).getL();
			v=valid.get(temp_index.get(j)).getR();
			for (int k=0;k<V&&k!=u&&k!=v;k++){
				if ((mat_a[u][k]&&!mat_a[v][k]&&!mat_b[v][k])||(!mat_a[u][k]&&!mat_b[u][k]&&mat_a[v][k])){
					count[j]++;
				}
			}
		}
		int max_count=min(count);
		for (int i=0;i<count.length;i++){
			if (count[i]==max_count) temp_index_two.add(i);
		}
		temp_index.clear();
		if (temp_index_two.size()==0) 
			temp_index_two=(LinkedList<Integer>) temp_index.clone();
		if (temp_index_two.size()==1) return valid.get(temp_index_two.get(0));
		for (int i=0;i<count.length;i++) count[i]=0;
		
		//complete mixed triangle
		
		for (int j=0;j<temp_index_two.size();j++){
			u=valid.get(temp_index_two.get(j)).getL();
			v=valid.get(temp_index_two.get(j)).getR();
			for (int k=0;k<V&&k!=u&&k!=v;k++){
				if ((mat_a[u][k]&&mat_b[v][k])||(mat_b[u][k]&&mat_a[v][k])){
					count[j]++;
				}
			}
		}
		 max_count=max(count);
		for (int i=0;i<count.length;i++){
			if (count[i]==max_count) temp_index.add(i);
		}
		temp_index_two.clear();
		if (temp_index.size()==1) return valid.get(temp_index.get(0));
		if (temp_index.size()==0) 
			temp_index=(LinkedList<Integer>) temp_index_two.clone();
		for (int i=0;i<count.length;i++) count[i]=0;
		
		//create a partial mixed triangle
		
		for (int j=0;j<temp_index.size();j++){
			u=valid.get(temp_index.get(j)).getL();
			v=valid.get(temp_index.get(j)).getR();
			for (int k=0;k<V&&k!=u&&k!=v;k++){
				if ((!mat_a[u][k]&&!mat_a[v][k]&&mat_b[v][k])||(!mat_a[u][k]&&mat_b[u][k]&&!mat_a[v][k])){
					count[j]++;
				}
			}
		}
		 max_count=max(count);
		 temp_index_two.clear();
		for (int i=0;i<count.length;i++){
			if (count[i]==max_count) temp_index_two.add(i);
		}
		if (temp_index_two.size()==1) return valid.get(temp_index_two.get(0));
		int rand=randInt(0, temp_index_two.size()-1);
		
		return valid.get(temp_index_two.get(rand));
				
		}
	
	//moves for b
	
	
	public Pair<Integer,Integer> Bmove(){
		LinkedList<Pair<Integer, Integer>> valid=new LinkedList<Pair<Integer,Integer>>();
		valid=this.validEdges();
		LinkedList<Integer> temp_index=new LinkedList<Integer>();
		LinkedList<Integer> temp_index_two=new LinkedList<Integer>();
		int[] count=new int[valid.size()];
		for (int i=0;i<count.length;i++) count[i]=0;
		if (valid.size()==1) return valid.get(0);
		int u,v;
		
		//ruin a safe move
		for (int i=0;i<valid.size();i++){
			u=valid.get(i).getL();
			v=valid.get(i).getR();
			if (!dangerous_a[u][v]){                             
				temp_index.add(i);
			}
		}
		if (temp_index.isEmpty()) {
			for (int j=0;j<valid.size();j++) temp_index.add(j);
		}
		if (temp_index.size()==1) return valid.get(temp_index.get(0));
		
		//creating a loser
		
		for (int j=0;j<temp_index.size();j++){
			u=valid.get(temp_index.get(j)).getL();
			v=valid.get(temp_index.get(j)).getR();
			for (int k=0;k<V&&k!=u&&k!=v;k++){
				if ((mat_b[u][k]&&!mat_b[v][k]&&!mat_a[v][k])||(!mat_b[u][k]&&!mat_a[u][k]&&mat_b[v][k])){
					count[j]++;
				}
			}
		}
		int max_count=min(count);
		for (int i=0;i<count.length;i++){
			if (count[i]==max_count) temp_index_two.add(i);
		}
		temp_index.clear();
		if (temp_index_two.size()==0) 
			temp_index_two=(LinkedList<Integer>) temp_index.clone();
		if (temp_index_two.size()==1) return valid.get(temp_index_two.get(0));
		for (int i=0;i<count.length;i++) count[i]=0;
		
		//complete mixed triangle
		
		for (int j=0;j<temp_index_two.size();j++){
			u=valid.get(temp_index_two.get(j)).getL();
			v=valid.get(temp_index_two.get(j)).getR();
			for (int k=0;k<V&&k!=u&&k!=v;k++){
				if ((mat_b[u][k]&&mat_a[v][k])||(mat_a[u][k]&&mat_b[v][k])){
					count[j]++;
				}
			}
		}
		 max_count=max(count);
		for (int i=0;i<count.length;i++){
			if (count[i]==max_count) temp_index.add(i);
		}
		temp_index_two.clear();
		if (temp_index.size()==1) return valid.get(temp_index.get(0));
		if (temp_index.size()==0) 
			temp_index=(LinkedList<Integer>) temp_index_two.clone();
		for (int i=0;i<count.length;i++) count[i]=0;
		
		//create a partial mixed triangle
		
		for (int j=0;j<temp_index.size();j++){
			u=valid.get(temp_index.get(j)).getL();
			v=valid.get(temp_index.get(j)).getR();
			for (int k=0;k<V&&k!=u&&k!=v;k++){
				if ((!mat_b[u][k]&&!mat_b[v][k]&&mat_a[v][k])||(!mat_b[u][k]&&mat_a[u][k]&&!mat_b[v][k])){
					count[j]++;
				}
			}
		}
		 max_count=max(count);
		 temp_index_two.clear();
		for (int i=0;i<count.length;i++){
			if (count[i]==max_count) temp_index_two.add(i);
		}
		if (temp_index_two.size()==1) return valid.get(temp_index_two.get(0));
		int rand=randInt(0, temp_index_two.size()-1);
		
		return valid.get(temp_index_two.get(rand));
				
		}
	
	
	public static int max(int[] count){
		int k=count[0];
		for (int i=1;i<count.length;i++){
			if (count[i]>k) k=count[i];
		}
		return k;
	}
	public static int min(int[] count){
		int k=count[0];
		for (int i=1;i<count.length;i++){
			if (count[i]<k) k=count[i];
		}
		return k;
	}
	public static int randInt(int min,int max){
		Random rand=new Random();
		int randomNum=rand.nextInt((max-min)+1)+min;
		return randomNum;
	}
	
	public static void main(String args[]){
		Graph g=new Graph(6);
		Pair<Integer,Integer> p,q=new Pair<Integer,Integer>();
		p=g.FirstA();
		p.print();
		q=g.SecondB();
		q.print();
		p=g.Amove();
		p.print();
	}
}