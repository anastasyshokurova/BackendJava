import com.github.javafaker.Faker;
import db.dao.ProductsMapper;
import dto.Product;
import enums.CategoryType;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import retrofit2.Retrofit;
import service.ProductService;
import utils.DbUtils;
import utils.PrettyLogger;
import utils.RetrofitUtils;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ProductUpdateTests {
    static ProductsMapper productsMapper;
    static Retrofit client;
    static ProductService productService;
    Faker faker = new Faker();
    Product product;
    int id;

    @BeforeAll
    static void beforeAll() {
        productsMapper = DbUtils.getProductsMapper();
        client = RetrofitUtils.getRetrofit();
        productService = client.create(ProductService.class);
    }

    @BeforeEach
    void setUp() throws IOException {
        product = new Product()
                .withTitle(faker.food().dish())
                .withPrice((int) ((Math.random() + 1) * 100))
                .withCategoryTitle(CategoryType.FOOD.getTitle());
        Response<Product> response = productService.createProduct(product).execute();
        id = response.body().getId();
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

    @AfterEach
    void tearDown() throws IOException {
        Response<ResponseBody> response = productService.deleteProduct(id).execute();

        assertThat(response.code(), equalTo(200));
        assertThat(response.isSuccessful(), equalTo(true));
    }
}
