package HW3;
import dto.PostImageResponse;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static HW3.Endpoints.UPLOAD_IMAGE;
import static io.restassured.RestAssured.given;

    @Story("Тесты на удаления файла изображения")
    public class ImageDeleteTests extends BaseTest{

    @BeforeEach
    void setUp() {
        uploadedImageId = given(requestSpecificationWithAuthWithBase64, positiveResponseSpecification)
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .body()
                .as(PostImageResponse.class)
                .getData().getDeletehash();

    }
    @DisplayName("Удаление загруженного файла")
    @Test
    void deleteTest() {
        given(requestSpecificationWithAuth,positiveResponseSpecification)
                .delete(UPLOAD_IMAGE + uploadedImageId);
    }

    @DisplayName("Удаление изображения неавторизованным пользователем")
    @Test
    void deleteImageTestUnAuth() {
        given(requestSpecificationWithoutAuth, negativeResponseSpecificationWithoutAuth)
                .delete(UPLOAD_IMAGE + uploadedImageId);
    }

    @DisplayName("Пустой запрос на удаление файла")
    @Test
    void deleteEmptyFileTest() {
        given(requestSpecificationWithAuth, negativeResponseSpecification400)
                .delete(UPLOAD_IMAGE);
    }
}