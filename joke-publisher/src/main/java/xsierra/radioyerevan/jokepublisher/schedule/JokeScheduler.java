package xsierra.radioyerevan.jokepublisher.schedule;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class JokeScheduler implements AutoCloseable {

    private static final String PUBLISHER_CRON = System.getenv("PUBLISHER_CRON");

    private final Scheduler scheduler;

    public JokeScheduler() {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            init();
        } catch (SchedulerException e) {
            throw new IllegalStateException(e);
        }
    }

    public void init() throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(ScheduledJokePublisher.class)
                .withIdentity("PublishJobJoke")
                .build();

        var trigger = newTrigger().
                withIdentity("PublishJokeTrigger")
                .startNow()
                .withSchedule(cronSchedule(PUBLISHER_CRON))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    @Override
    public void close() throws Exception {
        scheduler.shutdown();
    }

}
