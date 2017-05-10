package com.deepti.amifit.util;

/**
 * Created by deepti on 05/03/17.
 */

public class DPExample {
    static int max_lis_length; // stores the final LIS

    // Recursive implementation for calculating the LIS
    static int _lis(int arr[], int n)
    {
        if (n == 1)
            return 1;
        int current_lis_length = 1;
        for (int i=0; i<n-1; i++)
        {
            int subproblem_lis_length = _lis(arr, i);
            if (arr[i] < arr[n-1] &&
                    current_lis_length < (1+subproblem_lis_length))
                current_lis_length = 1+subproblem_lis_length;
        }
        if (max_lis_length < current_lis_length)
            max_lis_length = current_lis_length;

        return current_lis_length;
    }

    // The wrapper function for _lis()
    public static int lis(int arr[], int n)
    {
        max_lis_length = 1; // stores the final LIS

        // max_lis_length is declared static above
        // so that it can maintain its value
        // between the recursive calls of _lis()
        _lis( arr, n );

        return max_lis_length;
    }

//    // Driver program to test the functions above
//    public static void main(String args[])
//    {
//        int arr[] = {10, 22, 9, 33, 21, 50, 40};
//        int n = arr.length;
//        System.out.println("Length of LIS is " + lis( arr, n ));
//    }

} // End of LIS class.