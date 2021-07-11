import java.util.Scanner;
public class FCFS {
    static void findWaitingTime_FCFS(String processes[], int n, int burst_time[], int waiting_time[], int arrival_time[])
    {
        int sum_burst_time[] = new int[n];
        sum_burst_time[0] = 0;
        waiting_time[0] = 0;

        // calculating waiting time
        for (int i = 1; i < n ; i++)
        {

            // Add burst time of previous processes
            sum_burst_time[i] = sum_burst_time[i-1] + burst_time[i-1];

            // Find waiting time for current process =
            // sum - at[i]
            waiting_time[i] = sum_burst_time[i] - arrival_time[i];

            // If waiting time for a process is in negative
            // that means it is already in the ready queue
            // before CPU becomes idle so its waiting time is 0
            // wasted time is basically time for process to wait after a process is over
            //representing wasted time in queue
            int wasted=0;
            if (waiting_time[i] < 0) {
                wasted = Math.abs(waiting_time[i]);
                waiting_time[i] = 0;
            }
            //Add wasted time
            sum_burst_time[i] = sum_burst_time[i] + wasted;
        }
    }

    // Function to calculate turn around time
    static void findTurnAroundTime_FCFS(String processes[], int n, int burst_time[],
                                        int waiting_time[], int turna_round_time[])
    {
        // Calculating turnaround time by adding bt[i] + wt[i]
        for (int i = 0; i < n ; i++)
            turna_round_time[i] = burst_time[i] + waiting_time[i];
    }

    // Function to calculate average waiting and turn-around
// times.
    static void findavgTime_FCFS(String processes[], int n, int burst_time[], int arrival_time[] ,int queue_num[])
    {
        int waiting_time[] = new int[n], turn_around_time[] = new int[n];

        // Function to find waiting time of all processes
        findWaitingTime_FCFS(processes, n, burst_time, waiting_time, arrival_time);

        // Function to find turn around time for all processes
        findTurnAroundTime_FCFS(processes, n, burst_time, waiting_time, turn_around_time);

        // Display processes along with all details
        System.out.print("Processes " + " Burst_Time " + " Arrival_Time "
                + " Waiting_Time " + " Turn-Around_Time ");
        int total_waiting_time = 0, total_turn_around_time = 0;
        for (int i = 0 ; i < n ; i++)
        {
            total_waiting_time = total_waiting_time + waiting_time[i];
            total_turn_around_time = total_turn_around_time + turn_around_time[i];
            System.out.println(processes[i] + "\t\t\t\t" + burst_time[i] + "\t\t\t\t"
                    + arrival_time[i] + "\t\t\t\t" + waiting_time[i] + "\t\t\t\t"
                    + turn_around_time[i] );
        }

        System.out.print("Average waiting time = "
                + (float)total_waiting_time / (float)n);
        System.out.print("\nAverage turn around time = "
                + (float)total_turn_around_time / (float)n);
    }

    static void start ()
    {
        int x;
        Scanner sc=new Scanner(System.in);
        Scanner str=new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        x=sc.nextInt();
        String[] processes = new String[10];
        int[] burst_time = new int[10];
        int[] arrival_time = new int[10];
        int[] queue_num = new int[10];
        for(int i=0; i<x; i++)
        {
            System.out.println("Enter name of proscess "+ (i+1) + " :  ");
            processes[i]=str.nextLine();

            System.out.println("Enter Arrival time of proscess "+ (i+1) + " :  ");
            arrival_time[i]=sc.nextInt();

            System.out.println("Enter Burst time of proscess "+ (i+1) + " :  ");
            burst_time[i]=sc.nextInt();


        }

        findavgTime_FCFS(processes, x, burst_time, arrival_time ,queue_num );

    }

// Driver code

    /*public static void main(String []agrs){
        FCFS object = new FCFS();
        object.start();

    }*/
}
