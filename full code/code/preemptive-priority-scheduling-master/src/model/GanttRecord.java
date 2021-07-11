package model;

public class GanttRecord {
    private int inTime;
    private int outTime;
    private int processId;
    public GanttRecord(int inTime, int outTime, int processId) {
        this.inTime = inTime;
        this.outTime = outTime;
        this.processId = processId;
    }
    public int getOutTime() {
        return outTime;
    }
    public int getProcessId() {
        return processId;
    }

}
