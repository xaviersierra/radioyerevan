package xsierra.radioyerevan.jokepublisher;

import java.util.List;

public class JokeChannel {

    private final JokePublisher publisher;

    private final List<JokeConsumer> subscribers;

    public JokeChannel(JokePublisher publisher, List<JokeConsumer> subscribers) {
        this.publisher = publisher;
        this.subscribers = subscribers;
    }

    public void startChannel(){
        subscribers.forEach(publisher::subscribe);
    }
}
