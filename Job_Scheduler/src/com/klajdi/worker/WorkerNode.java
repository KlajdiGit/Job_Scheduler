package com.klajdi.worker;

import com.klajdi.common.Job;
import com.klajdi.scheduler.Scheduler;

public class WorkerNode {

    private String id;
    private Scheduler  scheduler;

    public WorkerNode(String id, Scheduler scheduler){
        this.id = id;
        this.scheduler = scheduler;
    }

    public void pollForJobs(){
        Job job = scheduler.getNextJob();
        if(job != null){
            System.out.println("Worker " + id + " executing job: " + job.getId());

            job.setStatus(com.klajdi.common.JobStatus.COMPLETED);
        } else{
            System.out.println("Worker " + id + "  didn't find this jobs.");
        }

    }
}
