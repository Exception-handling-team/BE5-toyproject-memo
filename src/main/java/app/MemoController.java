package app;

import java.nio.file.NoSuchFileException;
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


    }

    public void delete() {
        int id = getValidId("삭제할 메모 번호 : ");
        memoService.delete(id);
    }

    public void exit() {

    }


    public void modify() {
        int id = getValidId("수정할 메모 번호 : ");
        System.out.println("제목 : ");
        String title = sc.nextLine();
        System.out.println("내용 : ");
        String content = sc.nextLine();
        memoService.modify(id, title, content);
    }

    public void read() {
        int id = getValidId("읽을 메모 번호 : ");
        while(true){
            try{
                Memo memo = memoService.read(id);
                System.out.println("제목 : " + memo.getTitle());
                System.out.println("내용 : " + memo.getContent());
                break;
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                break;
            }
        }


    }
}
