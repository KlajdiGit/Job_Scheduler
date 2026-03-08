package main.java.klajdi.com.common.scheduler;

import main.java.klajdi.com.common.Job;
import main.java.klajdi.com.common.WorkerInfo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.Map;

import com.klajdi.redis.RedisClient;
import redis.clients.jedis.Jedis;

public class Scheduler {

    public static final long WORKER_TIMEOUT_MS = 5000;
    // private Queue<Job> jobQueue = new LinkedList<>();
    private Map<String, WorkerInfo> workers = new HashMap<>();
    private Jedis redis = new RedisClient().getConnection();
    public static final String JOB_QUEUE_KEY = "job_queue";



    public WorkerInfo getWorkerInfo(String workerId) {
        return workers.get(workerId);
    }

    /*public void submitJob(Job job){
        jobQueue.add(job);
        System.out.println("Job submitted: " + job.getId());
    }*/

    public void submitJob(Job job) {
        String serialized = job.getId() + "|" + job.getPayload();
        redis.lpush(JOB_QUEUE_KEY, serialized);
        System.out.println("Scheduler: Job submitted -> " + job.getId());
    }


   /* public Job getNextJob(){
        return jobQueue.poll();
    }*/

    public void  registerWorker(String workerID){
        workers.put(workerID, new WorkerInfo(workerID));
        System.out.println("Scheduler: Registered worker -> " + workerID);
    }

    public void hertbeat(String workerId){
        WorkerInfo info = workers.get(workerId);
        if(info != null){
            info.updateHeartbeat();
            System.out.println("Scheduler: Heartbeat received from " + workerId);
        }
    }

    public void checkForDeadWorkers(){

        long now = System.currentTimeMillis();

        for(Map.Entry<String, WorkerInfo> entry : workers.entrySet()){

            String workerId = entry.getKey();
            WorkerInfo info = entry.getValue();

            long last = info.getLastHeartbeat();
            if(now - last > WORKER_TIMEOUT_MS){
                System.out.println("Scheduler: Worker " + workerId + " is DEAD (no heartbeat)");

                if(info.getCurrentJob() != null){
                    Job job = info.getCurrentJob();
                    //jobQueue.add(job);
                    redis.lpush(JOB_QUEUE_KEY, job.getId() + "|" + job.getPayload());

                    System.out.println("Scheduler: Requeued job " + job.getId() + " from dead worker " + workerId);
                    info.setCurrentJob(null);
                }
            }
        }
    }

    public void startMonitoring(){

        new Thread(() -> {
            while(true){
                checkForDeadWorkers();

                try{
                    Thread.sleep(3000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
