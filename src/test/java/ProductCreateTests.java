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

public class ProductCreateTests {
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

    @DisplayName("Создание продукта с валидными данными")
    @Test
    void postProductTests() throws IOException {
        Response<Product> response = productService.createProduct(product).execute();

        PrettyLogger.DEFAULT.log(response.toString());
        assertThat(response.body().getTitle(), equalTo(product.getTitle()));
        assertThat(response.body().getPrice(), equalTo(product.getPrice()));
        assertThat(response.body().getCategoryTitle(), equalTo(product.getCategoryTitle()));
    }

    @DisplayName("Создание продукта с отрицательной ценой")
    @Test
    void postProductWithNegativePriceTest() throws IOException {
        product = new Product()
                .withTitle(faker.food().dish())
                .withPrice(-1)
                .withCategoryTitle(CategoryType.FOOD.getTitle());
        Response<Product> response = productService.createProduct(product).execute();

        PrettyLogger.DEFAULT.log(response.toString());
        assertThat(response.code(), equalTo(400));
        assertThat(response.isSuccessful(), equalTo(false));
    }

    @DisplayName("Создание продукта с пустой ценой")
    @Test
    void postProductWithNullPriceTest() throws IOException {
        product = new Product()
                .withTitle(faker.food().dish())
                .withPrice(null)
                .withCategoryTitle(CategoryType.FOOD.getTitle());
        Response<Product> response = productService.createProduct(product).execute();

        PrettyLogger.DEFAULT.log(response.toString());
        assertThat(response.code(), equalTo(400));
        assertThat(response.isSuccessful(), equalTo(false));
    }

    @DisplayName("Создание продукта с нулевой ценой")
    @Test
    void postProductWithZeroPriceTest() throws IOException {
        product = new Product()
                .withTitle(faker.food().dish())
                .withPrice(0)
                .withCategoryTitle(CategoryType.FOOD.getTitle());
        Response<Product> response = productService.createProduct(product).execute();

        PrettyLogger.DEFAULT.log(response.toString());
        assertThat(response.code(), equalTo(400));
        assertThat(response.isSuccessful(), equalTo(false));
    }
}
