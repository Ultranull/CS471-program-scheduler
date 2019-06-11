

import java.util.Collections;
import java.util.LinkedList;

class Scheduler {
    private final LinkedList<Process> ready;
    private final LinkedList<Process> blocked;
    private final float timeSlice;
    @SuppressWarnings("SpellCheckingInspection")
    private double curtime=0;
    private double step;
    private Process current;

    public int processComplete=0;
    public int processTerminated=0;


    public Scheduler(){
        ready=new LinkedList<>();
        blocked=new LinkedList<>();
        timeSlice=1000;
        step=1;
        current=null;
    }

    public void update(){
        if(current==null){
            if(!ready.isEmpty()) {
                current=ready.getFirst();
                ready.removeFirst();
            }
        }
        if (current!=null){
            current.step(step);
            curtime+=step;
            if(current.getTime()>timeSlice) {
                current.setRunning(false);
                current.terminate();
            }
            if(!current.isRunning()){

                if(current.isTerminated())
                    processTerminated++;
                else{
                    processComplete++;
                }

                if(!ready.isEmpty()) {
                    current = ready.getFirst();
                    ready.removeFirst();
                }else
                    current=null;
                curtime=0;
            }
        }
    }

    @SuppressWarnings("SpellCheckingInspection")
    public double getCurtime() {
        return curtime;
    }

    public float getTimeSlice() {
        return timeSlice;
    }

    public void block(){
        if(current!=null){
            blocked.add(current);
            current=null;
        }
    }
    public void unblock(){
        if(!blocked.isEmpty()){
            Process ind=blocked.getFirst();
            blocked.removeFirst();
            addToReady(ind);
        }
    }
    public void newProcess(String id,int p){
        addToReady(new Process(id,p,timeSlice));
    }

    public int progress(){
        return current!=null?current.getTimeStamp():0;
    }

    private void addToReady(Process p){
        ready.add(p);
       // ready.sort(Comparator.comparingInt(Process::getPriority).reversed());
        Collections.sort(ready);
    }
    public String displayCurrent(){
        if(current==null)
            return "none";
        return current+"";
    }

    public LinkedList<Process> getReady() {
        return ready;
    }

    public LinkedList<Process> getBlocked() {
        return blocked;
    }

    public Process getCurrent() {
        return current;
    }
}
