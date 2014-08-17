
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
		if (V<0) throw new RuntimeException("Number of vertices must be non-negative");
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
			adj_a[v]=new LinkedList<Integer>();
			}
		}
	public void addEdgeA(int u,int v){
		if (u < 0 || v >= V) throw new IndexOutOfBoundsException();
        if (u < 0 || v >= V) throw new IndexOutOfBoundsException();
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
        		dangerous_b[adj_a[u].get(k)][v]=true;
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
	

}