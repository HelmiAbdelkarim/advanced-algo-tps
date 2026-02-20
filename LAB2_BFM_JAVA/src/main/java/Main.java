import java.util.*;

public class Main {


    //EXERCISE 2
    public static Set<Integer> intersection(Set<Integer> setA, Set<Integer> setB) {
        Set<Integer> result = new HashSet<>();
        Set<Integer> smaller = setA.size() < setB.size() ? setA : setB;
        Set<Integer> larger = setA.size() < setB.size() ? setB : setA;

        for (int element : smaller) {
            if (larger.contains(element)) {
                result.add(element);
            }
        }
        return result;
    }

    public static Set<Integer> difference(Set<Integer> setA, Set<Integer> setB) {
        Set<Integer> result = new HashSet<>();
        for (int element : setA) {
            if (!setB.contains(element)) {
                result.add(element);
            }
        }
        return result;
    }

    public static Set<Integer> union(Set<Integer> setA, Set<Integer> setB) {
        Set<Integer> result = new HashSet<>();
        result.addAll(setA);
        result.addAll(setB);
        return result;
    }

    public static float calculateMutualFriendCoeff(Set<Integer> setA, Set<Integer> setB) {
        Set<Integer> intersectSet = intersection(setA, setB);
        Set<Integer> unionSet = union(setA, setB);

        if (unionSet.isEmpty()) {
            return 0;
        }
        return (float) intersectSet.size() / unionSet.size();
    }

    public static Set<Integer> findFriendsOfFriend(int userID, Map<Integer, Set<Integer>> socialGraph) {
        Set<Integer> suggestions = new HashSet<>();
        Set<Integer> myFriends = socialGraph.getOrDefault(userID, new HashSet<>());

        for (int friendID : myFriends) {
            Set<Integer> friendsOfFriend = socialGraph.getOrDefault(friendID, new HashSet<>());
            for (int fofID : friendsOfFriend) {
                if (fofID == userID) continue;
                if (!myFriends.contains(fofID)) {
                    suggestions.add(fofID);
                }
            }
        }
        return suggestions;
    }

    //EXERCISE 4

    public static void main(String [] args){

    }
}
