

public class Process implements Comparable<Process>{
    private final String pid;
    private final float timeSlice;
    private int time;
    private boolean isRunning;
    private boolean isTerminated=false;
    private final int priority;
    public Process(String id,int p,float ts){
        pid=id;
        timeSlice=(ts/8)+(float)((Math.random()*ts));
        priority=p;

        time=0;
        isRunning=true;

    }

    public void step(double stp){
        time+=stp;
        if(time>timeSlice){
            isRunning=false;
        }
    }

    public int getTimeStamp(){
        return (int)((((double)time)/((double)timeSlice))*100.0);
    }

    public int getTime() {
        return time;
    }

    public boolean isRunning() {
        return isRunning;
    }
    public void terminate() {
        isRunning = false;
        isTerminated=true;
    }
    public void setRunning(boolean running) {
        isRunning = running;
    }

    private String getPid() {
        return pid;
    }

    private int getTimeSlice() {
        return (int)timeSlice;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isTerminated() {
        return isTerminated;
    }

    public void setTerminated(boolean terminated) {
        isTerminated = terminated;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Process))return false;
        Process p=(Process) obj;
        return p.getPid().equals(getPid()) && p.getTimeSlice()==getTimeSlice();
    }

    @Override
    public String toString() {
        return getPriority()+" "+getPid()+" "+getTimeSlice();
    }

    @Override
    public int compareTo(Process process) {
        return getPriority()-process.getPriority();
    }
}
