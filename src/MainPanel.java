import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MainPanel extends JPanel{
    private final Scheduler dispatcher;
    @SuppressWarnings("SpellCheckingInspection")
    private boolean unpuased=false;
    private int steps=0;

    @SuppressWarnings("SpellCheckingInspection")
    private JTextField npid;
    private JSpinner nppriority;
    private JLabel current,curtime,completed,terminated;
    private JProgressBar progress;
    private JList<Process> ready,blocked;

    public MainPanel(Component parent){
        dispatcher=new Scheduler();
        setPreferredSize(parent.getPreferredSize());
        setLayout(new BorderLayout());
        swing_init();
        Timer timer=new Timer(1,e->update());
        timer.start();
    }
    private void update(){
        if(unpuased) {
            dispatcher.update();
            steps++;
        }
        current.setText(dispatcher.displayCurrent());
        curtime.setText(("Time Slice: "+dispatcher.getCurtime()+"/"+dispatcher.getTimeSlice()));
        progress.setValue(dispatcher.progress());

        Process[] rdy=new Process[dispatcher.getReady().size()];
        dispatcher.getReady().toArray(rdy);
        ready.setListData(rdy);
        Process[] blk=new Process[dispatcher.getBlocked().size()];
        dispatcher.getBlocked().toArray(blk);
        blocked.setListData(blk);



        completed.setText("Completed: "+dispatcher.processComplete);
        terminated.setText("Terminated: "+dispatcher.processTerminated);
    }

    @SuppressWarnings("SpellCheckingInspection")
    private void swing_init(){
        ButtonListener listener=new ButtonListener();
        JButton npbutton = new JButton("New Process");
        npid=new JTextField();
        nppriority=new JSpinner(new SpinnerNumberModel(0,0,5,1));
        npbutton.addActionListener(listener);
        GridLayout gl=new GridLayout(1,5);
        gl.setHgap(10);
        gl.setVgap(10);
        JPanel newprocess=new JPanel(gl);
        newprocess.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        newprocess.add(new JLabel("PID: "));
        newprocess.add(npid);
        newprocess.add(new JLabel("Priority: "));
        newprocess.add(nppriority);
        newprocess.add(npbutton);

        current=new JLabel(dispatcher.displayCurrent());
        curtime=new JLabel("Time Slice: "+dispatcher.getCurtime()+"/"+dispatcher.getTimeSlice());
        JButton pause = new JButton("Pause");
        pause.addActionListener(listener);
        progress=new JProgressBar(JProgressBar.HORIZONTAL,0,100);
        progress.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        progress.setBackground(Color.white);
        JButton term=new JButton("Terminate");
        term.addActionListener(listener);
        JPanel cpu=new JPanel(new GridLayout(2,2));
        cpu.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        cpu.add(new JLabel("Currently running: "));
        cpu.add(current);
        cpu.add(progress);
        cpu.add(pause);
        cpu.add(term);
        cpu.add(curtime);
        cpu.setBackground(Color.lightGray);

        ready=new JList<>();
        ready.setVisibleRowCount(20);
        ready.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        blocked=new JList<>();
        blocked.setVisibleRowCount(20);
        blocked.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JButton block = new JButton("Block");
        block.addActionListener(listener);
        JButton unblock = new JButton("Unblock");
        unblock.addActionListener(listener);
        JPanel queues=new JPanel(new GridLayout(1,3));
        completed=new JLabel("Completed: "+dispatcher.processComplete);
        terminated=new JLabel("Terminated: "+dispatcher.processTerminated);
        JPanel bunb=new JPanel(new GridLayout(4,1));
        bunb.setBorder(BorderFactory.createEmptyBorder(150,10,200,10));
        bunb.add(block);
        bunb.add(unblock);
        bunb.add(completed);
        bunb.add(terminated);

        queues.add(new JLabel("Ready queue"));
        queues.add(new JScrollPane(ready));
        queues.add(bunb);
        queues.add(new JLabel("Blocked queue"));
        queues.add(new JScrollPane(blocked));

        JPanel labels=new JPanel(new GridLayout(1,3));
        labels.add(new JLabel("Ready queue"));
        labels.add(new JLabel(""));
        labels.add(new JLabel("Blocked queue"));

        JPanel middle=new JPanel(new BorderLayout());
       // middle.add(labels,BorderLayout.NORTH);
        middle.add(queues,BorderLayout.SOUTH);


        add(cpu, BorderLayout.NORTH);
        add(middle,BorderLayout.CENTER);
        add(newprocess,BorderLayout.SOUTH);
    }

    private class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "New Process":
                    String id=npid.getText();
                    int pri=(int)nppriority.getValue();
                    if(id.isEmpty()) {
                        id = Integer.toHexString(((int) (Math.random() * 100000000)));
                        while(id.length()<8)
                            id="0"+id;
                        id="0x"+id.toUpperCase();
                        pri=(int)(Math.random()*6);
                    }
                    dispatcher.newProcess(id,pri);
                    LogDisplay.print("Process : "+id+" has been added to ready\n");
                    break;
                case "Pause":
                    unpuased=!unpuased;
                    LogDisplay.print(unpuased?"Computer un-paused.\n":"Computer paused.\n");
                    break;
                case "Block":
                    LogDisplay.print("Process : "+dispatcher.getCurrent()+" has been blocked\n");
                    dispatcher.block();
                    break;
                case "Unblock":
                    LogDisplay.print("Process : "+dispatcher.getCurrent()+" has been unblocked\n");
                    dispatcher.unblock();
                    break;
                case "Terminate":
                    Process t=dispatcher.getCurrent();
                    LogDisplay.print("Process : "+t+" has been terminated!\n", Color.red,Color.white);
                    t.setRunning(false);
                    t.setTerminated(true);
                    break;
            }
        }
    }

}
