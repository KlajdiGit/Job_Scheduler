package main.java.klajdi.com.common.worker;

import com.klajdi.redis.RedisClient;
import main.java.klajdi.com.common.Job;
import main.java.klajdi.com.common.WorkerInfo;
import main.java.klajdi.com.common.scheduler.Scheduler;
import main.java.klajdi.com.common.JobStatus;
import redis.clients.jedis.Jedis;
import java.util.List;


public class WorkerNode {

    private String id;
    // private Scheduler  scheduler;
    private Jedis redis = new RedisClient().getConnection();
    public static final String JOB_QUEUE_KEY = "job_queue";

    public WorkerNode(String id){
        this.id = id;
        //this.scheduler = scheduler;
    }

    public void sendHeartbeat() {
        redis.setex("worker:" + id + ":heartbeat", 10, String.valueOf(System.currentTimeMillis()));
    }

    public void pollForJobs() {
        // 1. Send heartbeat to Redis (10-second TTL)
        redis.setex("worker:" + id + ":heartbeat", 10, String.valueOf(System.currentTimeMillis()));

        // 2. Block until a job arrives from Redis
        List<String> result = redis.brpop(0, JOB_QUEUE_KEY);

        if (result != null && result.size() > 1) {
            String serialized = result.get(1);
            String[] parts = serialized.split("\\|");
            Job job = new Job(parts[0], parts[1]);

            System.out.println("Worker " + id + " executing job: " + job.getId());

            // 3. Track current job in Redis for crash recovery
            redis.set("worker:" + id + ":currentJob", serialized);

            job.setStatus(JobStatus.RUNNING);

            // 4. Simulate crash for worker-1
            if (id.equals("worker-1")) {
                System.out.println("Simulating worker-1 crash...");
                return;
            }

            // 5. Heartbeat before the sleep so TTL resets during execution
            sendHeartbeat();

            // 6. Simulate job execution
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 7. Mark job completed and clear current job
            job.setStatus(JobStatus.COMPLETED);
            redis.del("worker:" + id + ":currentJob");
            System.out.println("Worker " + id + " completed job: " + job.getId());
            sendHeartbeat();
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
