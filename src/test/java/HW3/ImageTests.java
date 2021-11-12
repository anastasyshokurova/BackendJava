package HW3;

import io.qameta.allure.Story;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.CoreMatchers.is;

@Story("Image api tests")
public class ImageTests extends BaseTest{
    private final String PATH_TO_IMAGE="src/test/resources/880249023.jpg";
    static String encodedFile;
    static String uploadedImageId;

    @BeforeEach
    void beforeTest() {
        byte[] byteArray = getFileContent();
        encodedFile = Base64.getEncoder().encodeToString(byteArray);
    }

    @DisplayName("Загрузка файла")
    @Test
    void uploadFileTest() {
        uploadedImageId = given()
                .header("Authorization", token)
                .multiPart("image", encodedFile)
                .expect()
                .body("success", is(true))
                .body("data.id", is(notNullValue()))
                .when()
                .post("https://api.imgur.com/3/image")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");

    }

    @DisplayName("Загрузка файла изображения")
    @Test
    void uploadFileImageTest() {
        uploadedImageId = given()
                .header("Authorization", token)
                .multiPart("image", new File("src/test/resources/880249023.jpg"))
                .expect()
                .statusCode(200)
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @DisplayName("Загрузка файла иного формата")
    @Test
    void uploadFileOtherFormatTest() {
        given()
                .header("Authorization", token)
                .multiPart("image", new File("src/test/resources/880249023.txt"))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .statusCode(400);

    }

    @DisplayName("Загрузка пустого файла")
    @Test
    void uploadEmptyFileTest() {
        given()
                .header("Authorization", token)
                .multiPart("image", new File(""))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .statusCode(400);
    }


    @DisplayName("Сделать изображение любимым")
    @Test
    void favoriteAnImageTest() {
        given()
                .header("Authorization", clientId)
                .log()
                .all()
                .when()
                .post("https://api.imgur.com/3/image/{{imageHash}}/favorite")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @DisplayName("Удаление загруженного изображения авторизованным пользователем")
    @Test
    void deleteImageTestAuth() {
        given()
                .header("Authorization", token)
                .log()
                .all()
                .when()
                .delete("https://api.imgur.com/3/account/{{username}}/image/{{deleteHash}}")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @DisplayName("Удаление изображения неавторизованным пользователем")
    @Test
    void deleteImageTestUnAuth() {
        given()
                .when()
                .delete("https://api.imgur.com/3/image/{{imageHash}}")
                .prettyPeek()
                .then()
                .statusCode(401);
    }

    @DisplayName("Загрузка файла неавторизованным пользователем")
    @Test
    void uploadFileUnAuthedTest() {
        given()
                .multiPart("image", new File("src/test/resources/880249023.jpg"))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @AfterEach
    void tearDown() {
        given()
                .header("Authorization", token)
                .when()
                .delete("https://api.imgur.com/3/account/{username}/image/{deleteHash}","testprogmath", uploadedImageId)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    private byte[] getFileContent() {
        byte[] byteArray = new byte[0];
        try {
            byteArray = FileUtils.readFileToByteArray(new File(PATH_TO_IMAGE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }
}


