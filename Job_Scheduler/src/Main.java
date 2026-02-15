
import com.klajdi.common.Job;
import com.klajdi.scheduler.Scheduler;
import com.klajdi.worker.WorkerNode;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {

        Scheduler scheduler = new Scheduler();

        scheduler.submitJob(new Job("job1", "payload1"));
        scheduler.submitJob(new Job("job2", "payload2"));

        WorkerNode worker1 = new WorkerNode("worker-1", scheduler);
        WorkerNode worker2 = new WorkerNode("worker-2", scheduler);


        scheduler.registerWorker("worker-1");
        scheduler.registerWorker("worker-2");

        worker1.start();
        worker2.start();


        while(true){
            Thread.sleep(3000);
            scheduler.checkForDeadWorkers();
        }
    }
}