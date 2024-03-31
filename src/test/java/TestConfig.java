
import org.example.DataValidatorComponent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.example")
public class TestConfig {
    public DataValidatorComponent dataValidatorComponent () {
        return new DataValidatorComponent();
    }
}
