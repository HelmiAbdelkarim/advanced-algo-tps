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
        Set<Integer> friendList1 = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        Set<Integer> friendList2 = new HashSet<>(Arrays.asList(3, 4, 5, 6));
        Set<Integer> resultIntersection = intersection(friendList1, friendList2);
        Set<Integer> resultUnion = union(friendList1, friendList2);
        System.out.println(calculateMutualFriendCoeff(friendList1,friendList2));
        System.out.println(difference(friendList1,friendList2));
        System.out.println(resultUnion);
        System.out.println(resultIntersection);

        Map<Integer, Set<Integer>> graph = new HashMap<>();
        graph.put(1, new HashSet<>(Arrays.asList(2, 3)));
        graph.put(2, new HashSet<>(Arrays.asList(1, 4, 5)));
        graph.put(3, new HashSet<>(Arrays.asList(1, 6)));
        graph.put(4, new HashSet<>(List.of(2)));
        graph.put(5, new HashSet<>(List.of(2)));
        graph.put(6, new HashSet<>(List.of(3)));
        System.out.println(findFriendsOfFriend(1,graph));

        FollowerMatrix fm = new FollowerMatrix(5);

        // Set up follow relationships
        fm.follow(1, 2);
        fm.follow(2, 1); // mutual with 1-2
        fm.follow(1, 3);
        fm.follow(3, 1); // mutual with 1-3
        fm.follow(2, 3);
        fm.follow(4, 1);
        fm.follow(5, 1);
        fm.follow(1, 5); // mutual with 1-5

        System.out.println("User 1 follows User 2: " + fm.isFollowing(1, 2)); // true
        System.out.println("User 1 follows User 4: " + fm.isFollowing(1, 4)); // false

        System.out.println("Followers of User 1: " + fm.getFollowers(1));

        System.out.println("User 1 is following: " + fm.getFollowing(1));

        List<int[]> mutuals = fm.getMutualFollows();
        for (int[] pair : mutuals) {
            System.out.println("Mutual: " + Arrays.toString(pair));
        }

        for (int user = 1; user <= 5; user++) {
            System.out.printf("Influence score of User %d: %.2f%n", user, fm.calculateInfluence(user));
        }

        fm.unfollow(2, 1);
        System.out.println("After User 2 unfollows User 1:");
        System.out.println("User 2 follows User 1: " + fm.isFollowing(2, 1)); // false
        System.out.println("Mutual follows after unfollow:");
        for (int[] pair : fm.getMutualFollows()) {
            System.out.println("Mutual: " + Arrays.toString(pair)); // [1,3], [1,5] only
        }

        //AI WAS USED IN THE GENERATION OF TEST CASES FOR FOLLOWERMATRIX
    }
}
