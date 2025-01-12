import java.util.*;
import java.util.List;

public class Graph<T> {
    // We use Hashmap to store the edges in the graph
    private Map<T, List<T>> map = new HashMap<>();

    // This function adds a new vertex to the graph
    public void addVertex(T s)
    {
        map.put(s, new LinkedList<T>());
    }

    // This function adds the edge
    // between source to destination
    public void addEdge(T source, T destination)
    {

        if (!map.containsKey(source))
            addVertex(source);

        if (!map.containsKey(destination))
            addVertex(destination);
        if(!map.get(source).contains(destination)){
            map.get(source).add(destination);
            map.get(destination).add(source);
        }

    }

    // This function gives the count of vertices
    public int getVertexCount()
    {
       return map.keySet().size();
    }

    // This function gives the count of edges
    public void getEdgesCount(boolean bidirection)
    {
        int count = 0;
        for (T v : map.keySet()) {
            count += map.get(v).size();
        }
        if (bidirection == true) {
            count = count / 2;
        }
        System.out.println("The graph has " + count
                + " edges.");
    }

    // This function gives whether
    // a vertex is present or not.
    public void hasVertex(T s)
    {
        if (map.containsKey(s)) {
            System.out.println("The graph contains " + s
                    + " as a vertex.");
        }
        else {
            System.out.println("The graph does not contain "
                    + s + " as a vertex.");
        }
    }

    // This function gives whether an edge is present or
    // not.
    public boolean hasEdge(T s, T d)
    {
        if (map.get(s).contains(d)) {
            return true;
        }
        else {
           return false;
        }
    }

    public void neighbours(T s)
    {
        if(!map.containsKey(s))
            return;
        System.out.println("The neighbours of "+s+" are");
        for(T w:map.get(s))
            System.out.print(w+",");
    }

    public List<T> sortVertex(){
        List<T> list= new ArrayList<>(map.keySet());
        list.sort(new Comparator<T>() {

            @Override
            public int compare(T o1, T o2) {
                if(map.get(o1).size()<map.get(o2).size()){
                    return 1;
                }
                else if(map.get(o1).size()>map.get(o2).size()){
                    return -1;
                }
                return 0;
            }
        });
        return list;
    }
    public void printNodeColors(){
        for(T v : map.keySet()){
            System.out.println(v.toString());
        }
    }
    public Set<T> getMap() {
        return map.keySet();
    }
    // Prints the adjancency list of each vertex.
    @Override public String toString()
    {
        StringBuilder builder = new StringBuilder();

        for (T v : map.keySet()) {
            builder.append(v.toString() + ": ");
            for (T w : map.get(v)) {
                builder.append(w.toString() + " ");
            }
            builder.append("\n");
        }

        return (builder.toString());
    }
}
