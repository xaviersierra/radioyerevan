package xsierra.radioyerevan.twitterbot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnswerFormatterTest {

    @Test
    void shortAnswers_ShouldNotBeSplitted() {

        var formated = AnswerFormatter.formatAnswer("There are enough to have a referendum.");

        assertEquals(1, formated.length);

    }

    @Test
    void longAnswers_ShouldNotBeSplittedByItsLineBreaks() {

        var formated = AnswerFormatter.formatAnswer(
                "In England, what is permitted, is permitted, and what is prohibited, is prohibited.\n" +
                        "In America everything is permitted except for what is prohibited.\n" +
                        "In Germany everything is prohibited except for what is permitted.\n" +
                        "In France everything is permitted, even what is prohibited.\n" +
                        "In the USSR everything is prohibited, even what is permitted.");

        assertEquals(5, formated.length);

    }
}
