package main.java.klajdi.com.common;

public class Job {

    private String id;
    private String payload;
    private JobStatus status;

    public Job( String id, String payload){
        this.id = id;
        this.payload = payload;
        this.status = JobStatus.PENDING;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }
}
