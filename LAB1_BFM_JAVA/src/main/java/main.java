import java.util.*;

public class main {

    //EXERCISE 1
    public static int integerMirror(int n){
        //Edge case
        if(n == 0) return 0;

        int mirror = 0;
        while(n > 0){
            mirror = n % 10 + mirror * 10;
            n /= 10;
        }
        return mirror;
    }

    //EXERCISE 2
    public static boolean validParentheses(String s){
        Stack<Character> stack = new Stack<>();
        HashMap<Character, Character> map = new HashMap<>();
        map.put('{','}');
        map.put('(',')');
        map.put('[',']');

        int comparisons = 0;

        for(int i = 0; i < s.length(); i++){
            char curr = s.charAt(i);
            comparisons++;
            if(map.containsKey(curr)){
                stack.push(map.get(curr));
            }else {
                comparisons++;
                if(stack.isEmpty()) return false;

                comparisons++;
                if (stack.pop() != curr) return false;
            }
        }
        System.out.println("Number of comparisons: "+comparisons );
        return stack.isEmpty();
    }

    //EXERCISE 3
    public static int[][] mergeIntervals(int[][] arr) {
        // Edge case
        if (arr == null || arr.length <= 1) return arr;


        Arrays.sort(arr, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);

        List<int[]> merged = new ArrayList<>();
        merged.add(arr[0]);

        for (int i = 1; i < arr.length; i++) {
            int[] current = arr[i];
            int[] last = merged.get(merged.size() - 1);

            if (current[0] <= last[1]) {
                last[1] = Math.max(last[1], current[1]);
            } else {
                merged.add(current);
            }
        }

        return merged.toArray(new int[merged.size()][]);
    }

    //EXERCISE 6: APPROACH HASHMAP
    public static int firstUniqChar(String s){
        // Edge cases
        if (s == null || s.isEmpty()) return -1;
        if (s.length() == 1) return 0;

        HashMap <Character, Integer> map = new HashMap<>();
        for(char c : s.toCharArray()){
            map.put(c, map.getOrDefault(c,0)+1);
        }

        for(int i = 0; i < s.length(); i++ ){
            if(map.get(s.charAt(i)) == 1) return i;
        }

        return -1;
    }

    //EXERCISE 6: APPROACH LINKED HASHMAP
    public static int firstUniqCharLinked(String s) {
        // Edge cases
        if (s == null || s.isEmpty()) return -1;
        if (s.length() == 1) return 0;

        LinkedHashMap<Character, Integer> map = new LinkedHashMap<>();

        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                return s.indexOf(entry.getKey());
            }
        }

        return -1;
    }

    public static void main(String [] args){
        int x = 400; // Tested with 315, 7
        System.out.println("Mirror of:"+x +"is:"+integerMirror(x));
        String s = "([)]"; // Tested with "" and ({[]})
        boolean res = validParentheses(s);
        System.out.println("For: "+s+" we got:" + res);


        String sLe = "Leetcode"; //Tested with loveleetcode, aabb, dddcccbba
        System.out.println("The index of the first unique character for " + s + "is: " + firstUniqChar(sLe));

        System.out.println("The index of the first unique character for " + s + "is: " + firstUniqCharLinked(sLe));

    }
}
