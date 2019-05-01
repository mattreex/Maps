package bearmaps;

import java.util.*;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<PriorityNode> heap;
    int count;
    HashMap<T, PriorityNode> items;

    public ArrayHeapMinPQ() {
        heap = new ArrayList<>();
        heap.add(0, new PriorityNode(null, 0.0));
        count = 0;  // For convenient mat
        items = new HashMap<>();
    }

    @Override
    public void add(T item, double priority) {
        if(contains(item)){
            throw new IllegalArgumentException();
        }
        PriorityNode pn = new PriorityNode(item, priority);
        if(count == 0){
            heap.add(1, pn);
            count++;
        }else{
            heap.add(count+1, pn);
            count++;
            swim(count);

        }
        items.put(item, pn);

    }

    private void swap(int i, int j){
        Collections.swap(heap, i, j);
    }
    private void swim(int i){
        while(i > 1){
            PriorityNode cur = heap.get(i);
            if((cur.compareTo(heap.get(i/2)) < 0)){
                break;
            }
            swap(i, i/2);
            i = i/2;
        }

    }

    private void sink(int k){
        while (2*k <= size()+1) {
            int j = 2*k;
            if (j < size()+1 && heap.get(k).compareTo(heap.get(j+1)) >0) j++;
            if (heap.get(k).compareTo(heap.get(j))==0) break;
            swap(j, k);
            k = j;
        }
    }

    public PriorityNode getRoot(){
        return heap.get(1);
    }

    @Override
    public boolean contains(T item) {
        return items.containsKey(item);}

    @Override
    public T getSmallest() {
        if(heap.size() == 0){
            throw new NoSuchElementException();
        }
        return getRoot().getItem();
    }

    @Override
    public T removeSmallest() {
        if(heap.size() == 0){
        throw new NoSuchElementException();
    }
        T item = heap.remove(1).item;
        PriorityNode newboss = heap.remove(heap.size());
        heap.add(1, newboss);
        sink(1);
        items.remove(item);
        return item;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public void changePriority(T T, double priority) {
        if(heap.size() == 0){
            throw new NoSuchElementException();
        }
        PriorityNode tochange = items.get(T);
        double prioritysearch = tochange.getPriority();
        double currprior = getRoot().getPriority();
        int left = 0;
        int right = 0;
        if((prioritysearch != currprior)){
            if (currprior < prioritysearch){
                add(T, priority);
                return;
            }
            if (currprior >= prioritysearch){
                left = heapTraverse(prioritysearch, tochange, 2);
               right = heapTraverse(prioritysearch, tochange, 3);
            }
        }
        else{
            tochange.setPriority(priority);
            sink(1);
        }
        if(left > 0){
           PriorityNode target = heap.get(left);
           target.setPriority(priority);
           if(priority > heap.get(left/2).getPriority()){
               swim(left);
           }else if(priority  < heap.get(left/2).getPriority()){
               sink(left);
            }
        }else if(right > 0){
            PriorityNode target = heap.get(left);
            target.setPriority(priority);
            if(priority > heap.get(left/2).getPriority()){
                swim(right);
            }else if(priority  < heap.get(left/2).getPriority()){
                sink(right);
            }
        }
    }

    private int heapTraverse(double priority, PriorityNode node, int index){
        if(index > heap.size()){
            return -1;
        }
        PriorityNode curr = heap.get(index);
        double currprior = curr.getPriority();
        if(currprior == priority && node.equals(curr)){
            return index;
        } else if(currprior > priority){
              return -1;
        }else{
            heapTraverse(priority, node, index*2);
            heapTraverse(priority, node, index*2 +1);

        }
        return -1;
    }

    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }
    public static void main(String[] args){
        ArrayHeapMinPQ<String> heap = new ArrayHeapMinPQ<>();
        heap.add("This", 10);
        heap.add("can", 9);
        heap.add("only", 7);
        heap.add("go",100);
        heap.add("one",10);
        heap.add("way",21);
        heap.changePriority("one", 200);
        heap.changePriority("one", 230);
    }

}
