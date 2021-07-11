
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Vector;

class multi_level_3 {
    public static Queue<Integer> Ready_Queue=new LinkedList<>();
    public static Vector<String> execution_order=new Vector<String>();
    public static Vector<String> execution_order_FCFS=new Vector<String>();
    public static int t=0;;
    public  void start()
    {
        int quantum;
        int num_processes;
        String processes[];
        int brust_time[];
        int arrival_time[];
        int turnaround_time[];
        int completion_time[];
        int waiting_time[];
        int queue_num[];

        try (Scanner input = new Scanner(System.in))
        {
            System.out.println("How much is the quantum?");
            quantum=Integer.parseInt(input.nextLine());

            System.out.println("How many processes you want to add?");
            num_processes = Integer.parseInt(input.nextLine());
            processes=new String [num_processes];
            brust_time=new int[num_processes];
            arrival_time=new int[num_processes];
            waiting_time=new int[num_processes];
            queue_num=new int[num_processes];


            for(int i=0;i<num_processes;i++)
            {
                System.out.println("Enter process name");
                processes[i]=input.nextLine();

                System.out.println("Enter process arrival_time");
                arrival_time[i]=Integer.parseInt(input.nextLine());

                System.out.println("Enter process brust_time");
                brust_time[i]=Integer.parseInt(input.nextLine());

                System.out.println("Enter process queue_num");
                queue_num[i]=Integer.parseInt(input.nextLine());
            }

        }
        calc_waiting( queue_num, processes , waiting_time , num_processes ,
                brust_time , quantum, arrival_time);

    }
    public void calc_waiting(int queue_num[],String process[],int wt_time[],
                             int n ,int brusttime[],int quantum,int arrival_time[] )
    {

        int RR_count=0,FCFS_count=0;
        for (int i = 0; i < n; i++) {
            if (queue_num[i] == 1) {
                RR_count++;
            } else if (queue_num[i] == 2) {
                FCFS_count++;
            }
        }



        int[] queue_RR = new int[RR_count];
        int[] queue_FCFS = new int[FCFS_count];
        int rr_element = 0, fcfs_element = 0;
        for (int i = 0; i < n; i++) {
            if (queue_num[i] == 1) {
                queue_RR[rr_element] = i;
                rr_element++;
            } else if (queue_num[i] == 2) {
                queue_FCFS[fcfs_element] = i;
                fcfs_element++;
            }
        }


        int sum_arrival = 0;
        for (int i = 0; i < n; i++) {
            sum_arrival += arrival_time[i];
        }

        Queue<Integer> RR_arrival_queue = new LinkedList<>();
        Queue<Integer> FCFS_arrival_queue = new LinkedList<>();

        int[] FCFS_arrival = new int[queue_FCFS.length];
        int[] RR_arrival = new int[queue_RR.length];

        int[] FCFS_reman = new int[queue_FCFS.length];
        int[] RR_reman = new int[queue_RR.length];

        String [] RR_prosses = new String[queue_RR.length];
        String[] FCFS_prosses = new String[queue_FCFS.length];

        int[] RR_qnum = new int[queue_RR.length];
        int[] FCFS_qnum = new int[queue_FCFS.length];

        int completion_time_rr[] = new int[queue_RR.length];
        int waiting_time_rr[] = new int[queue_RR.length];
        int turnaround_time_rr[] = new int[queue_RR.length];

        int waiting_time_fcfs[] = new int[queue_FCFS.length];
        int turn_around_time_fcfs[] = new int[queue_FCFS.length];

        for (int i = 0; i < queue_FCFS.length; i++) {
            FCFS_arrival_queue.add(arrival_time[queue_FCFS[i]]);
            FCFS_arrival[i] = arrival_time[queue_FCFS[i]];
            FCFS_reman[i] = brusttime[queue_FCFS[i]];
            FCFS_prosses[i] = process[queue_FCFS[i]];
            FCFS_qnum[i] = queue_num[queue_FCFS[i]];

        }

        for (int i = 0; i < queue_RR.length; i++) {
            RR_arrival_queue.add(arrival_time[queue_RR[i]]);
            RR_arrival[i] = arrival_time[queue_RR[i]];
            RR_reman[i] = brusttime[queue_RR[i]];
            RR_prosses[i] = process[queue_RR[i]];
            RR_qnum[i] = queue_num[queue_RR[i]];
        }

        //int t=arrival_time[0];
        // int i=0 ,j=0;

        //  while(t<sum_arrival)
        //{

        // for(int r=0 ; r<RR_arrival.length ; r++)
        // {
        if(!RR_arrival_queue.isEmpty())
        {
            RR_arrival_queue.remove();
            calc_waiting_time_RR(RR_prosses,waiting_time_rr,queue_RR.length,RR_reman,quantum,completion_time_rr,RR_arrival);
            calc_turnaround_time_RR(RR_prosses,waiting_time_rr,queue_RR.length,RR_reman,turnaround_time_rr,completion_time_rr,arrival_time);
            int total_wt_RR = 0, total_tat_RR = 0;

            System.out.println("RR_prosses " +" RR_arrival\t"+ " RR_reman " +" completion_time_rr "+
                    " turnaround_time_rr " + " waiting_time_rr " +" RR_qnum ");
            for (int rr=0; rr<queue_RR.length; rr++)
            {
                total_wt_RR = total_wt_RR + waiting_time_rr[rr];
                total_tat_RR = total_tat_RR + turnaround_time_rr[rr];
                System.out.println(" " + RR_prosses[rr] + "\t\t\t\t"+ RR_arrival[rr]+"\t\t\t\t"+ + RR_reman[rr] +"\t\t\t\t " +completion_time_rr[rr]+"\t\t\t\t"
                        +turnaround_time_rr[rr] +"\t\t\t\t " + waiting_time_rr[rr] + "\t\t\t\t "+RR_qnum[rr]);
            }

            System.out.println("Average waiting time = " +
                    (float)total_wt_RR / (float)queue_RR.length);
            System.out.println("Average turn around time = " +
                    (float)total_tat_RR / (float)queue_RR.length);

            print_execution_RR();
        }

        if(!FCFS_arrival_queue.isEmpty())
        {
            FCFS_arrival_queue.remove();

            findWaitingTime_FCFS(  FCFS_prosses,  queue_FCFS.length,  FCFS_reman,  waiting_time_fcfs,  FCFS_arrival , completion_time_rr , RR_arrival);
            findTurnAroundTime_FCFS(  FCFS_prosses, queue_FCFS.length, FCFS_reman, waiting_time_fcfs, turn_around_time_fcfs);

            // Display processes along with all details
            System.out.print("FCFS_prosses " + " FCFS_reman " + " FCFS_arrival "
                    + " Waiting_Time_FCFS " + " Turn-Around_Time_FCFS "+"FSFC_qnum"+"\n");
            int total_waiting_time_FCFS = 0, total_turn_around_time_FCFS = 0;
            for (int fcfs= 0 ; fcfs < queue_FCFS.length ; fcfs++)
            {
                total_waiting_time_FCFS = total_waiting_time_FCFS + waiting_time_fcfs[fcfs];
                total_turn_around_time_FCFS = total_turn_around_time_FCFS + turn_around_time_fcfs[fcfs];
                System.out.println(FCFS_prosses[fcfs] + "\t\t\t\t" + FCFS_reman[fcfs] + "\t\t\t\t"
                        + FCFS_arrival[fcfs] + "\t\t\t\t" + waiting_time_fcfs[fcfs] + "\t\t\t\t"
                        + turn_around_time_fcfs[fcfs] + "\t\t\t\t\t"+ FCFS_qnum[fcfs]);
            }

            System.out.print("Average waiting time FCFS = "
                    + (float)total_waiting_time_FCFS / (float)queue_FCFS.length);
            System.out.print("\nAverage turn around time FCFS = "
                    + (float)total_turn_around_time_FCFS / (float)queue_FCFS.length);
            print_execution_FCFS();

        }
        //}

        //   }

    }

