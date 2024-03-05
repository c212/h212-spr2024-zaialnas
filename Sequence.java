import java.util.ArrayList;

// Sequence class that extends ArrayList<Integer> to demonstrate custom sorting.
public class Sequence extends ArrayList<Integer> {
    //         i
    //1, 3, 7, 6, 4, 2, 75
    //[1, 3, 7][6, 4, 2, 75]
    //             j
    //         [6][4, 2, 75]
    //            [4][2, 75]
    //               [2, 75][]
    //            [2, 4, 75]
    //         [2, 4, 6, 75]
    //[1, 2, 3, 6, 7, 75]

    // Default constructor that creates an empty sequence.
    public Sequence() {
        this(new int[]{}); // Calls another constructor with an empty array.
    }

    // Constructor that initializes the sequence with an array of integers.
    public Sequence(int[] values) {
        for (int v : values) {
            this.add(v); // Adds each element to the sequence.
        }
    }

    // Copy constructor that creates a new sequence based on another sequence.
    public Sequence(Sequence sequence) {
        super(sequence); // Utilizes the ArrayList's copy constructor.
    }

    // Method to split the sequence based on a condition and return two sequences.
    public ArrayList<Sequence> split(Sequence prefix, Sequence rest) {
        prefix = new Sequence(prefix);
        rest = new Sequence(rest);

        // Check if splitting is possible based on the condition that the last element of prefix is less than the first element of rest.
        if (rest.size() > 0 && (prefix.size() == 0 || prefix.get(prefix.size() - 1) < rest.get(0))) {
            prefix.add(rest.get(0)); // Move the first element of rest to the end of prefix.
            rest.remove(0); // Remove that element from rest.
            return split(prefix, rest); // Recursive call to continue splitting.
        } else {
            ArrayList<Sequence> result = new ArrayList<>();
            result.add(prefix); // Add the final prefix sequence.
            result.add(rest); // Add the remaining rest sequence.
            return result;
        }
    }

    // Method to merge two sorted sequences into one sorted sequence.
    public Sequence merge(Sequence a, Sequence b) {
        a = new Sequence(a);
        b = new Sequence(b);

        if (a.size() == 0) return b; // If a is empty, return b.
        else if (b.size() == 0) return a; // If b is empty, return a.
        else {
            Integer e;
            // Determine the smallest element from the start of both sequences.
            if (a.get(0) < b.get(0)) e = a.remove(0);
            else e = b.remove(0);

            Sequence result = merge(a, b); // Merge the remaining elements.
            result.add(0, e); // Add the smallest element to the front of the result.
            return result;
        }
    }

    // Method to merge this sequence with another sorted sequence.
    public Sequence mergeSorted(Sequence other) {
        // Split both sequences into two parts, but only the prefix is used for merging.
        ArrayList<Sequence> p1 = this.split(new Sequence(), this);
        ArrayList<Sequence> p2 = other.split(new Sequence(), other);
        return merge(p1.get(0), p2.get(0)); // Merge the two prefixes.
    }

    // Static method to sort a sequence.
    public static Sequence sort(Sequence a) {
        if (a.size() < 2) return new Sequence(a); // If the sequence has less than 2 elements, return as is.
        else {
            // Split the sequence into two parts.
            ArrayList<Sequence> pair = a.split(new Sequence(), a);
            Sequence b = pair.get(0); // First part for further splitting or merging.
            Sequence c = Sequence.sort(pair.get(1)); // Recursively sort the second part.
            System.out.println("merge " + b + " with " + c);
            return b.mergeSorted(c); // Merge the sorted sequences.
        }
    }

    // Main method to demonstrate sorting.
    public static void main(String[] args) {
        Sequence a = new Sequence(new int[]{4, 5, 9, 3, 1, 75, 6});
        System.out.println(Sequence.sort(a));
    }
}
