package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**A memory optimized A* algorithm
 * Created by Matthew Clark
 */

public class AStarSolver<Vertex> implements ShortestPathsSolver {

    private final AStarGraph<Vertex> graph;
    private Vertex source;
    private Vertex dest;
    private SolverOutcome result;
    private HashMap<Vertex, Double> distTo = new HashMap<>();
    private ArrayHeapMinPQ<Vertex> fringe = new ArrayHeapMinPQ<>();
    private HashMap<Vertex, WeightedEdge<Vertex>> edgeTo = new HashMap<>(); // answers the question which vertex to ge to this vertex
    private double solutionweight;
    private List<Vertex> solution;
    private ArrayList<Vertex> marked = new ArrayList<>();
    private double timetosolve;
    private int numofstates = 0;


    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {

        graph = input;
        source = start;
        dest = end;
        if (start.equals(end)) {
            solutionweight = 0;
            solution = List.of(start);
            result = SolverOutcome.SOLVED;
            numofstates = 0;
            timetosolve = 0;
            return;
        }
        fringe.add(start, 0.0);
        distTo.put(start, 0.0);
        Stopwatch sw = new Stopwatch();
        while (!fringe.isEmpty()) {
            Vertex src = fringe.removeSmallest();
            numofstates++;
            if (src.equals(dest)) {
                solutionweight = distTo.get(src);
                solution = pathTracer(edgeTo.get(src));
                timetosolve = sw.elapsedTime();
                result = SolverOutcome.SOLVED;
                return;
            }
            marked.add(src);
            List<WeightedEdge<Vertex>> neighbors = graph.neighbors(src);
            for (WeightedEdge<Vertex> e : neighbors) {

                double heur = graph.estimatedDistanceToGoal(e.to(), dest);
                if ((heur == Double.POSITIVE_INFINITY || marked.contains(e.to())) && !e.to().equals(dest)) {
                    continue;
                }
                double distFr = distTo.get(e.from()) + e.weight();
                if (!distTo.containsKey(e.to())) {
                    distTo.put(e.to(), distFr);
                    fringe.add(e.to(), distFr + heur);
                    edgeTo.put(e.to(), e);
                } else if (distTo.get(e.to()) > distFr) {
                    fringe.changePriority(e.to(), heur + distFr);
                    edgeTo.put(e.to(), e);
                    distTo.put(e.to(), distFr);
                }
            }

            if (timeout < sw.elapsedTime()) {
                result = SolverOutcome.TIMEOUT;
                return;
            }
        }
        result = SolverOutcome.UNSOLVABLE;
        solution = List.of();

    }

    private List<Vertex> pathTracer(WeightedEdge<Vertex> e) {
        ArrayList<Vertex> path = new ArrayList<>();
        path.add(e.to());
        path.add(e.from());
        while (!e.from().equals(source)) {
            e = edgeTo.get(e.from());
            path.add(e.from());
        }
        Collections.reverse(path);
        return path;
    }

    @Override
    public SolverOutcome outcome() {
        return result;
    }

    @Override
    public List solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionweight;
    }

    @Override
    public int numStatesExplored() {
        return numofstates;
    }

    @Override
    public double explorationTime() {
        return timetosolve;
    }
}
