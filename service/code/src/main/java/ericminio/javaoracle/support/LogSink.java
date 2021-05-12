package ericminio.javaoracle.support;


import java.io.ByteArrayOutputStream;
import java.util.logging.*;

public class LogSink {

    private final Logger logger;
    private ByteArrayOutputStream inMemoryLogData;
    private StreamHandler inMemoryHandler;
    private boolean logsToConsole;

    public LogSink() {
        this(false);
    }

    public LogSink(boolean logsToConsole) {
        this.logsToConsole = logsToConsole;
        logger = Logger.getAnonymousLogger();
        logger.setUseParentHandlers(false);
        inMemoryLogData = new ByteArrayOutputStream();
        inMemoryHandler = new StreamHandler(inMemoryLogData, new BasicFormatter());
        logger.addHandler(inMemoryHandler);
        if (logsToConsole) {
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new BasicFormatter());
            logger.addHandler(consoleHandler);
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public String getLog() {
        inMemoryHandler.flush();
        return inMemoryLogData.toString();
    }

    public boolean logsToConsole() {
        return logsToConsole;
    }

    class BasicFormatter extends Formatter {

        @Override
        public String format(LogRecord record) {
            return "[" + record.getLevel().getName() + "] " + record.getMessage() + "\n";
        }
    }
}
