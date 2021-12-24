import com.github.javafaker.Faker;
import db.dao.ProductsMapper;
import db.model.Products;
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
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ProductReadTests {
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

    @DisplayName("Получение продукта по валидному id")
    @Test
    void getProductByIdTest() throws IOException {
        Products dbProduct = DbUtils.getProductsMapper().selectByPrimaryKey((long)id);
        Response<Product> response = productService.getProduct(id).execute();

        PrettyLogger.DEFAULT.log(response.toString());

        assertThat(dbProduct.getId(), equalTo((long)id));
        assertThat(dbProduct.getTitle(), equalTo(product.getTitle()));
        assertThat(dbProduct.getPrice(), equalTo(product.getPrice()));

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
        Integer countProducts = DbUtils.countProducts(productsMapper);
        Response<ArrayList<Product>> response = productService.getProducts().execute();

        PrettyLogger.DEFAULT.log(response.toString());
        assertThat(countProducts, equalTo(response.body().size()));

        assertThat(response.code(), equalTo(200));
        assertThat(response.isSuccessful(), equalTo(true));
        response.body().forEach(product ->
                assertThat(product.getId(), notNullValue()));
    }

    @AfterEach
    void tearDown() throws IOException {
        Response<ResponseBody> response = productService.deleteProduct(id).execute();

        assertThat(response.code(), equalTo(200));
        assertThat(response.isSuccessful(), equalTo(true));
    }
}
