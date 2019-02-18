package ru.otus.L041;
/*
 -agentlib:jdwp=transport=dt_socket,address=14000,server=y,suspend=n
 -Xms512m
 -Xmx512m
 -XX:MaxMetaspaceSize=256m
 -verbose:gc
 -Xlog:gc*:file=./logs/gc_%t_pid_%p.log
 -Dcom.sun.management.jmxremote.port=15000
 -Dcom.sun.management.jmxremote.authenticate=false
 -Dcom.sun.management.jmxremote.ssl=false
 -XX:+HeapDumpOnOutOfMemoryError
 -XX:HeapDumpPath=./dumps
 -XX:OnOutOfMemoryError="kill -3 %p"
 -XX:+UseG1GC
 -XX:+UseConcMarkSweepGC
 -XX:+UseSerialGC
 -XX:+UseParallelGC
 jps -- list vms or ps -e | grep java
 jstack <pid> >> threaddumps.log -- get dump from pid
 jinfo -- list VM parameters
 jvisualvm-- analyze heap dump
 */

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;


public class GCMain {

    public static Map<Long, Object[]> STORAGE = new HashMap<Long, Object[]>();

    public static void main(String[] args) throws InterruptedException, MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {

        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean());
        Thread mainThread = Thread.currentThread();
        GCUtil.startGCMonitor(mainThread);

        //-XX:+UseParallelGC
        //final int SIZE = 165 * 1000 * 1000;

        //-XX:+UseG1GC
        final int SIZE = 165 * 1000 * 1000;

        final MBeanServer mbs  = ManagementFactory.getPlatformMBeanServer();
        final ObjectName name = new ObjectName("ru.otus:type=Benchmark");
        final Benchmark benchmark = new Benchmark();
        mbs.registerMBean(benchmark, name);
        benchmark.setSize(SIZE);
        benchmark.run();
    }
}
