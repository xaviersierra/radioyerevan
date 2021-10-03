package xsierra.radioyerevan.jokepublisher;

public interface JokePublisher {

    void subscribe(JokeConsumer consumer);

    void publishJoke();
}
