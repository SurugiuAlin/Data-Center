/* Implement this class. */

import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class MyHost extends Host {

    Task current_task;
    // pornesc host-ul
    boolean isRunning = true;

    // ordonez coada descrescator dupa prioritate si crescator dupa timpul de start
    PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>(1, Comparator.comparing(Task::getPriority).reversed().thenComparing(Task::getStart));

    @Override
    public void run() {
        // cat timp host-ul este pornit (exista task-uri in coada)
        while (isRunning) {
            // daca nu exista un task curent, il extrag din coada
            if (current_task == null) {
                current_task = queue.poll();
            }
            Task task = queue.peek();
            // daca task-ul curent extras din coada are prioritate mai mica decat task-ul din varful cozii si este preemptibil
            // incep executia task-ului din varful cozii (task) si pun task-ul curent (current_task) inapoi in coada
            if (current_task != null && task != null && task.getPriority() > current_task.getPriority() && current_task.isPreemptible()) {
                Task t = current_task;
                current_task = queue.poll();
                queue.add(t);
            }
            // simulam executia task-ului curent
            if (current_task != null) {
                if (current_task.getLeft() - 1000L >= 0) {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    current_task.setLeft(current_task.getLeft() - 1000L);
                }
                // termin executia task-ului curent
                if (current_task.getLeft() <= 0) {
                    current_task.finish();
                    current_task = null;
                }
            }
        }
    }

    @Override
    public void addTask(Task task) {
        queue.add(task);
    }

    @Override
    public int getQueueSize() {
        // daca exista un task curent, il adaug la dimensiunea cozii
        if (current_task != null) {
            return queue.size() + 1;
        }
        // altfel returnez dimensiunea cozii
        return queue.size();
    }

    @Override
    public long getWorkLeft() {
        long work_left = 0;
        // adun timpul ramas de executie al tuturor task-urilor din coada
        for (Task task : queue) {
            work_left += task.getLeft();
        }
        // daca exista un task curent, il adaug la timpul ramas de executie
        if (current_task != null) {
            work_left += current_task.getLeft();
        }
        // returnez timpul ramas de executie
        return work_left;
    }

    @Override
    public void shutdown() {
        // opresc host-ul
        isRunning = false;
    }
}
