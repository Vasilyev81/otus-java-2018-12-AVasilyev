package ru.otus.L041;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class GCUtil {
    private static NotificationListener gcHandler;
    private static GarbageCollectionNotificationInfo gcInfo;
    private static GCStatistics gcStatistics;
    private static Logger logger;
    private static Thread mainThread;

    static {
        logger = new Logger();
        gcStatistics = new GCStatistics();
    }

    // Garbage collection message handler
    static {
        gcHandler = new NotificationListener() {
            @Override
            public void handleNotification(Notification notification, Object handback) {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    gcInfo = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    gcStatistics.commitGarbageCollection(gcInfo.getGcName(), gcInfo.getGcInfo().getDuration());
                }
            }
        };
    }

    //Starts the garbage collection monitoring process
    public static void startGCMonitor(Thread thread) {
        for (GarbageCollectorMXBean mBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            ((NotificationEmitter) mBean).addNotificationListener(gcHandler, null, null);
        }
        mainThread = thread;
        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (mainThread.isAlive()) {
                    logger.printToLog(gcStatistics.getCollectionsTypeAndAmount() + gcStatistics.getCollectionsTimePerMinute());
                    gcStatistics.resetStatistics();
                } else {
                    timer.shutdown();
                }
            }
        }, 0, 60, TimeUnit.SECONDS);
    }
}
