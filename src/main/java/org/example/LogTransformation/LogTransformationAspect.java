package org.example.LogTransformation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
@Aspect
@Component
public class LogTransformationAspect {

    @Value("${DirectoryLogTransformation}")
    private String LogDirectory  ;
    //&& !execution(* getOrder())
    //@AfterReturning(value = "execution(* *(..)) && @within(logTransformation) && !execution(* getOrder())", returning="result")
    @Around(value = "execution(* *(..)) && @within(logTransformation) && !execution(* getOrder()) " )
    // execution(* *(..)) --любого метода с любыми аргументами  кроме метода getOrder
    public Object logTransformation(ProceedingJoinPoint joinPoint, LogTransformation logTransformation  ) throws Throwable
    {
        if (logTransformation==null)
            return null;

        String  methodname =joinPoint.getSignature().getName();
        String logFile = logTransformation.LogFile(); // Получаем имя лог-файла из аннотации
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String filepath =LogDirectory +logFile;
        Object[] args = joinPoint.getArgs();
        Object result= joinPoint.proceed();
        // Логируем данные в файл
        try  (BufferedWriter writer =new BufferedWriter(new FileWriter(filepath,true))) {
            writer.write("\n Начало выполнения : " +LocalDateTime.now());
            writer.write(" Класс компонента: " + className);
            writer.write(" Метод: " + methodname);
            writer.write("\n Входные данные: ");
            writer.write("\n args.lenght= "+ args.length);
            for (Object arg : args) {
                writer.write(" Тип аргумента" + arg.getClass().toGenericString() + " " + arg.toString())  ;
            }
            writer.write("\n Выходные данные: тип значения " + result.getClass().getSimpleName() +" значение "+ result);
            writer.write("\n-----------------------------------------------------------------------------");
        }
        catch (IOException e ) {
            e.printStackTrace();
        }
        return result;
    }
}
