package husseinm.opdss;

import javafx.concurrent.Task;
import javafx.concurrent.Worker;

import java.util.concurrent.atomic.AtomicBoolean;

public class Model {
    public Worker<String> worker;
    public AtomicBoolean shouldThrow = new AtomicBoolean(false);

    private int total = 150;

    public void setTotal(int total) {
        this.total = total;
    }

    public Model() {
        worker = new Task<String>() {
            @Override
            protected String call() throws Exception {
                updateTitle("Example Task");
                updateMessage("Starting...");
                updateProgress(0, total);
                for (int i = 1; i <= total; i++) {
                    if (isCancelled()) {
                        updateValue("Canceled at " + System.currentTimeMillis());
                        return null;
                    }
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        updateValue("Canceled at " + System.currentTimeMillis());
                        return null;
                    }
                    if (shouldThrow.get()) {
                        throw new RuntimeException("Exception thrown at " + System.currentTimeMillis());
                    }
                    updateTitle("Example Task (" + i + ")");
                    updateMessage("Processed " + i + " of " + total + " items.");
                    updateProgress(i, total);
                }
                return "Completed at " + System.currentTimeMillis();
            }

            @Override
            protected void scheduled() {
                System.out.println("The task is scheduled.");
            }

            @Override
            protected void running() {
                System.out.println("The task is running.");
            }
        };
        ((Task<String>) worker).setOnSucceeded(event -> {
            System.out.println("The task succeeded.");
        });
        ((Task<String>) worker).setOnCancelled(event -> {
            System.out.println("The task is canceled.");
        });
        ((Task<String>) worker).setOnFailed(event -> {
            System.out.println("The task failed.");
        });
    }
}