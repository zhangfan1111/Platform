//package com.k2data.k2app.log4j;
//
//import org.apache.logging.log4j.core.LogEvent;
//import org.apache.logging.log4j.core.config.plugins.Plugin;
//import org.apache.logging.log4j.core.pattern.ConverterKeys;
//import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
//
///**
// * 自定义 log4j2 输出
// *
// * @author lidong9144@163.com 17-3-22.
// */
//@Plugin(name="ThreadIdPatternLayout", category = "Converter")
//@ConverterKeys({"tId"})
//public class ThreadIdPatternLayout extends LogEventPatternConverter {
//
//    /**
//     * Constructs an instance of LoggingEventPatternConverter.
//     *
//     * @param name  name of converter.
//     * @param style CSS style for output.
//     */
//    protected ThreadIdPatternLayout(String name, String style) {
//        super(name, style);
//    }
//
//    @Override
//    public void format(LogEvent event, StringBuilder toAppendTo) {
//        toAppendTo.append(Thread.currentThread().getId());
//    }
//
//}
