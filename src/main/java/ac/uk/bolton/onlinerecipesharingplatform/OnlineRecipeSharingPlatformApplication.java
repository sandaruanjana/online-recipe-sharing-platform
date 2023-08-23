package ac.uk.bolton.onlinerecipesharingplatform;

import ac.uk.bolton.onlinerecipesharingplatform.entity.Category;
import ac.uk.bolton.onlinerecipesharingplatform.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineRecipeSharingPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineRecipeSharingPlatformApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(CategoryService categoryService) {
        return args -> {
//            Category category1 = new Category();
//            category1.setName("Breakfast");
//            categoryService.createCategory(category1);
//
//            Category category2 = new Category();
//            category2.setName("Lunch");
//            categoryService.createCategory(category2);
//
//            Category category3 = new Category();
//            category3.setName("Dinner");
//            categoryService.createCategory(category3);
//
//            Category category4 = new Category();
//            category4.setName("Desserts");
//            categoryService.createCategory(category4);
        };
    }

}
