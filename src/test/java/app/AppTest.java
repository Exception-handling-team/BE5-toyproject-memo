package app;

import global.AppConfig;
import org.junit.jupiter.api.*;
import standard.Util;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    @BeforeAll
    static void beforeAll() {
        AppConfig.setTestMode();
    }

    @BeforeEach
    void before() {
        Util.File.deleteForce(AppConfig.getDbPath());
    }

    @AfterEach
    void after() {
        Util.File.deleteForce(AppConfig.getDbPath());
    }

    @Test
    @DisplayName("== 메모장 == 출력")
    void t1() {
        String out = TestBot.run("");

        assertThat(out).contains("== 메모장 ==");
    }

    @Test
    @DisplayName("메모 목록 보기")
    void t2() {
        String out = TestBot.run("""
                목록
                """);

        assertThat(out).contains("== 메모 목록 ==");
    }

    @Test
    @DisplayName("메모 등록하기")
    void t3() {
        String out = TestBot.run("""
                작성
                첫번째 메모
                안녕하세요?
                목록
                """);

        assertThat(out)
                .contains("== 메모 목록 ==")
                .contains("첫번째 메모");

    }

    @Test
    @DisplayName("메모 삭제하기")
    void t4() {
        String out = TestBot.run("""
                작성
                첫번째 메모
                안녕하세요?
                삭제
                1
                목록
                """);

        assertThat(out)
                .contains("== 메모 목록 ==")
                .doesNotContain("첫번째 메모");

    }

    @Test
    @DisplayName("메모 수정하기")
    void t5() {
        String out = TestBot.run("""
                작성
                첫번째 메모
                안녕하세요?
                수정
                1
                첫번째 메모수정본
                반갑습니다
                목록
                """);

        assertThat(out)
                .contains("== 메모 목록 ==")
                .contains("첫번째 메모수정본");
    }

    @Test
    @DisplayName("메모 읽기")
    void t6() {
        String out = TestBot.run("""
                작성
                첫번째 메모
                안녕하세요?
                읽기
                1
                """);

        assertThat(out)
                .contains("== 메모 목록 ==")
                .contains("첫번째 메모수정본");
    }
}


