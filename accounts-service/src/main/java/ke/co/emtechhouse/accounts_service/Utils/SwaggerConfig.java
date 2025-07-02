package ke.co.emtechhouse.accounts_service.Utils;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Account-Service Sacco Swagger API")
                        .description("Account Service.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Calvin")
                                .email("chumacalvin8@gmail.com")
                                .url("")

                        ).license(new License()
                                .name("MIT")
                                .url("")
                        )
                );


    }
//    @Bean
//    public OperationCustomizer addGlobalHeaders() {
//        return (operation, handlerMethod) -> {
//            operation.addParametersItem(new Parameter()
//                    .in(ParameterIn.HEADER.toString())
//                    .name("userName")
//                    .description("Remote User")
//                    .required(true)
//                    .schema(new Schema<String>().type("string")));
//            operation.addParametersItem(new Parameter()
//                    .in(ParameterIn.HEADER.toString())
//                    .name("entityId")
//                    .description("Entity ID")
//                    .required(true)
//                    .schema(new Schema<String>().type("string")));
//            return operation;
//        };
//    }
}
