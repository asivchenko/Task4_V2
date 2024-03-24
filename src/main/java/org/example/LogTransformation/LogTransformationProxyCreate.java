package org.example.LogTransformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;
import java.util.List;


//создание прокси объекта
@Component
public class LogTransformationProxyCreate {

    private final Logger logger;

    @Autowired
    public LogTransformationProxyCreate (Logger logger)
    {
        this.logger =logger;
    }
    public static <T> T createproxy (T target ,Logger logger    )
    {

        //Class <?> [] inters =target.getClass().getInterfaces();  //// надо все
        return (T) Proxy.newProxyInstance(
                // новый динамический проккси объект для заданного класса ,
                //интерфейсов и обработчика вызовов
                target.getClass().getClassLoader(), //загрузчик классов для создания прокси
                target.getClass().getInterfaces(),  //интерфейсы которые реализует объект
                new Handler (target , logger)   // обработчик вызовов для прокси
        );
    }

    private static class Handler implements InvocationHandler {

        private final Object target;
        private final Logger logger;

        public Handler(Object target, Logger logger)
        {
            this.target = target;
            this.logger = logger;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //определяем  метод через класс проксируемого объектаж
           // Method objmethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());
            Method objmethod =target.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
            if (objmethod.isAnnotationPresent(LogTransformation.class)) {
                LogTransformation annotation =objmethod.getAnnotation(LogTransformation.class);
                // определяем log
                String logfile =annotation.LogFile();
                //логируем информацию овызове метода
                logger.log( "\n Протокол входных данных: Start " + new Date() +
                            "\n LogFile " +logfile +
                            "\n Class" + target.getClass().getName() +
                            "\n Method " + objmethod.getName() +
                            "\n Arguments " + convertArgsToString(args));
            }
            Object result = method.invoke(target, args)  ;
            logger.log ("\n Протокол выходных данных: Result " + convertResultToString(result));
            return result;
        }
        //заточим под List
        //Преобразование в строку переданныъ args или result
        private String convertArgsToString (Object[] args)
        {
            if (args ==null)   return null;
            StringBuilder stringBuilder = new StringBuilder();
            for (Object arg : args) {
                if (arg instanceof List) {
                    ///////////////
                    List<?> listarg = (List<?>) arg;
                    for (Object element : listarg) {
                        stringBuilder.append(element.toString()).append(" ");
                    }
                }
            }

            return stringBuilder.toString();
        }
        private String convertResultToString (Object value)
        {
            if (value instanceof List)
            {
                StringBuilder stringBuilder = new StringBuilder();
                List<?> listvalue = (List<?>) value;
                for (Object element : listvalue) {
                    stringBuilder.append(element.toString()).append("\n ");
                }
                return  stringBuilder.toString();
            }
            else
                return  value.toString();
        }





    }
}
