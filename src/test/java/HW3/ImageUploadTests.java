package HW3;
import dto.PostImageResponse;
import io.qameta.allure.Story;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import static HW3.Endpoints.UPLOAD_IMAGE;
import static io.restassured.RestAssured.given;

    @Story("Тесты на загрузку файла изображения")

    public class ImageUploadTests extends BaseTest {

    @DisplayName("Загрузка файла") @Test
    void uploadFileTest() {
        uploadedImageId = given(requestSpecificationWithAuthWithBase64, positiveResponseSpecification)
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(PostImageResponse.class)
                .getData().getDeletehash();
    }

    @DisplayName("Загрузка файла изображения")
    @Test
    void uploadFileImageTest () {
        MultiPartSpecification multiPartSpecWithFile = new MultiPartSpecBuilder(new File(Endpoints.PATH_TO_IMAGE))
                .controlName("image")
                .build();

        RequestSpecification requestSpecificationWithAuthAndMultipartImage = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .addFormParam("type", Endpoints.TYPE_FILE)
                .addFormParam("name", Endpoints.IMAGE_NAME)
                .addMultiPart(multiPartSpecWithFile)
                .build();

        uploadedImageId = given(requestSpecificationWithAuthAndMultipartImage, positiveResponseSpecification)
                .post(Endpoints.API_UPLOAD)
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(PostImageResponse.class)
                .getData().getId();
    }
    @DisplayName("Загрузка изображения в формате base64")
    @Test
    void uploadFileBase64Test() {
        uploadedImageId = given(requestSpecificationWithAuthWithBase64, positiveResponseSpecification)
                .post(Endpoints.API_UPLOAD)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.id");
    }

    @DisplayName("Загрузка файла неавторизованным пользователем")
    @Test
    void uploadFileUnAuthedTest() {
         RequestSpecification requestSpecificationUnAuthWithBase64 = new RequestSpecBuilder()
                 .addFormParam("type", Endpoints.TYPE_BASE64)
                 .addFormParam("name", Endpoints.IMAGE_NAME)
                 .addMultiPart(base64MultiPartSpec)
                 .build();

        given(requestSpecificationUnAuthWithBase64, negativeResponseSpecificationWithoutAuth)
                .post(Endpoints.API_UPLOAD);
    }

     @DisplayName("Загрузка файла иного формата")
     @Test
     void uploadFileOtherFormatTest() {
         RequestSpecification requestSpecificationOtherFormat = new RequestSpecBuilder()
                 .addHeader("Authorization", token)
                 .addFormParam("type", "text")
                 .addFormParam("name", Endpoints.IMAGE_NAME)
                 .addMultiPart(base64MultiPartSpec)
                 .build();

         given(requestSpecificationOtherFormat, negativeResponseSpecification400)
                 .post(Endpoints.API_UPLOAD);
     }

     @DisplayName("Пустой запрос на загрузку файла")
     @Test
     void uploadEmptyFileTest() {
         RequestSpecification requestSpecificationWithEmptyFile = new RequestSpecBuilder()
                 .addHeader("Authorization", token)
                 .addFormParam("type", Endpoints.TYPE_BASE64)
                 .addFormParam("name", Endpoints.IMAGE_NAME)
                 .build();

         given(requestSpecificationWithEmptyFile,negativeResponseSpecification400)
                 .post(Endpoints.API_UPLOAD);
     }

     @AfterEach
     void tearDown () {
        given(requestSpecificationWithAuth,positiveResponseSpecification)
                .delete(UPLOAD_IMAGE + uploadedImageId);

        }
    }


