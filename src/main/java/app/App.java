package app;

import java.util.Scanner;

public class App {
    private final Scanner sc;
    private final MemoController memoController;

    public App(Scanner sc) {
        this.sc = sc;
        memoController = new MemoController(sc);
    }
    public void run() {
        System.out.println("== 메모장 ==");
        while(true) {
            System.out.println("명령 ) ");
            String cmd = sc.nextLine();

            switch (cmd) {
                case "목록" -> memoController.list();
                case "작성" -> memoController.write();
                case "종료" -> memoController.exit();
                case "삭제" -> memoController.delete();
                case "수정" -> memoController.modify();
                case "읽기" -> memoController.read();



                default -> System.out.println("올바른 명령이 아닙니다.");
            }
            if (cmd.equals("종료")) break;
        }
    }
}
