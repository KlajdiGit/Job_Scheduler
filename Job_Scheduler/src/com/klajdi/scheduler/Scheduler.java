package com.klajdi.scheduler;

import com.klajdi.common.Job;
import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {

    private Queue<Job> jobQueue = new LinkedList<>();

    public void submitJob(Job job){
        jobQueue.add(job);
        System.out.println("Job submitted: " + job.getId());
    }

    public Job getNextJob(){
        return jobQueue.poll();
    }
}