    public void print_execution_RR()
    {
        System.out.println("/n***Order of execution***");
        System.out.println(execution_order);
    }

    public void print_execution_FCFS()
    {
        System.out.println("/n***Order of execution***");
        System.out.println(execution_order_FCFS);
    }


    public static void calc_waiting_time_RR(String process[],int wt_time[],int n ,int RR_reman[],int quantum,int completion_time[],int arrival_time[]){

        int remainding_time[] = new int[n];

        for(int i=0;i<wt_time.length;i++){
            remainding_time[i]= RR_reman[i];
        }



        int element=0;
        Queue<Integer>arriving=new LinkedList<>();
        for(int i=0;i<arrival_time.length;i++)
        {
            arriving.add(arrival_time[i]);
        }

        while(true)
        {
            boolean done = true;
            while(!arriving.isEmpty())
            {
                if(arriving.peek()<=t)
                {
                    Ready_Queue.add(element);
                    element++;
                    arriving.remove();
                }
                else
                {
                    break;
                }
            }
            if(Ready_Queue.size()==0&&!arriving.isEmpty())
            {
                t++;
                continue;
            }


            if(Ready_Queue.size()==0&& arriving.isEmpty())
            {
                t++;
                break;
            }
            int index=0;
            for(int i=0;i<1;i++)
            {
                index = Ready_Queue.remove();
                if(remainding_time[index] > 0) {
                    done = false;
                    if (remainding_time[index] > quantum) {
                        t += quantum;
                        remainding_time[index] -= quantum;
                        execution_order.add(process[index]);

                        /////////////////////////////////////////////////////////////

                        while(!arriving.isEmpty())
                        {
                            if(arriving.peek()<=t)
                            {
                                Ready_Queue.add(element);
                                element++;
                                arriving.remove();

                            }
                            else
                            {
                                break;
                            }
                        }
                        ////////////////////////////////////////////////
                        Ready_Queue.add(index);
                    }
                    else
                    {
                        t += remainding_time[index];
                        remainding_time[index] = 0;
                        execution_order.add(process[index]);
                        completion_time[index] = t;
                    }

                }
                if (done == true)
                {
                    break;
                }

            }

        }
    }

