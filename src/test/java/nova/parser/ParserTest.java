package nova.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nova.command.Command;
import nova.exception.NovaException;
import nova.task.Task;

public class ParserTest {
    private final Parser parser = new Parser();

    @Test
    public void parseTask_validDeadline_returnsDeadline() throws NovaException {
        Task task = parser.parseTask(
                "deadline submit report /by 2026-09-05", Command.DEADLINE);

        assertEquals("[D][ ] submit report (by: Sep 05 2026)", task.toString());
    }

    @Test
    public void parseTask_partialDelimiter_throwsException() {
        assertThrows(NovaException.class, () -> parser.parseTask(
                "deadline submit report /bye tomorrow", Command.DEADLINE));
        assertThrows(NovaException.class, () -> parser.parseTask(
                "event demo /from 2026-09-05 /today", Command.EVENT));
    }

    @Test
    public void parseTask_invalidDate_throwsException() {
        assertThrows(NovaException.class, () -> parser.parseTask(
                "deadline submit report /by Friday", Command.DEADLINE));
    }
}
