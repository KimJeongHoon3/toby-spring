package external.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

@Configuration
public class HelloConfigWithImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        String mode=(String)importingClassMetadata.getAnnotationAttributes(EnableHelloWithImportSelector.class.getName()).get("mode");
        if(mode.equals("mode1")){
            return new String[]{HelloConfig1.class.getName()};
        }else{
            return new String[]{HelloConfig2.class.getName()};
        }
    }
}
