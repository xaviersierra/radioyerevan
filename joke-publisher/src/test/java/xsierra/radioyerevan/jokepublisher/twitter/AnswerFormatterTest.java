package xsierra.radioyerevan.jokepublisher.twitter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnswerFormatterTest {

    @Test
    void should_contain_empty_answers() {
        var answers = AnswerFormatter.formatAnswer("A crime novel titled \"How I received the Nobel prize in\n" +
                "\n" +
                "literature.\"");

        assertEquals(2, answers.length);

    }
}