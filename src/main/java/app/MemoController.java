package app;

import java.util.Scanner;

public class MemoController {
    private final Scanner sc;
    private MemoService memoService;

    private int getValidId(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("잘못된 번호입니다. 다시 입력해주세요.");
            }
        }
    }

    public MemoController(Scanner sc) {
        this.sc = sc;
        memoService = new MemoService();
    }

    public void list() {
        System.out.println("== 메모 목록 ==");
        memoService.list().forEach(memo -> {
            System.out.println(memo.getId() + " : " + memo.getTitle());
        });
    }

    public void write() {
        System.out.println("제목 : ");
        String title = sc.nextLine();
        System.out.println("내용 : ");
        String content = sc.nextLine();
        memoService.write(title, content);
        System.out.println("메모가 성공적으로 작성되었습니다.");
    }

    public void delete() {
        while (true) {
            int id = getValidId("삭제할 메모 번호 : ");
            Memo memo = memoService.read(id);
            if (memo != null) {
                memoService.delete(id);
                System.out.println("메모가 성공적으로 삭제되었습니다.");
                break;
            } else {
                System.out.println("해당 번호의 메모가 존재하지 않습니다. 다시 입력해주세요.");
            }
        }
    }

    public void exit() {
        System.out.println("프로그램을 종료합니다.");
    }

    public void modify() {
        while (true) {
            int id = getValidId("수정할 메모 번호 : ");
            Memo memo = memoService.read(id);
            if (memo != null) {
                System.out.println("새 제목 : ");
                String title = sc.nextLine();
                System.out.println("새 내용 : ");
                String content = sc.nextLine();
                memoService.modify(id, title, content);
                System.out.println("메모가 성공적으로 수정되었습니다.");
                break;
            } else {
                System.out.println("해당 번호의 메모가 존재하지 않습니다. 다시 입력해주세요.");
            }
        }
    }

    public void read() {
        while (true) {
            int id = getValidId("읽을 메모 번호 : ");
            Memo memo = memoService.read(id);
            if (memo != null) {
                System.out.println("제목 : " + memo.getTitle());
                System.out.println("내용 : " + memo.getContent());
                break;
            } else {
                System.out.println("해당 번호의 메모가 존재하지 않습니다. 다시 입력해주세요.");
            }
        }
    }
}
