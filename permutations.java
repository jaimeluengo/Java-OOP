// When adding elements an arraylist check that they are not pointing to the same memory location (static variables or objects)
public class Solution {
    public ArrayList<ArrayList<Integer>> permute(ArrayList<Integer> A) {
        
        HashSet<ArrayList<Integer>> poss = new HashSet<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> yeah = new ArrayList<ArrayList<Integer>>();
        
        poss.add(A);
        yeah.add(A);
        
        for(int k=0; k<yeah.size(); k++){
            // System.out.printf("||%d start|| ",yeah.size());
            // for(ArrayList<Integer> mu: yes) print(mu);
            // System.out.printf("||%d end|| ",yeah.size());
            ArrayList<Integer> lol = (ArrayList<Integer>) yeah.get(k).clone();
            
            for(int i=0;  i<lol.size(); i++){
                for(int j=i+1;  j<lol.size(); j++){
                    int temp = lol.get(i);
                    lol.set(i,lol.get(j));
                    lol.set(j,temp);
                    
                    
                    if(!poss.contains(lol)){
                        yeah.add((ArrayList<Integer>) lol.clone());
                        poss.add((ArrayList<Integer>) lol.clone());
                        // lol.set(0,85);
                    }
                }
            }
        }
        

        return yeah;
    }
    
    public void print(ArrayList<Integer> x){
        System.out.printf("[");
        for(int y:x){
            System.out.printf("%d,", y);
        }
        System.out.printf("]");
    }
    public void swap(int i, int j, ArrayList<Integer> x){
        int temp = x.get(i);
        x.set(i,x.get(j));
        x.set(j,temp);
    }
    
}
