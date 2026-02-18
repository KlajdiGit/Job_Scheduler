package com.klajdi.common;

import java.util.Formatter;

public class WorkerInfo {
    private String id;
    private long lastHeartbeat;
    private Job currentJob;

    public WorkerInfo(String id){
        this.id = id;
        this.lastHeartbeat = System.currentTimeMillis();
    }

    public String getId(){
        return id;
    }

    public long getLastHeartbeat(){
        return lastHeartbeat;
    }

    public void updateHeartbeat(){
        this.lastHeartbeat = System.currentTimeMillis();
    }

    public Job getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(Job currentJob) {
        this.currentJob = currentJob;
    }
}
