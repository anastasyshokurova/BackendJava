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
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ProductReadTests {
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

    @DisplayName("Получение продукта по валидному id")
    @Test
    void getProductByIdTest() throws IOException {
        Response<Product> response = productService.getProduct(id).execute();

        PrettyLogger.DEFAULT.log(response.toString());
        assertThat(response.code(), equalTo(200));
        assertThat(response.isSuccessful(), equalTo(true));
        assertThat(response.body().getId(), equalTo(id));
        assertThat(response.body().getTitle(), equalTo(product.getTitle()));
        assertThat(response.body().getPrice(), equalTo(product.getPrice()));
        assertThat(response.body().getCategoryTitle(), equalTo(product.getCategoryTitle()));
    }

    @DisplayName("Получение продукта по не валидному id")
    @Test
    void getProductByNullIdTest() throws IOException {
        Response<Product> response = productService.getProduct(-1 * id).execute();

        PrettyLogger.DEFAULT.log(response.toString());
        assertThat(response.code(), equalTo(404));
        assertThat(response.isSuccessful(), equalTo(false));
    }

    @DisplayName("Получение всех продуктов")
    @Test
    void getProductsTest() throws IOException {
        Response<ArrayList<Product>> response = productService.getProducts().execute();

        PrettyLogger.DEFAULT.log(response.toString());
        assertThat(response.code(), equalTo(200));
        assertThat(response.isSuccessful(), equalTo(true));
        response.body().forEach(product ->
                assertThat(product.getId(), notNullValue()));
    }
}
