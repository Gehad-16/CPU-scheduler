import java.util.*;
public class RoundRobin
{
    public static Queue<Integer> Ready_Queue=new LinkedList<>();
    public static Vector<String>execution_order=new Vector<String>();


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

           for(int i=0;i<num_processes;i++)
           {
               System.out.println("Enter process name");
               processes[i]=input.nextLine();

               System.out.println("Enter process arrival_time");
               arrival_time[i]=Integer.parseInt(input.nextLine());

               System.out.println("Enter process brust_time");
               brust_time[i]=Integer.parseInt(input.nextLine());
           }
           calc_avarage_time(processes,num_processes,brust_time,context_switching,quantum,arrival_time);
           print_execution();
       }
   }


    public static void calc_waiting_time(String process[],int wt_time[],int n ,int brusttime[],int context_switching,int quantum,int completion_time[],int arrival_time[]){

        int remainding_time[] = new int[n];

        for(int i=0;i<wt_time.length;i++){
            remainding_time[i]= brusttime[i];
        }


        int t=0;
        int element=0;
        int index=0;
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
    public static void calc_turnaround_time(String process[] ,int waiting_time[],int n,int brust_time[],int turnaround_time[],int completion_time[],int arrival_time[]){
        for(int i=0;i<n;i++){
            turnaround_time[i]= completion_time[i]-arrival_time[i];
            waiting_time[i] = turnaround_time[i]-brust_time[i];


        }

    }

    public static void calc_avarage_time(String process[],int n,int brust_time[],int context_switching,int quantum,int arrival_time[]){
        int waiting_time[] = new int[n];
        int turnaround_time[] = new int[n];
        int completion_time[] = new int[n];
        calc_waiting_time(process,waiting_time,n,brust_time,context_switching,quantum,completion_time,arrival_time);
        calc_turnaround_time(process,waiting_time,n,brust_time,turnaround_time,completion_time,arrival_time);
        int total_wt = 0, total_tat = 0;

        System.out.println("Processes " +" Arrival Time\t"+ "  Burst time " +" completion time"+
                " Turnaround Time " + " Waiting time");
        for (int i=0; i<n; i++)
        {
            total_wt = total_wt + waiting_time[i];
            total_tat = total_tat + turnaround_time[i];
            System.out.println(" " + process[i] + "\t\t\t\t"+ arrival_time[i]+"\t\t\t\t"+ + brust_time[i] +"\t\t\t\t " +completion_time[i]+"\t\t\t\t"
                    +turnaround_time[i] +"\t\t\t\t " + waiting_time[i]);
        }

        System.out.println("Average waiting time = " +
                (float)total_wt / (float)n);
        System.out.println("Average turn around time = " +
                (float)total_tat / (float)n);
    }

    public void print_execution()
    {
        System.out.println("***Order of execution***");
        System.out.println(execution_order);
    }
  
}