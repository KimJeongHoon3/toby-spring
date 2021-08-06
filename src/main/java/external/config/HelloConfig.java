package external.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Configuration
public class HelloConfig implements ImportAware {
    @Bean
    Hello hello(){
        Hello hello=new Hello();
        hello.setName("Spring");
        return hello;
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        Map<String, Object> allAnnotationAttributes = importMetadata.getAnnotationAttributes(EnableHello.class.getName());
        String name = (String)allAnnotationAttributes.get("name");
        hello().setName(name);

    }
}
