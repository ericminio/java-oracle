package ericminio.javaoracle.support;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class LogSinkTest {

    private LogSink logSink;

    @Before
    public void sut() {
        logSink = new LogSink();
    }

    @Test
    public void logsTheGivenMessageWithLevel() {
        Logger logger = logSink.getLogger();
        logger.log(Level.INFO, "hello world");

        assertThat(logSink.getLog(), equalTo("INFO: hello world\n"));
    }

    @Test
    public void logsMessagesOnSeperateLines() {
        Logger logger = logSink.getLogger();
        logger.log(Level.INFO, "hello");
        logger.log(Level.INFO, "world");

        assertThat(logSink.getLog(), equalTo("INFO: hello\nINFO: world\n"));
    }

    @Test
    public void doesNotLogToConsoleByDefault() {
        assertThat(logSink.logsToConsole(), equalTo(false));
        assertThat(logSink.getLogger().getHandlers()[0], CoreMatchers.<Handler>instanceOf(StreamHandler.class));
        assertThat(logSink.getLogger().getHandlers().length, equalTo(1));
    }

    @Test
    public void canLogToTheConsoleToo() {
        assertThat(new LogSink(true).logsToConsole(), equalTo(true));
        assertThat(new LogSink(true).getLogger().getHandlers().length, equalTo(2));
        assertThat(new LogSink(true).getLogger().getHandlers()[0], CoreMatchers.<Handler>instanceOf(StreamHandler.class));
        assertThat(new LogSink(true).getLogger().getHandlers()[1], CoreMatchers.<Handler>instanceOf(ConsoleHandler.class));
    }
}
