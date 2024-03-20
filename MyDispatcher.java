/* Implement this class. */

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.abs;

public class MyDispatcher extends Dispatcher {
    AtomicInteger nr = new AtomicInteger(0);

    public MyDispatcher(SchedulingAlgorithm algorithm, List<Host> hosts) {
        super(algorithm, hosts);
    }

    @Override
    public synchronized void addTask(Task task) {
        switch (algorithm) {
            case ROUND_ROBIN:
                int increment = nr.getAndIncrement();
                hosts.get(increment % hosts.size()).addTask(task);
                break;
            case SHORTEST_QUEUE:
                int min = 0;
                for (int i = 0; i < hosts.size(); i++) {
                    if (hosts.get(i).getQueueSize() < hosts.get(min).getQueueSize()) {
                        min = i;
                    }
                }
                hosts.get(min).addTask(task);
                break;
            case SIZE_INTERVAL_TASK_ASSIGNMENT:
                if (task.getType() == TaskType.SHORT) {
                    hosts.get(0).addTask(task);
                }
                if (task.getType() == TaskType.MEDIUM) {
                    hosts.get(1).addTask(task);
                }
                if (task.getType() == TaskType.LONG){
                    hosts.get(2).addTask(task);
                }
                break;
            case LEAST_WORK_LEFT:
                int min2 = 0;
                for (int i = 0; i < hosts.size(); i++) {
                    if (abs(hosts.get(min2).getWorkLeft() - hosts.get(i).getWorkLeft()) < 1000L) {
                        if (hosts.get(min2).getId() < hosts.get(i).getId()) {
                            min2 = i;
                        }
                    } else if (hosts.get(i).getWorkLeft() < hosts.get(min2).getWorkLeft()) {
                        min2 = i;
                    }
                }
                hosts.get(min2).addTask(task);
                break;
        }
    }
}
