class DirectedGraph {
  public static void main(String[] args) {
    //lalala
  }

  public static boolean reach(Integer s, Integer t, boolean[] marked) {
    if(s == t) return true; // Returns asap
    marked[s] = true;
    for(Integer v : adj[s]) {
      if(!marked[v] && reach(v,t,marked)) return true;
    }
    return false;
  }
}
