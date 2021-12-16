import com.github.javafaker.Faker;
import dto.Product;
import enums.CategoryType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import service.ProductService;
import utils.PrettyLogger;
import utils.RetrofitUtils;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ProductUpdateTests {
    static Retrofit client;
    static ProductService productService;
    Faker faker = new Faker();
    Product product;
    int id;

    @BeforeAll
    static void beforeAll() {
        client = RetrofitUtils.getRetrofit();
        productService = client.create(ProductService.class);
    }

    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().dish())
                .withPrice((int) ((Math.random() + 1) * 100))
                .withCategoryTitle(CategoryType.FOOD.getTitle());
    }

    @DisplayName("Удаление названия продукта")
    @Test
    void putProductNullTitleTest() throws IOException {
        product.setId(id);
        product.setTitle(null);

        Response<Product> response = productService.updateProduct(product).execute();

        PrettyLogger.DEFAULT.log(response.toString());
        assertThat(response.code(), equalTo(400));
        assertThat(response.isSuccessful(), equalTo(false));
    }

    @DisplayName("Обнуление цены продукта")
    @Test
    void putProductZeroPriceTest() throws IOException {
        product.setId(id);
        product.setPrice(0);

        Response<Product> response = productService.updateProduct(product).execute();

        PrettyLogger.DEFAULT.log(response.toString());
        assertThat(response.code(), equalTo(400));
        assertThat(response.isSuccessful(), equalTo(false));
    }

    @DisplayName("Удаление цены продукта")
    @Test
    void putProductNullPriceTest() throws IOException {
        product.setId(id);
        product.setPrice(null);

        Response<Product> response = productService.updateProduct(product).execute();

        PrettyLogger.DEFAULT.log(response.toString());
        assertThat(response.code(), equalTo(400));
        assertThat(response.isSuccessful(), equalTo(false));
    }


}
