package app.utils;
/**
 * Пользовательский источник вывода log4j2, простой вывод на консоль или в ArrayList
 *
 */
//The MyLog4j2 corresponding here is in xml,
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *  <appenders>
 * <MyLog4j2 name="customAppender" printString="A     ">
 *     </MyLog4j2>
 *  </appenders>
 *
 */
@Plugin(name = "MyLog4j2", category = "Core", elementType = "appender", printObject = true)
public class MyLog4j2Appender extends AbstractAppender {

    String printString;
    /**
     * конструктор может настроить параметр здесь напрямую, передать константу и вывести
     *
     */
    protected MyLog4j2Appender(String name, Filter filter, Layout<? extends Serializable> layout, String printString) {
        super(name, filter, layout);
        this.printString = printString;
    }

    @Override
    public void append(LogEvent event) {
        if (event != null && event.getMessage() != null) {
            // Custom implementation output here
            // Get the output value: event.getMessage().toString()
//             System.out.print(event.getMessage().toString());


            // formatted output
            System.out.print(printString + " ：" + getLayout().toSerializable(event));
            Serializable serializable = getLayout().toSerializable(event);
            List<String> list = new ArrayList<>();
            list.add((String) serializable);
            System.out.println("******* Сообщение из ArrayList ************" + list.get(0));
        }

    }

    /** Получить параметры в файле конфигурации
     *
     * @PluginAttribute буквально знает, является ли значение атрибута узла xml, например <oKong name = "oKong"> </oKong>, где name - это атрибут
     * @PluginElement: представляет элементы дочернего узла xml,
     * Такие как
     *     <oKong name="oKong">
     *         <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
     *     </oKong>
     * Среди них PatternLayout - это Layout, который на самом деле является классом реализации {@link Layout}.
     */
    @PluginFactory
    public static MyLog4j2Appender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Filter") final Filter filter,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginAttribute("printString") String printString) {

        if (name == null) {
            LOGGER.error("имя не определено в конфиге");
            return null;
        }
        // Use PatternLayout by default
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }

        return new MyLog4j2Appender(name, filter, layout, printString);
    }

    @Override
    public void start() {
        System.out.println("log4j2-start method is called");
        super.start();
    }

    @Override
    public void stop() {
        System.out.println("log4j2-stop method is called");
        super.stop();
    }
}