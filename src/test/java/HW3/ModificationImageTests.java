package HW3;

import dto.PostImageResponse;
import io.qameta.allure.Story;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static HW3.Endpoints.UPLOAD_IMAGE;
import static io.restassured.RestAssured.given;

@Story("Тесты на изменение изображение")

public class ModificationImageTests extends BaseTest {

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

    @DisplayName("Сделать изображение любимым")
    @Test
    void favoriteAnImageTest() {
        given(requestSpecificationWithAuth, positiveResponseSpecification)
                .post(UPLOAD_IMAGE + uploadedImageId + "/favorite");
    }

    @DisplayName("Изменить заголовок файла")
    @Test
    void updateTitleTest() {
        RequestSpecification requestSpecificationUpdateTitle = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .addFormParam("title", Endpoints.IMAGE_TITLE)
                .build();
    }

    @AfterEach
    void tearDown() {
        given(requestSpecificationWithAuth, positiveResponseSpecification)
                .delete(UPLOAD_IMAGE + uploadedImageId);
    }
}