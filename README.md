# Datacenter
Task planning in a datacenter

MyDispatcher and MyHost

MyDispatcher
As a developer, I manage the assignment of tasks to various threads based on the specified algorithm. I implement multiple task assignment algorithms:

Round Robin
For the Round Robin algorithm, I use an AtomicInteger variable to ensure that the thread index is incremented atomically and is not accessed by multiple threads simultaneously. I apply the modulo operation between the value of this variable and the number of available threads given by hosts.size(), so that I can add the task to the appropriate thread.

SHORTEST_QUEUE
For the SHORTEST_QUEUE algorithm, I iterate through the list of threads and identify the thread with the shortest task queue. I add the new task to the queue of this thread.

SIZE_INTERVAL_TASK_ASSIGNMENT
For the SIZE_INTERVAL_TASK_ASSIGNMENT algorithm, I check the type of the task and assign it to the thread queue corresponding to the task execution duration (Short, Medium, Long).

LEAST_WORK_LEFT
For the LEAST_WORK_LEFT algorithm, I iterate through the list of threads and identify the thread with the smallest remaining execution time. I add the new task to the queue of this thread, considering equality between threads.

MyHost
As a developer, I represent a execution thread that handles tasks received from MyDispatcher. I utilize a PriorityBlockingQueue to manage the task queue in a synchronized manner between threads. The queue is ordered in descending order by the tasks' priority and ascending by their start time.

Useful Methods
getQueueSize(): Returns the size of the task queue.
getWorkLeft(): Returns the total remaining execution time of tasks in the queue.
The shutdown() Method
The shutdown() method is used to stop the execution of the host. I assign the boolean variable isRunning the value false, so that the run method no longer executes anything and allows the program to terminate.
