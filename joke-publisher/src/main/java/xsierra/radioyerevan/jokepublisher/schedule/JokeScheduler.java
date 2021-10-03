package xsierra.radioyerevan.jokepublisher.schedule;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import xsierra.radioyerevan.jokepublisher.JokePublisher;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.TriggerBuilder.newTrigger;

public class JokeScheduler implements AutoCloseable {

    private final Scheduler scheduler;

    private final JokePublisher jokePublisher;

    public JokeScheduler(JokePublisher jokePublisher) {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            this.jokePublisher = jokePublisher;
            init();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void init() throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(JokePublisherJob.class)
                .withIdentity("PublishJobJoke")
                .build();

        var trigger = newTrigger().
                withIdentity("PublishJokeTrigger")
                .startNow()
                .withSchedule(dailyAtHourAndMinute(21, 0))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    @Override
    public void close() throws Exception {
        scheduler.shutdown();
    }

    private class JokePublisherJob implements Job {

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            jokePublisher.publishJoke();
        }
    }
}
