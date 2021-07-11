
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Vector;

public class multi_level_2 {
    public static Queue<Integer> Ready_Queue=new LinkedList<>();
    public static Vector<String> execution_order=new Vector<String>();

    public  void start()
    {
        int context_switching;
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

            System.out.println("How much is the context switching?");
            context_switching=Integer.parseInt(input.nextLine());

            System.out.println("How many processes you want to add?");
            num_processes = Integer.parseInt(input.nextLine());
            processes=new String [num_processes];
            brust_time=new int[num_processes];
            arrival_time=new int[num_processes];
            turnaround_time=new int[num_processes];
            completion_time=new int[num_processes];
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
    }

    public void calc_waiting(int queue_num[],String process[],int wt_time[],int n ,int brusttime[],int context_switching,int quantum,int arrival_time[] )
    {
        int[] queue_RR=new int[n];
        int[] queue_FCFS=new int[n];
        int rr_element=0, fcfs_element=0;
        for(int i=0;i<n;i++)
        {
            if(queue_num[i]==1)
            {
                queue_RR[rr_element]=i;
                rr_element++;
            }
            else if(queue_num[i]==2)
            {
                queue_FCFS[fcfs_element]=i;
                fcfs_element++;
            }
        }


        int sum_arrival=0;
        for(int i=0 ; i<n ; i++)
        {
            sum_arrival+=arrival_time[i];
        }

        Queue<Integer> RR_arrival = new LinkedList<>();
        Queue<Integer> FCFS_arrival = new LinkedList<>();

        int[] FCFS_reman=new int[queue_FCFS.length];
        int[] RR_reman=new int[queue_FCFS.length];

        for(int i=0 ; i<queue_FCFS.length ; i++)
        {
            FCFS_arrival.add(arrival_time[queue_FCFS[i]]);
            FCFS_reman[i]=brusttime[queue_FCFS[i]];
        }

        for(int i=0 ; i<queue_RR.length ; i++)
        {
            RR_arrival.add(arrival_time[queue_RR[i]]);
            RR_reman[i]=brusttime[queue_RR[i]];
        }

        int t=arrival_time[0];
        int i=0 ,j=0;
        while(t<sum_arrival)
        {
            int w_RR_time = 0;
            int k=0;
            int Btime=0;

            while(RR_arrival.peek()<=t)
            {
                int completion_time[] = new int[n];
                calc_waiting_time_RR( k , process, wt_time , n , brusttime, context_switching, quantum, completion_time ,RR_arrival, FCFS_reman);
                i++;
                t++;
                Btime++;///////////////last time


            }

            if(FCFS_arrival.peek()<=t)
            {
                int start_time=Btime-FCFS_arrival.remove();
                int x=0;
                FCFS_reman[x]-=1;
                if( FCFS_reman[x]>0)
                {
                    while(t<k)
                    {
                        FCFS_reman[x]-=1;
                        t++;
                    }
                    if(t==k)
                    {

                    }
                }



                j++;

            }
            else
            {
                t++;
            }


        }

    }

    static void findWaitingTime_FCFS(String process[], int n, int brusttime[], int wt_time[], int arrival_time[])
    {
        int sum_burst_time[] = new int[n];
        sum_burst_time[0] = 0;
        wt_time[0] = 0;

        // calculating waiting time
        for (int i = 1; i < n ; i++)
        {
            sum_burst_time[i] = sum_burst_time[i-1] + brusttime[i-1];

            wt_time[i] = sum_burst_time[i] - arrival_time[i];
            int wasted=0;
            if (wt_time[i] < 0) {
                wasted = Math.abs(wt_time[i]);
                wt_time[i] = 0;
            }
            //Add wasted time
            sum_burst_time[i] = sum_burst_time[i] + wasted;
        }
    }


    public static void calc_waiting_time_RR(int k ,String process[],int wt_time[],int n ,int brusttime[],int context_switching,int quantum,int completion_time[], Queue<Integer> RR_arrival , int FCFS_reman[]){

        int remainding_time[] = new int[n];

        for(int i=0;i<wt_time.length;i++){
            remainding_time[i]= brusttime[i];
        }


        int t=0;
        int element=0;
        Queue<Integer>arriving=new LinkedList<>();
        for(int i=0;i<RR_arrival.size();i++)
        {
            arriving.add(RR_arrival.remove());
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
                    k=arriving.peek();
                    break;
                }
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
                if (remainding_time[index] > 0) {
                    done = false;
                    if (remainding_time[index] > quantum) {
                        t += quantum+context_switching;
                        remainding_time[index] -= quantum;
                        execution_order.add(process[index]);

                        ////////////////////////////////////////////////
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
                                //findWaitingTime_FCFS(process ,  n,  FCFS_reman ,  wt_time,  FCFS_arrival );
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
}

