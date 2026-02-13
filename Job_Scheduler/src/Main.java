
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

        WorkerNode worker = new WorkerNode("worker-1", scheduler);

        scheduler.registerWorker("worker-1");

        worker.pollForJobs();
        worker.pollForJobs();
        worker.pollForJobs();

        Thread.sleep(6000);

        scheduler.checkForDeadWorkers();
    }
}