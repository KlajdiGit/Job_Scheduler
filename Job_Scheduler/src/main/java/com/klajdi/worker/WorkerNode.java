package main.java.klajdi.com.common.worker;

import main.java.klajdi.com.common.Job;
import main.java.klajdi.com.common.JobStatus;
import main.java.klajdi.com.common.WorkerInfo;
import main.java.klajdi.com.common.scheduler.Scheduler;

public class WorkerNode {

    private String id;
    private Scheduler  scheduler;

    public WorkerNode(String id, Scheduler scheduler){
        this.id = id;
        this.scheduler = scheduler;
    }
    public void sendHeartbeat(){
        scheduler.hertbeat(id);
    }

    public void pollForJobs(){
        sendHeartbeat();
        WorkerInfo info = scheduler.getWorkerInfo(id);
        Job job = scheduler.getNextJob();

        if(job != null){
            System.out.println("Worker " + id + " executing job: " + job.getId());
            info.setCurrentJob(job);
            job.setStatus(JobStatus.COMPLETED);

            if (id.equals("worker-1")) {
                System.out.println("Simulating worker-1 crash...");
                return;
            }

            try{
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            job.setStatus(JobStatus.COMPLETED);
            info.setCurrentJob(null);

        } else{
            System.out.println("Worker " + id + "  didn't find this jobs.");
            info.setCurrentJob(null);
        }
    }

    public void start(){
        new Thread(() -> {
            while(true) {
                sendHeartbeat();
                pollForJobs();

                try{
                    Thread.sleep(2000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