    static void findWaitingTime_FCFS(String process[], int n, int FCFS_reman[], int wt_time[], int FCFS_arrival[] , int completion_time_rr[] , int RR_arrival[])
    {
        int sum_burst_time[] = new int[n];
        sum_burst_time[0] = 0;
        int arrivel_ff=FCFS_arrival[0];
        int start_fcfs_time = 0;
        for(int j=0 ; j<RR_arrival.length ; j++)
        {
            if(arrivel_ff >= RR_arrival[j])
            {
                start_fcfs_time=completion_time_rr[j];
            }
            else
            {
                break;
            }

        }
        wt_time[0] = start_fcfs_time-FCFS_arrival[0];

        // calculating waiting time
        for (int i = 1; i < n ; i++)
        {
            // Add burst time of previous processes
            sum_burst_time[i] = sum_burst_time[i-1] + FCFS_reman[i-1];

            wt_time[i] = sum_burst_time[i] - FCFS_arrival[i] +start_fcfs_time;

            int wasted=0;
            if (wt_time[i] < 0) {
                wasted = Math.abs(wt_time[i]);
                wt_time[i] = 0;
            }
            //Add wasted time
            sum_burst_time[i] = sum_burst_time[i] + wasted;
            t+=sum_burst_time[i];
        }
    }


    public static void calc_turnaround_time_RR(String process[] ,int waiting_time[],int n,int brust_time[],int turnaround_time[],int completion_time[],int arrival_time[]){
        for(int i=0;i<n;i++){
            turnaround_time[i]= completion_time[i]-arrival_time[i];
            waiting_time[i] = turnaround_time[i]-brust_time[i];


        }

    }

    static void findTurnAroundTime_FCFS(String process[], int n, int brusttime[], int wt_time[], int turnaround_time[])
    {
        // Calculating turnaround time by adding bt[i] + wt[i]
        for (int i = 0; i < n ; i++) {
            turnaround_time[i] = brusttime[i] + wt_time[i];
            execution_order_FCFS.add(process[i]);
        }
    }




    public static void main(String []agrs){
        multi_level_3 object = new multi_level_3();
        object.start();

    }
}

